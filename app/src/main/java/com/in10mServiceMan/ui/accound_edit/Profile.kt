package com.in10mServiceMan.ui.accound_edit


import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import com.in10m.ui.activities.BaseFragment
import com.in10mServiceMan.models.CompleteProfile
import com.in10mServiceMan.models.CustomerCompleteProfile
import com.in10mServiceMan.models.CustomerCompleteProfileAfterUpdate
import com.in10mServiceMan.models.RequestUpdateServiceMan

import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.profile.IProfileView
import com.in10mServiceMan.ui.activities.profile.ImageUpdateResponse
import com.in10mServiceMan.ui.activities.profile.ProfilePresenter
import com.in10mServiceMan.ui.activities.profile.ServiceOfferAdapter
import com.in10mServiceMan.ui.activities.services.ServicesResponse
import com.in10mServiceMan.ui.activities.signup.State
import com.in10mServiceMan.ui.activities.signup.StatesResponse
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.in10mServiceMan.utils.localStorage
import com.in10mServiceMan.utils.spinnerAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class Profile : BaseFragment(), IProfileView {
    override fun onDPUpdated(mData: ImageUpdateResponse) {
        destroyDialog()
        if (mData.status == 1) {
            val mServiceManId =
                SharedPreferencesHelper.getString(activity, Constants.SharedPrefs.User.USER_ID, "")
            if (mServiceManId != null) {
                mPresenter.getCompleteProfile(mServiceManId)
            }
            Showtoast(mData.message!!)
        }
    }

    override fun onFailed(msg: String) {
        destroyDialog()
        Showtoast(msg)
    }

    override fun onProfileUpdated(mData: CustomerCompleteProfileAfterUpdate) {
        destroyDialog()
        if (mData.status == 1) {
            val mAuthToken = SharedPreferencesHelper.getString(
                activity,
                Constants.SharedPrefs.User.AUTH_TOKEN,
                ""
            )
            val mServiceManId =
                SharedPreferencesHelper.getString(activity, Constants.SharedPrefs.User.USER_ID, "")

            if (imageUri.isNotEmpty()) {
                showProgressDialog("")
                if (mAuthToken != null) {
                    if (mServiceManId != null) {
                        mPresenter.updateProfilePicture(mAuthToken, mServiceManId, imageUri)
                    }
                }
            } else {
                if (mServiceManId != null) {
                    mPresenter.getCompleteProfile(mServiceManId)
                }
            }
        } else {
            Showtoast(mData.message)
        }
    }

    override fun onCompleteProfileReceived(metaData: CustomerCompleteProfile) {
        if (metaData.status == 1) {
            localStorage(activity).saveCompleteCustomer(metaData.data)
            activity?.finish()
        }
    }

    var country: String = ""
    var country_id: String = ""
    private var myUserId = 0
    var profile: CompleteProfile = CompleteProfile()
    var imageUri: String = ""
    internal var rq = RequestUpdateServiceMan()
    private val mPresenter = ProfilePresenter(this)
    private var servicemanSelectedServiceAdapter: ServiceOfferAdapter? = null
    private var mStatesList: List<State?>? = null
    var state: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        view.btnSaveProfile.visibility = View.GONE
        view.btnEditProfile.visibility = View.VISIBLE
        view.btnChangeImage.visibility = View.GONE
        view.fullnameET.isEnabled = false
        view.mobileET.isEnabled = false
        view.edt_age.isEnabled = false
        view.emailET.isEnabled = false
        view.apartmentNameET1.isEnabled = false
        view.streetNameET1.isEnabled = false
        view.landMarkNameET1.isEnabled = false
        view.stateNameET1.isEnabled = false
        view.pinCodeET1.isEnabled = false


        view.tvAddressType_EditProfLay.setOnClickListener {
            openAddressTypePopUp()
        }
        view.btnEditProfile.setOnClickListener {
            view.btnSaveProfile.visibility = View.VISIBLE
            view.btnEditProfile.visibility = View.GONE
            view.btnChangeImage.visibility = View.VISIBLE
            view.fullnameET.isEnabled = true
            view.mobileET.isEnabled = true
            view.edt_age.isEnabled = true
            view.emailET.isEnabled = true
            view.apartmentNameET1.isEnabled = true
            view.streetNameET1.isEnabled = true
            view.landMarkNameET1.isEnabled = true
            view.stateNameET1.visibility = View.GONE
            view.txt_view_state.visibility = View.VISIBLE
            view.pinCodeET1.isEnabled = true

            view.edt_age.setOnClickListener {
                val cal = Calendar.getInstance()
                val startDateYear = cal.get(Calendar.YEAR)
                val startDateMonth = cal.get(Calendar.MONTH)
                val startDateDay = cal.get(Calendar.DAY_OF_MONTH)

                val dpd_startdate = activity?.let { it1 ->
                    DatePickerDialog(
                        it1,
                        R.style.CalendarThemeOne,
                        DatePickerDialog.OnDateSetListener { v, myear, mmonth, mdayOfMonth ->
                            val month = mmonth + 1

                            view.edt_age.setText("$month-$mdayOfMonth-$myear")//"$myear-$month-$mdayOfMonth"


                        },
                        startDateYear,
                        startDateMonth,
                        startDateDay
                    )
                }
                // dpd_startdate.datePicker.minDate = System.currentTimeMillis() - 1000
                dpd_startdate!!.show()
            }
        }

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

        loadProfile()
        getPreServices()
        getStates()

        view.btnSaveProfile.setOnClickListener {
            val mAuthToken = SharedPreferencesHelper.getString(
                activity,
                Constants.SharedPrefs.User.AUTH_TOKEN,
                ""
            )
            val mServiceManId =
                SharedPreferencesHelper.getString(activity, Constants.SharedPrefs.User.USER_ID, "")

            val email = view.emailET.text.toString()
            val mobile = view.mobileET.text.toString()
            val fullname = view.fullnameET.text.toString()
            val dob = getFormattedDateRequest(view.edt_age.text.toString().trim())
            val address1 = view.apartmentNameET1.text.toString()
            val address2 = view.streetNameET1.text.toString()
            val city = view.landMarkNameET1.text.toString()
            val pincode = view.pinCodeET1.text.toString()

            if (fullname.isEmpty()) {
                Showtoast("Enter the name")
            } else if (mobile.isEmpty() || mobile.length != 10) {
                Showtoast("Enter valid mobile number")
            } else if (dob.isEmpty()) {
                Showtoast("Please select a DOB")
            } else if (!isValidEmail(email)) {
                Showtoast("Enter a valid email address")
            } else if (address1.isEmpty()) {
                Showtoast("Enter Suite or Apt. Number")
            } else if (address2.isEmpty()) {
                Showtoast("Enter your Address")
            } else if (city.isEmpty()) {
                Showtoast("Enter your city")
            } else if (state.isEmpty()) {
                Showtoast("Please select your state")
            } else if (pincode.isEmpty()) {
                Showtoast("Enter pincode")
            } else {
                rq.serviceproviderId = SharedPreferencesHelper.getString(
                    activity,
                    Constants.SharedPrefs.User.USER_ID,
                    "0"
                )
                rq.serviceproviderName = fullname
                rq.serviceproviderMobile = mobile
                rq.serviceproviderCountryCode = "+1"
                rq.serviceproviderEmail = email
                rq.serviceproviderLastname = ""
                rq.serviceproviderAddress1 = address1
                rq.serviceproviderAdddress2 = address2
                rq.serviceproviderStreetName = profile.streetName
                rq.serviceproviderPincode = pincode
                rq.serviceproviderWorkingAs = profile.workingAs
                rq.serviceproviderExperience =
                    profile.experience//edit_text_total_eperiance.text.toString()
                rq.serviceproviderDob = dob
                rq.serviceproviderCity = city
                rq.serviceproviderCountry = profile.country
                rq.serviceproviderCivilId = profile.civilId
                rq.serviceproviderLanguage = profile.language
                rq.serviceproviderLatitude = profile.latitude
                rq.serviceproviderLongitude = profile.longitude
                rq.serviceproviderGender = profile.gender
                rq.serviceproviderGender = state

                showProgressDialog("")
                if (mAuthToken != null) {
                    mPresenter.updateProfile(mAuthToken, rq)
                }
            }
        }

        return view
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
        var myStateList: ArrayList<String>? = ArrayList()
        if (mStatesList?.size!! > 0) {
            for (i in 0 until mStatesList!!.size) {
                myStateList?.add(mStatesList?.get(i)?.name!!)
            }
        }
        val adapter = spinnerAdapter(this.context, R.layout.custom_state_spinner_list)
        myStateList?.let { adapter.addAll(it) }
        adapter.add("STATE")
        view!!.txt_view_state.adapter = adapter
        view!!.txt_view_state.setSelection(adapter.count)
    }

    private fun loadProfile() {
        /*val isLoggedIn = localStorage(this@ProfileActivity).isLoggedIn*/
        var isLoggedIn = false //localStorage(context).isLoggedIn
        isLoggedIn =
            !SharedPreferencesHelper.getString(activity, Constants.SharedPrefs.User.AUTH_TOKEN, "")
                .isNullOrEmpty()
        if (isLoggedIn) {
            myUserId = SharedPreferencesHelper.getString(
                activity,
                Constants.SharedPrefs.User.USER_ID,
                "0"
            )!!
                .toInt()//localStorage(this@ProfileActivity).loggedInUser.customerId
            val callServiceProviders = APIClient.getApiInterface().getCompleteProfile(myUserId)
            callServiceProviders.enqueue(object : Callback<CustomerCompleteProfile> {
                override fun onResponse(
                    call: Call<CustomerCompleteProfile>,
                    response: Response<CustomerCompleteProfile>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data!!.data != null) {
                            profile = data.data

                            rq.serviceproviderExperience = profile.experience
                            rq.serviceproviderWorkingAs = profile.workingAs
                            view!!.txt_view_working_as.text = profile.workingAs
                            rq.serviceproviderId = profile.id!!.toString()
                            rq.serviceproviderName = profile.name
                            rq.serviceproviderLastname = profile.lastname
                            rq.serviceproviderDob = profile.dob
                            if (profile.dob != null)
                                view!!.edt_age.setText(getFormattedDateResponse(profile.dob.toString()))
                            if (profile.state != null)
                                view!!.stateNameET1.setText(profile.state)
                            rq.serviceproviderMobile = profile.mobile
                            rq.serviceproviderCountryCode = profile.countryCode
                            rq.serviceproviderEmail = profile.email
                            rq.serviceproviderGender = profile.gender
                            rq.serviceproviderAddress1 = profile.address1
                            rq.serviceproviderAdddress2 = profile.adddress2
                            rq.serviceproviderCity = profile.city
                            rq.serviceproviderCountry = profile.country
                            rq.serviceproviderLanguage = "en"
                            rq.serviceproviderLatitude = profile.latitude
                            rq.serviceproviderLongitude = profile.longitude
                            rq.serviceproviderImage =
                                if (profile.image.isEmpty()) "http://35.180.58.90/development/in10m/in10m/public/images/users/default_user_avatar.png" else profile.image
                            rq.serviceproviderRating = profile.rating
                            rq.serviceproviderCivilId = profile.civilId
                            rq.serviceproviderLanguage = profile.language
                            rq.serviceproviderPincode = profile.pincode
                            rq.serviceproviderState = profile.state
                            rq.serviceproviderStreetName = profile.streetName

                            if (profile.lastname != null)
                                view!!.fullnameET.setText(profile.name.toString() + " " + profile.lastname)
                            else
                                view!!.fullnameET.setText(profile.name.toString())

                            view!!.mobileET.setText(profile.mobile.toString())
                            view!!.emailET.setText(if (profile.email == null) "" else profile.email)
                            view!!.apartmentNameET1.setText(if (profile.address1 == null) "" else profile.address1)
                            view!!.streetNameET1.setText(if (profile.adddress2 == null) "" else profile.adddress2)
                            view!!.landMarkNameET1.setText(if (profile.city == null) "" else profile.city)
                            view!!.stateNameET1.setText(if (profile.state == null) "" else profile.state)
                            view!!.pinCodeET1.setText(if (profile.pincode == null) "" else profile.pincode)
                            view!!.txt_view_working_as.text = profile.workingAs

                            Picasso.get().load(profile.image) // web image url
                                .fit().centerInside()
                                .rotate(0f)                    //if you want to rotate by 90 degrees give 90f
                                .error(R.drawable.dummy_user)
                                .placeholder(R.drawable.dummy_user)
                                .into(view!!.serviceManProfile)
                        }
                    } else {
                        Showtoast("Error in loading Complete profile")
                    }
                }

                override fun onFailure(call: Call<CustomerCompleteProfile>, t: Throwable) {

                }
            })
        }
    }

    fun bindOfferedServiceRecyclerView(body: ServicesResponse?) {
        val linearLayoutManager = LinearLayoutManager(activity)
        servicemanSelectedServiceAdapter = ServiceOfferAdapter(body?.data, activity)
        view!!.recycler_view.layoutManager = linearLayoutManager
        view!!.recycler_view.adapter = servicemanSelectedServiceAdapter
        view!!.recycler_view.isNestedScrollingEnabled = false
    }

    private fun getPreServices() {
        APIClient.Token =
            SharedPreferencesHelper.getString(activity, Constants.SharedPrefs.User.AUTH_TOKEN, "")
        val homeCall = APIClient.getApiInterface().getExistingServiceDetailsWithHeaderAndExperience(
            "Bearer " + APIClient.Token,
            SharedPreferencesHelper.getString(activity, Constants.SharedPrefs.User.USER_ID, "0")!!
                .toInt()
        )
        homeCall.enqueue(object : Callback<ServicesResponse> {
            override fun onResponse(
                call: Call<ServicesResponse>,
                response: Response<ServicesResponse>
            ) {
                if (response.isSuccessful) {
                    bindOfferedServiceRecyclerView(response.body()!!)
                } else {
                    Log.d("error", "Error")
                }
            }

            override fun onFailure(call: Call<ServicesResponse>, t: Throwable) {
                Log.d("error", "Error")
            }
        })


        /*loadExistingServices()
        loadServicemanExperience()*/
    }

    private fun getFormattedDateResponse(date: String): String {
        try {
            var mOrderDateObject: Date? = null
            try {
                mOrderDateObject = SimpleDateFormat("yyyy-MM-dd").parse(date)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if (mOrderDateObject != null) {
                val mOrderCalender = Calendar.getInstance()
                mOrderCalender.time = mOrderDateObject
                mOrderCalender.add(Calendar.HOUR, 0)
                mOrderCalender.add(Calendar.MINUTE, 0)
                return SimpleDateFormat("dd-MM-yyyy").format(mOrderCalender.time)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return date
    }

    private fun getFormattedDateRequest(date: String): String {
        try {
            var mOrderDateObject: Date? = null
            try {
                mOrderDateObject = SimpleDateFormat("dd-MM-yyyy").parse(date)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if (mOrderDateObject != null) {
                val mOrderCalender = Calendar.getInstance()
                mOrderCalender.time = mOrderDateObject
                mOrderCalender.add(Calendar.HOUR, 0)
                mOrderCalender.add(Calendar.MINUTE, 0)
                return SimpleDateFormat("yyyy-MM-dd").format(mOrderCalender.time)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return date
    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return if (target == null) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    fun Showtoast(msg: String) {
        Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
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
                etBuildingNo_EditProfLay.setText("")
                etFloorNo_EditProfLay.setText("")
                etAptOffNo_EditProfLay.setText("")
                etAvenue_EditProfLay.setText("")
                addressType = "1"
                tvAddressType_EditProfLay.text = context!!.resources.getString(R.string.house)
                etBuildingNo_EditProfLay.hint = context!!.resources.getString(R.string.house_no)
                etFloorNo_EditProfLay.visibility = View.GONE
                etAptOffNo_EditProfLay.visibility = View.GONE
            } else if (checkedId == R.id.rbApartment_AddressTypePopUp) {
                etBuildingNo_EditProfLay.setText("")
                etFloorNo_EditProfLay.setText("")
                etAptOffNo_EditProfLay.setText("")
                etAvenue_EditProfLay.setText("")
                addressType = "2"
                tvAddressType_EditProfLay.text = context!!.resources.getString(R.string.apartment)
                etBuildingNo_EditProfLay.hint = context!!.resources.getString(R.string.building_no)
                etAptOffNo_EditProfLay.hint = context!!.resources.getString(R.string.apartment)
                etFloorNo_EditProfLay.visibility = View.VISIBLE
                etAptOffNo_EditProfLay.visibility = View.VISIBLE
            } else if (checkedId == R.id.rbOffice_AddressTypePopUp) {
                etBuildingNo_EditProfLay.setText("")
                etFloorNo_EditProfLay.setText("")
                etAptOffNo_EditProfLay.setText("")
                etAvenue_EditProfLay.setText("")
                addressType = "3"
                tvAddressType_EditProfLay.text = context!!.resources.getString(R.string.office)
                etBuildingNo_EditProfLay.hint = context!!.resources.getString(R.string.building_no)
                etAptOffNo_EditProfLay.hint = context!!.resources.getString(R.string.office_no)
                etFloorNo_EditProfLay.visibility = View.VISIBLE
                etAptOffNo_EditProfLay.visibility = View.VISIBLE
            }
        }
        ivClose_AddressPopUp.setOnClickListener { v: View? -> dialog.dismiss() }
        btnContinue_AddressPopUp.setOnClickListener { v: View? -> dialog.dismiss() }
        dialog.show()
    }
}
