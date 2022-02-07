package com.in10mServiceMan.ui.activities.signup


import android.app.DatePickerDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.PlaceBuffer
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

import com.in10mServiceMan.R
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.in10mServiceMan.utils.spinnerAdapter
import kotlinx.android.synthetic.main.fragment_signup_details.*
import kotlinx.android.synthetic.main.fragment_signup_details.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.*
import kotlin.collections.ArrayList


class SignupDetailsFragment : Fragment(), GoogleApiClient.OnConnectionFailedListener,
    GoogleApiClient.ConnectionCallbacks {

    var state: String = "Hawally"
    var city: String = "Kuwait City"
    var country: String = "Kuwait"
    var country_id: String = "12"

    private var mGoogleApiClient: GoogleApiClient? = null
    private var mPlaceArrayAdapter: PlaceArrayAdapter? = null
    private var mStatesList: List<State?>? = null
    var filter: AutocompleteFilter? = null
    private val BOUNDS_MOUNTAIN_VIEW =
        LatLngBounds(LatLng(37.398160, -122.180831), LatLng(37.430610, -121.972090))
    val GOOGLE_API_CLIENT_ID = 0

    companion object {
        private var mListener: NextFragmentInterfaceOne? = null
        fun newInstance(mNextFragmentListener: NextFragmentInterfaceOne): SignupDetailsFragment {
            mListener = mNextFragmentListener
            return SignupDetailsFragment()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_signup_details, container, false)
        filter = AutocompleteFilter.Builder().setCountry("KW").build()
        getStates()

        /*if (SharedPreferencesHelper.getString(this.context, Constants.SharedPrefs.User.ACCOUNT_TYPE, "2") == "2") {
            view.personalDetailsCompanyDetailsTV.visibility = View.VISIBLE
            view.personalDetailsCompanyTIL.visibility = View.VISIBLE
        } else {
            view.personalDetailsCompanyDetailsTV.visibility = View.GONE
            view.personalDetailsCompanyTIL.visibility = View.GONE

        }*/
        var myTypeSpinner = view.findViewById(R.id.txt_view_state) as Spinner

        myTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (mStatesList != null) {
                    /*state = mStatesList!![position]!!.name!!*/
                    state = myTypeSpinner.selectedItem.toString()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        view.tvAddressType_SignUpProfLay.setOnClickListener {
            openAddressTypePopUp()
        }

        view.signUpPhaseDetailsOkTickIV.setOnClickListener {

            when {
                view.personalDetailsFirstName.text.toString().trim().isEmpty() -> {
                    ShowToast(resources.getString(R.string.please_enter_name))
                }
                view.personalDetailsLastName.text.toString().trim().isEmpty() -> {
                    ShowToast(resources.getString(R.string.please_enter_last_name))
                }
                view.personalDetailsLastDOB.text.toString().trim().isEmpty() -> {
                    ShowToast(resources.getString(R.string.select_your_dob))
                }
                ageCalculator(view.personalDetailsLastDOB.text.toString()) < 13 -> {
                    ShowToast(resources.getString(R.string.age_must_be_greater_than))
                }
                view.etAreaName_SignUpProfLay.text.toString().trim().isEmpty() -> {
                    ShowToast(resources.getString(R.string.please_enter_your_area_name))
                }
                view.etBlock_SignUpProfLay.text.toString().trim().isEmpty() -> {
                    ShowToast(resources.getString(R.string.please_enter_your_block))
                }
                view.etStreet_SignUpProfLay.text.toString().trim().isEmpty() -> {
                    ShowToast(resources.getString(R.string.please_enter_your_street))
                }

                /*view.personalDetailsLastStreetAddress.text.toString().trim().isEmpty() -> {
                    ShowToast(resources.getString(R.string.please_enter_street_address))
                }
                view.personalDetailsLastSuite.text.toString().trim().isEmpty() -> {
                    ShowToast(resources.getString(R.string.please_enter_apartment_number))
                }
                view.personalDetailsStreetAddress.text.toString().trim().isEmpty() -> {
                    ShowToast(resources.getString(R.string.please_enter_your_city))
                }
                state.isEmpty() -> {
                    ShowToast(resources.getString(R.string.please_select_your_state))
                }
                view.personalDetailsZip.text.toString().trim().length != 5 -> {
                    ShowToast(resources.getString(R.string.please_enter_valid_zipcode))
                }*/
                else -> {
                    SharedPreferencesHelper.putString(
                        this.context!!,
                        Constants.SharedPrefs.User.FIRST_NAME,
                        view.personalDetailsFirstName.text.toString().trim()
                    )
                    SharedPreferencesHelper.putString(
                        this.context!!,
                        Constants.SharedPrefs.User.LAST_NAME,
                        view.personalDetailsLastName.text.toString().trim()
                    )
                    SharedPreferencesHelper.putString(
                        this.context!!,
                        Constants.SharedPrefs.User.STREET,
                        view.etStreet_SignUpProfLay.text.toString().trim()
                    )
                    SharedPreferencesHelper.putString(
                        this.context!!,
                        Constants.SharedPrefs.User.SUITE,
                        view.etAreaName_SignUpProfLay.text.toString().trim()
                    )
                    SharedPreferencesHelper.putString(
                        this.context!!,
                        Constants.SharedPrefs.User.CITY,
                        view.etBlock_SignUpProfLay.text.toString().trim()
                    )
                    SharedPreferencesHelper.putString(
                        this.context!!,
                        Constants.SharedPrefs.User.STATE,
                        state
                    )
                    SharedPreferencesHelper.putString(
                        this.context!!,
                        Constants.SharedPrefs.User.ZIPCODE,
                        "500051"
                    )
                    SharedPreferencesHelper.putString(
                        this.context!!,
                        Constants.SharedPrefs.User.COUNTRY,
                        country
                    )
                    SharedPreferencesHelper.putString(
                        this.context!!,
                        Constants.SharedPrefs.User.COUNTRY_CODE,
                        "+1"
                    )//country_id

                    displayAlertDialog()
                }
            }
        }
        view.personalDetailsLastDOB.setOnClickListener()
        {
            val cal = Calendar.getInstance()
            val startDateYear = cal.get(Calendar.YEAR)
            val startDateMonth = cal.get(Calendar.MONTH)
            val startDateDay = cal.get(Calendar.DAY_OF_MONTH)

            val dpd_startdate = DatePickerDialog(
                this.context!!,
                R.style.CalendarThemeOne,
                { v, myear, mmonth, mdayOfMonth ->
                    val month = mmonth + 1

                    view.personalDetailsLastDOB.text =
                        "$month-$mdayOfMonth-$myear"//"$myear-$month-$mdayOfMonth"
                    SharedPreferencesHelper.putString(
                        this.context!!,
                        Constants.SharedPrefs.User.DATE_OF_BIRTH,
                        "$myear-$month-$mdayOfMonth"
                    )

                },
                startDateYear,
                startDateMonth,
                startDateDay
            )
            // dpd_startdate.datePicker.minDate = System.currentTimeMillis() - 1000
            dpd_startdate.show()
        }
        return view
    }


    private fun displayAlertDialog() {
        val mDialogView =
            LayoutInflater.from(this.context).inflate(R.layout.custom_alert_confirm_dialog, null)
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
            override fun onResponse(
                call: Call<StatesResponse>,
                response: Response<StatesResponse>
            ) {
                if (response.isSuccessful) {
                    bindData(response.body()!!)
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
            for (i in 0 until mStatesList!!.size) {
                myStateList?.add(mStatesList?.get(i)?.name!!)
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

    override fun onPause() {
        super.onPause()
        if (mGoogleApiClient != null && mGoogleApiClient?.isConnected!!) {
            mGoogleApiClient?.stopAutoManage(this.context as FragmentActivity)
            mGoogleApiClient?.disconnect()
        }
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

    var addressType = "1"

    private fun openAddressTypePopUp() {
        val dialog = Dialog(context, android.R.style.Theme_Translucent_NoTitleBar)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val window = dialog.window
        dialog.setContentView(R.layout.addresstype_popup)
        val wlp = window.attributes
        wlp.gravity = Gravity.CENTER
        wlp.flags = WindowManager.LayoutParams.FLAG_BLUR_BEHIND
        window.attributes = wlp
        Objects.requireNonNull(window).setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        //        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        val rg_AddressTypePopUp = dialog.findViewById<RadioGroup>(R.id.rg_AddressTypePopUp)
        val ivClose_AddressPopUp: AppCompatImageView =
            dialog.findViewById(R.id.ivClose_AddressPopUp)
        val btnContinue_AddressPopUp: AppCompatButton =
            dialog.findViewById(R.id.btnContinue_AddressPopUp)
        rg_AddressTypePopUp.setOnCheckedChangeListener { group: RadioGroup?, checkedId: Int ->
            if (checkedId == R.id.rbHouse_AddressTypePopUp) {
                etBuildingNo_SignUpProfLay.setText("")
                etFloorNo_SignUpProfLay.setText("")
                etAptOffNo_SignUpProfLay.setText("")
                etAvenue_SignUpProfLay.setText("")
                addressType = "1"
                tvAddressType_SignUpProfLay.text = context!!.resources.getString(R.string.house)
                etBuildingNo_SignUpProfLay.hint = context!!.resources.getString(R.string.house_no)
                etFloorNo_SignUpProfLay.visibility = View.GONE
                etAptOffNo_SignUpProfLay.visibility = View.GONE
            } else if (checkedId == R.id.rbApartment_AddressTypePopUp) {
                etBuildingNo_SignUpProfLay.setText("")
                etFloorNo_SignUpProfLay.setText("")
                etAptOffNo_SignUpProfLay.setText("")
                etAvenue_SignUpProfLay.setText("")
                addressType = "2"
                tvAddressType_SignUpProfLay.text = context!!.resources.getString(R.string.apartment)
                etBuildingNo_SignUpProfLay.hint =
                    context!!.resources.getString(R.string.building_no)
                etAptOffNo_SignUpProfLay.hint = context!!.resources.getString(R.string.apartment)
                etFloorNo_SignUpProfLay.visibility = View.VISIBLE
                etAptOffNo_SignUpProfLay.visibility = View.VISIBLE
            } else if (checkedId == R.id.rbOffice_AddressTypePopUp) {
                etBuildingNo_SignUpProfLay.setText("")
                etFloorNo_SignUpProfLay.setText("")
                etAptOffNo_SignUpProfLay.setText("")
                etAvenue_SignUpProfLay.setText("")
                addressType = "3"
                tvAddressType_SignUpProfLay.text = context!!.resources.getString(R.string.office)
                etBuildingNo_SignUpProfLay.hint =
                    context!!.resources.getString(R.string.building_no)
                etAptOffNo_SignUpProfLay.hint = context!!.resources.getString(R.string.office_no)
                etFloorNo_SignUpProfLay.visibility = View.VISIBLE
                etAptOffNo_SignUpProfLay.visibility = View.VISIBLE
            }
        }
        ivClose_AddressPopUp.setOnClickListener { v: View? -> dialog.dismiss() }
        btnContinue_AddressPopUp.setOnClickListener { v: View? -> dialog.dismiss() }
        dialog.show()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Toast.makeText(
            this.context,
            "Google Places API connection failed with error code:" + p0.errorCode,
            Toast.LENGTH_LONG
        ).show()
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
}
