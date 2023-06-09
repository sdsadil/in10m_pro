package com.in10mServiceMan.ui.activities.company_registration


import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.PlaceBuffer
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.signup.State
import com.in10mServiceMan.ui.activities.signup.StatesResponse
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.in10mServiceMan.utils.spinnerAdapter
import kotlinx.android.synthetic.main.fragment_signup_company_details.*
import kotlinx.android.synthetic.main.fragment_signup_company_details.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.*


class SignupCompanyDetailsFragment : Fragment(), GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    override fun onConnectionFailed(p0: ConnectionResult) {
        Toast.makeText(this.context, "Google Places API connection failed with error code:" + p0.errorCode, Toast.LENGTH_LONG).show()
    }

    override fun onConnected(p0: Bundle?) {
        mPlaceArrayAdapter?.setGoogleApiClient(mGoogleApiClient)
    }

    override fun onConnectionSuspended(p0: Int) {
        mPlaceArrayAdapter?.setGoogleApiClient(null)

    }

    interface NextFragmentInterfaceOne {
        fun toNextFragmentOne()
    }

    var state: String = ""
    var city: String = ""
    var country: String = ""
    var country_id: String = ""
    private var mGoogleApiClient: GoogleApiClient? = null
    private var mPlaceArrayAdapter: PlaceArrayAdapter? = null
    private var mStatesList: List<State?>? = null
    var filter: AutocompleteFilter? = null
    private val BOUNDS_MOUNTAIN_VIEW = LatLngBounds(LatLng(37.398160, -122.180831), LatLng(37.430610, -121.972090))
    val GOOGLE_API_CLIENT_ID = 0


    companion object {
        private var mListener: SignupCompanyDetailsFragment.NextFragmentInterfaceOne? = null
        fun newInstance(mNextFragmentListener: SignupCompanyDetailsFragment.NextFragmentInterfaceOne): SignupCompanyDetailsFragment {
            mListener = mNextFragmentListener
            return SignupCompanyDetailsFragment()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_signup_company_details, container, false)

        filter = AutocompleteFilter.Builder().setCountry("US").build()
        getStates()
        var myTypeSpinner = view.findViewById(R.id.txt_view_state) as Spinner

        myTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>, view: View,
                    position: Int, id: Long
            ) {
                if (mStatesList != null) {

                    /*state = mStatesList!![position]!!.name!!*/
                    state = myTypeSpinner.selectedItem.toString()

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        view.signUpPhaseDetailsOkTickIV.setOnClickListener {

            if (view.personalDetailsFirstName.text.toString().isEmpty()) {
                ShowToast("Please enter name")
            } else if (view.personalDetailsLastName.text.toString().isEmpty()) {
                ShowToast("Please enter last name")
            } else if (view.personalDetailsLastDOB.text.toString().isEmpty()) {
                ShowToast("Select Your DOB")
            } else if (ageCalculator(view.personalDetailsLastDOB.text.toString()) < 13) {
                ShowToast("Age must be greater than 13")
            } else if (view.personalDetailsLastStreetAddress.text.toString().isEmpty()) {
                ShowToast("Please enter Street Address")
            } else if (view.personalDetailsLastSuite.text.toString().isEmpty()) {
                ShowToast("Please enter Apartment number")
            } else if (view.personalDetailsStreetAddress.text.toString().isEmpty()) {
                ShowToast("Please enter your city")
            } else if (state.isEmpty()) {
                ShowToast("Please select your state")
            } else if (view.personalDetailsZip.text.toString().isEmpty()) {
                ShowToast("Please enter valid zipcode")
            } else {
                SharedPreferencesHelper.putString(this.context!!, Constants.SharedPrefs.User.FIRST_NAME, view.personalDetailsFirstName.text.toString())
                SharedPreferencesHelper.putString(this.context!!, Constants.SharedPrefs.User.LAST_NAME, view.personalDetailsLastName.text.toString())
                SharedPreferencesHelper.putString(this.context!!, Constants.SharedPrefs.User.STREET, view.personalDetailsLastStreetAddress.text.toString())
                SharedPreferencesHelper.putString(this.context!!, Constants.SharedPrefs.User.SUITE, view.personalDetailsLastSuite.text.toString())
                SharedPreferencesHelper.putString(this.context!!, Constants.SharedPrefs.User.CITY, view.personalDetailsStreetAddress.text.toString())
                SharedPreferencesHelper.putString(this.context!!, Constants.SharedPrefs.User.STATE, state)
                SharedPreferencesHelper.putString(this.context!!, Constants.SharedPrefs.User.ZIPCODE, view.personalDetailsZip.text.toString())
                SharedPreferencesHelper.putString(this.context!!, Constants.SharedPrefs.User.COUNTRY, country)
                SharedPreferencesHelper.putString(this.context!!, Constants.SharedPrefs.User.COUNTRY_CODE, "+965")//country_id
                SharedPreferencesHelper.putString(this.context!!, Constants.SharedPrefs.User.NAME_OF_COMPANY, view.personalDetailsCompanyET.text.toString())
                displayAlertDialog()
            }
        }
        view.personalDetailsLastDOB.setOnClickListener()
        {
            val cal = Calendar.getInstance()
            val startDateYear = cal.get(Calendar.YEAR)
            val startDateMonth = cal.get(Calendar.MONTH)
            val startDateDay = cal.get(Calendar.DAY_OF_MONTH)

            val dpd_startdate = DatePickerDialog(this.context!!, R.style.CalendarThemeOne, DatePickerDialog.OnDateSetListener { v, myear, mmonth, mdayOfMonth ->
                val month = mmonth + 1

                view.personalDetailsLastDOB.text = "$month-$mdayOfMonth-$myear"//"$myear-$month-$mdayOfMonth"
                SharedPreferencesHelper.putString(this.context!!, Constants.SharedPrefs.User.DATE_OF_BIRTH, "$myear-$month-$mdayOfMonth")

            }, startDateYear, startDateMonth, startDateDay)
            // dpd_startdate.datePicker.minDate = System.currentTimeMillis() - 1000
            dpd_startdate.show()
        }

        /*view.personalDetailsStreetAddress.threshold = 3
        if (mGoogleApiClient == null || !mGoogleApiClient?.isConnected!!) {
            try {

                mGoogleApiClient = GoogleApiClient.Builder(this.context as Activity)
                        .addApi(Places.GEO_DATA_API)
                        .enableAutoManage(this.activity!!, GOOGLE_API_CLIENT_ID, this)
                        .addConnectionCallbacks(this)
                        .build()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        view.personalDetailsStreetAddress.onItemClickListener = mAutocompleteClickListener
        mPlaceArrayAdapter = PlaceArrayAdapter(this.context, android.R.layout.simple_list_item_1, BOUNDS_MOUNTAIN_VIEW, filter)
        view.personalDetailsStreetAddress.setAdapter(mPlaceArrayAdapter)*/



        return view
    }

    val mAutocompleteClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
        val item = mPlaceArrayAdapter?.getItem(position)
        val placeId = item?.placeId.toString()

        val placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient!!, placeId)
        placeResult.setResultCallback(mUpdatePlaceDetailsCallback)
    }

    val mUpdatePlaceDetailsCallback = object : ResultCallback<PlaceBuffer> {
        override fun onResult(places: PlaceBuffer) {
            if (!places.status.isSuccess) {
                Log.e("Location", ("Place query did not complete. Error: " + places.status.toString()))
                return
            }
            // Selecting the first object buffer.
            val place = places.get(0)
            val attributions = places.attributions
            personalDetailsStreetAddress.setText(place.name.toString())
            /* mNameView.setText(Html.fromHtml(place.getAddress() + ""))*/
        }
    }

    private fun displayAlertDialog() {
        val mDialogView = LayoutInflater.from(this.context).inflate(R.layout.custom_alert_confirm_dialog, null)
        val mBuilder = AlertDialog.Builder(this.context!!).setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val button = mDialogView.findViewById(R.id.confirmButton) as ConstraintLayout
        button.setOnClickListener {
            mListener!!.toNextFragmentOne()
            mAlertDialog.cancel()
        }
    }

    private fun ShowToast(msg: String) {
        Toast.makeText(this.context!!, msg, Toast.LENGTH_SHORT).show()
    }

    private fun getStates() {
        val homeCall = APIClient.getApiInterface().states
        homeCall.enqueue(object : Callback<StatesResponse> {
            override fun onResponse(call: Call<StatesResponse>, response: Response<StatesResponse>) {
                if (response.isSuccessful) {
                    bindData(response.body()!!)
                } else {

                }
            }

            override fun onFailure(call: Call<StatesResponse>, t: Throwable) {

            }
        })


    }

    fun bindData(body: StatesResponse) {
        mStatesList = body.data?.states
        country = body.data?.countryCode!!
        country_id = body.data.countryId.toString()
        val myStateList: ArrayList<String> = ArrayList()
        if (mStatesList?.size!! > 0) {
            for (i in mStatesList!!.indices) {
                myStateList.add(mStatesList?.get(i)?.name!!)
            }
        }
        val adapter = spinnerAdapter(this.context, R.layout.custom_state_spinner_list)
        if (myStateList != null) {
            adapter.addAll(myStateList)
        }
        adapter.add("STATE")
        txt_view_state.adapter = adapter
        txt_view_state.setSelection(adapter.count)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun ageCalculator(date: String): Int {
        val s = date
        val sdf = SimpleDateFormat("MM-dd-yyyy")
        val d = sdf.parse(s)
        val c = Calendar.getInstance()
        c.time = d
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH) + 1
        val date = c.get(Calendar.DATE)
        val l1 = LocalDate.of(year, month, date)
        val now1 = LocalDate.now()
        val diff1 = Period.between(l1, now1)
        val age = diff1.years
        val months = diff1.months
        val days = diff1.days
        Log.d("age", "$age $months $days")
        return age
    }
}
