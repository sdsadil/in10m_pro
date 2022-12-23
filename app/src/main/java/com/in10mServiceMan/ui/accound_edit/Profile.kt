package com.in10mServiceMan.ui.accound_edit

import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.services.s3.AmazonS3Client
import com.in10mServiceMan.BuildConfig
import com.in10mServiceMan.R
import com.in10mServiceMan.models.*
import com.in10mServiceMan.models.viewmodels.ServiceWithSubService
import com.in10mServiceMan.ui.activities.BaseFragment
import com.in10mServiceMan.ui.activities.profile.IProfileView
import com.in10mServiceMan.ui.activities.profile.ImageUpdateResponse
import com.in10mServiceMan.ui.activities.profile.ProfilePresenter
import com.in10mServiceMan.ui.activities.profile.ServiceOfferAdapter
import com.in10mServiceMan.ui.activities.services.ServiceData
import com.in10mServiceMan.ui.activities.services.ServicesResponse
import com.in10mServiceMan.ui.activities.signup.State
import com.in10mServiceMan.ui.activities.signup.StatesResponse
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.ui.listener.EditSubServicesListener
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.in10mServiceMan.utils.cropper.CropImage
import com.in10mServiceMan.utils.cropper.CropImageView
import com.in10mServiceMan.utils.localStorage
import com.in10mServiceMan.utils.spinnerAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class Profile(context: Context) : BaseFragment(), IProfileView, EditSubServicesListener {

    private val mcontext: Context = context
    private var isStarted = false
    private var isVisiblee = false
    private var isNewImage: Boolean = false
    private val BUCKET_NAME: String = "in10mdevimages"

    private var mCountryList: List<CountryPojoArray?>? = ArrayList()
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

    private val sharedPref = SharedPreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        try {
            //        setDisable()

            view?.btnSaveProfile?.visibility = View.GONE
            view?.btnEditProfile?.visibility = View.VISIBLE
            view?.btnChangeImage?.visibility = View.GONE
            view?.fullnameET?.isEnabled = false
            view?.mobileET?.isEnabled = false
            view?.edt_age?.isEnabled = false
            view?.emailET?.isEnabled = false
            view?.apartmentNameET1?.isEnabled = false
            view?.streetNameET1?.isEnabled = false
            view?.landMarkNameET1?.isEnabled = false
            view?.stateNameET1?.isEnabled = false
            view?.pinCodeET1?.isEnabled = false

            view.tvGovernorate_EditProfLay.setOnClickListener {
                showGovernorateDialog()
            }
            view.tvAddressType_EditProfLay.setOnClickListener {
                openAddressTypePopUp()
            }
            view.btnChangeImage.setOnClickListener {
                CropImage.activity().setGuidelines(CropImageView.Guidelines.ON)
                    .setOutputCompressQuality(50)
                    .setFixAspectRatio(false)
                    .start(mcontext!!, this)
            }
            view.btnEditProfile.setOnClickListener {
                view.btnSaveProfile.visibility = View.VISIBLE
                view.btnEditProfile.visibility = View.GONE
                view.btnChangeImage.visibility = View.VISIBLE
                view.fullnameET.isEnabled = true
                view.mobileET.isEnabled = false
                view.edt_age.isEnabled = true
                view.emailET.isEnabled = true
                view.apartmentNameET1.isEnabled = true
                view.streetNameET1.isEnabled = true
                view.landMarkNameET1.isEnabled = true
                view.stateNameET1.visibility = View.GONE
                view.txt_view_state.visibility = View.GONE
                view.pinCodeET1.isEnabled = true
            }

            view.edt_age.setOnClickListener {
                val cal = Calendar.getInstance()
                val startDateYear = cal.get(Calendar.YEAR)
                val startDateMonth = cal.get(Calendar.MONTH)
                val startDateDay = cal.get(Calendar.DAY_OF_MONTH)

                val dpd_startdate = mcontext?.let { it1 ->
                    DatePickerDialog(
                        it1,
                        R.style.CalendarThemeOne,
                        { v, myear, mmonth, mdayOfMonth ->
                            val month = mmonth + 1
                            view.edt_age.text =
                                "$mdayOfMonth-$month-$myear"//"$myear-$month-$mdayOfMonth"
                        },
                        startDateYear,
                        startDateMonth,
                        startDateDay
                    )
                }
                // dpd_startdate.datePicker.minDate = System.currentTimeMillis() - 1000
                dpd_startdate!!.show()
            }
            /* val myTypeSpinner = view.findViewById(R.id.txt_view_state) as Spinner

           myTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long,
                ) {
                    if (mStatesList != null) {
                        state = myTypeSpinner.selectedItem.toString()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }*/

            view.btnSaveProfile.setOnClickListener {
                if (isNewImage) {
                    uploadImageToAWS()
                } else {
                    updateProf(imageUri)
                }
            }

            isStarted = true
            if (isVisiblee) {
                loadProfile()
                //            getPreServices()
            }

            val spCountry1_EditProfLay =
                view.findViewById(R.id.spCountry1_EditProfLay) as AppCompatSpinner
            spCountry1_EditProfLay.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long,
                    ) {
                        Log.e("InProfile", "onItemSelected")
                        when {
                            mCountryList != null -> {
                                Log.e("InProfile", "mCountryList != Null")
                                if (spCountry1_EditProfLay.selectedItem.toString() != mcontext?.resources?.getString(
                                        R.string.india
                                    )
                                ) {
                                    country_id = mCountryList!![position]!!.phonecode.toString()
                                    country = spCountry1_EditProfLay.selectedItem.toString()
                                }
                            }
                            else -> {
                                Log.e("InProfile", "mCountryList == Null")
                            }


                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                    }
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return view
    }

    private fun showGovernorateDialog() {
        val dialog = Dialog(mcontext!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.governorate_popup)
        val window = dialog.window
        Objects.requireNonNull(window)!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val tv1_GovernoratePopUp =
            dialog.findViewById<AppCompatTextView>(R.id.tv1_GovernoratePopUp)
        val tv2_GovernoratePopUp =
            dialog.findViewById<AppCompatTextView>(R.id.tv2_GovernoratePopUp)
        val tv3_GovernoratePopUp =
            dialog.findViewById<AppCompatTextView>(R.id.tv3_GovernoratePopUp)
        val tv4_GovernoratePopUp =
            dialog.findViewById<AppCompatTextView>(R.id.tv4_GovernoratePopUp)
        val tv5_GovernoratePopUp =
            dialog.findViewById<AppCompatTextView>(R.id.tv5_GovernoratePopUp)
        val tv6_GovernoratePopUp =
            dialog.findViewById<AppCompatTextView>(R.id.tv6_GovernoratePopUp)
        val ivClose_GovernoratePopUp: AppCompatImageView =
            dialog.findViewById(R.id.ivClose_GovernoratePopUp)
        tv1_GovernoratePopUp.setOnClickListener { v: View? ->
            dialog.dismiss()
            view?.tvGovernorate_EditProfLay?.text = tv1_GovernoratePopUp.text.toString()
        }
        tv2_GovernoratePopUp.setOnClickListener { v: View? ->
            dialog.dismiss()
            view?.tvGovernorate_EditProfLay?.text = tv2_GovernoratePopUp.text.toString()
        }
        tv3_GovernoratePopUp.setOnClickListener { v: View? ->
            dialog.dismiss()
            view?.tvGovernorate_EditProfLay?.text = tv3_GovernoratePopUp.text.toString()
        }
        tv4_GovernoratePopUp.setOnClickListener { v: View? ->
            dialog.dismiss()
            view?.tvGovernorate_EditProfLay?.text = tv4_GovernoratePopUp.text.toString()
        }
        tv5_GovernoratePopUp.setOnClickListener { v: View? ->
            dialog.dismiss()
            view?.tvGovernorate_EditProfLay?.text = tv5_GovernoratePopUp.text.toString()
        }
        tv6_GovernoratePopUp.setOnClickListener { v: View? ->
            dialog.dismiss()
            view?.tvGovernorate_EditProfLay?.text = tv6_GovernoratePopUp.text.toString()
        }
        ivClose_GovernoratePopUp.setOnClickListener { v: View? -> dialog.dismiss() }
        dialog.show()
    }

    fun setDisable() {
        view?.btnSaveProfile?.visibility = View.GONE
        view?.btnEditProfile?.visibility = View.VISIBLE
        view?.btnChangeImage?.visibility = View.GONE
        view?.fullnameET?.isEnabled = false
        view?.mobileET?.isEnabled = false
        view?.edt_age?.isEnabled = false
        view?.emailET?.isEnabled = false
        view?.apartmentNameET1?.isEnabled = false
        view?.streetNameET1?.isEnabled = false
        view?.landMarkNameET1?.isEnabled = false
        view?.stateNameET1?.isEnabled = false
        view?.pinCodeET1?.isEnabled = false
    }

    fun profileUpdate(
        profilePicData: String,
    ) {
        val header =
            SharedPreferencesHelper.getString(mcontext, Constants.SharedPrefs.User.AUTH_TOKEN, "")
        val mServiceManId =
            SharedPreferencesHelper.getString(mcontext, Constants.SharedPrefs.User.USER_ID, "")

        val userId = RequestBody.create(MediaType.parse("text/plain"), mServiceManId)
        var body: MultipartBody.Part? = null
        val file = File(profilePicData)
        val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
        body = MultipartBody.Part.createFormData("profile_picture", file.name, reqFile)

        val profilePicRequest = APIClient.getApiInterface()
            .UpdateServiceManProfilePicture(header, userId, body)
        profilePicRequest.enqueue(object : Callback<ImageUpdateResponse> {
            override fun onResponse(
                call: Call<ImageUpdateResponse>,
                response: Response<ImageUpdateResponse>,
            ) {
                if (response.isSuccessful) {
                    isNewImage = false
                    updateProf(imageUri)
                }
            }

            override fun onFailure(call: Call<ImageUpdateResponse>, t: Throwable) {
            }
        })
    }


    private fun uploadImageToAWS() {
        try {
//            AWSMobileClient.getInstance().initialize(activity).execute()
//            uploadWithTransferUtility(BUCKET_NAME, imageUri)
            profileUpdate(imageUri)
        } catch (e: UninitializedPropertyAccessException) {
            e.printStackTrace()
        }
    }

    private fun uploadWithTransferUtility(remote: String, filePath: String) {

        val localFile = File(filePath)
        val txUtil = TransferUtility.builder().context(mcontext)
            .awsConfiguration(AWSMobileClient.getInstance().configuration)
            .s3Client(AmazonS3Client(AWSMobileClient.getInstance().credentialsProvider))
            .build()

        val fileKey: String =
            profile.id.toString() + "_" + System.currentTimeMillis() + localFile.name

        val txObserver = txUtil.upload(remote, fileKey, localFile)

        txObserver.setTransferListener(object : TransferListener {
            override fun onStateChanged(id: Int, state: TransferState) {
                if (state == TransferState.COMPLETED) {
                    // Handle a completed upload
                    state.declaringClass.getResource(state.name)
                    Log.e(
                        "uploadWithTransfer",
                        "URL : " + "https://" + txObserver.bucket + ".s3.amazonaws.com/" + fileKey
                    )
                    val finalUrl: String =
                        "https://" + txObserver.bucket + ".s3.amazonaws.com/" + fileKey
                    isNewImage = false
                    updateProf(finalUrl)

                    //txObserver.bucket
                }
            }

            override fun onProgressChanged(id: Int, current: Long, total: Long) {
                /*val done = (((current / total) * 100.0) as Float) as Int
                Log.d(TAG, "AWS ID: $id, percent done = $done")*/
            }

            override fun onError(id: Int, ex: Exception) {
                // Handle errors
            }
        })
    }

    private fun updateProf(imageUrl: String) {
        val mAuthToken = SharedPreferencesHelper.getString(
            mcontext,
            Constants.SharedPrefs.User.AUTH_TOKEN,
            ""
        )
        val mServiceManId =
            SharedPreferencesHelper.getString(mcontext, Constants.SharedPrefs.User.USER_ID, "")

        val email = view!!.emailET.text.toString()
        val mobile = view!!.mobileET.text.toString()
        val fullname = view!!.fullnameET.text.toString()
        val dob = getFormattedDateRequest(view!!.edt_age.text.toString().trim())
        val address1 = view!!.apartmentNameET1.text.toString()
        val address2 = view!!.streetNameET1.text.toString()
        val city = view!!.landMarkNameET1.text.toString()
        val pincode = view!!.pinCodeET1.text.toString()
        val state = view!!.tvGovernorate_EditProfLay.text.toString()

        rq.serviceproviderId = SharedPreferencesHelper.getString(
            mcontext,
            Constants.SharedPrefs.User.USER_ID,
            "0"
        )
        rq.serviceproviderName = fullname
        rq.serviceproviderMobile = mobile
        rq.serviceproviderCountryCode = "965"
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
        rq.serviceproviderState = state
        rq.serviceproviderImage = imageUrl
        rq.serviceproviderCountry = country
//        rq.serviceproviderCountryCode = country_id

        if (fullname.isEmpty()) {
            showToastMsg(mcontext.resources.getString(R.string.enter_the_name))
        } else if (mobile.isEmpty() || mobile.length != 8) {
            showToastMsg(mcontext.resources.getString(R.string.enter_valid_mobile_number))
        } else if (dob.isEmpty()) {
            showToastMsg(mcontext.resources.getString(R.string.please_select_a_dob))
        } else {
            if (mAuthToken != null) {
                showProgressDialog("")
                mPresenter.updateProfile(mAuthToken, rq)
            }
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isVisiblee = isVisibleToUser
        if (isVisiblee && isStarted) {
            loadProfile()
        } else {
            destroyDialog()
        }
    }

    private fun getStates() {
        val homeCall = APIClient.getApiInterface().states
        homeCall.enqueue(object : Callback<StatesResponse> {
            override fun onResponse(
                call: Call<StatesResponse>,
                response: Response<StatesResponse>,
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
        val myStateList: ArrayList<String>? = ArrayList()
        if (mStatesList?.size!! > 0) {
            for (i in 0 until mStatesList!!.size) {
                myStateList?.add(mStatesList?.get(i)?.name!!)
            }
        }
        val adapter = spinnerAdapter(mcontext, R.layout.custom_state_spinner_list)
        myStateList?.let { adapter.addAll(it) }
        adapter.add("STATE")
        view!!.txt_view_state.adapter = adapter
        view!!.txt_view_state.setSelection(adapter.count)
    }

    private fun loadProfile() {
        showProgressDialog("")
        /*val isLoggedIn = localStorage(this@ProfileActivity).isLoggedIn*/
        val isLoggedIn: Boolean =
            !SharedPreferencesHelper.getString(mcontext, Constants.SharedPrefs.User.AUTH_TOKEN, "")
                .isNullOrEmpty() //localStorage(context).isLoggedIn
        if (isLoggedIn) {
            myUserId = SharedPreferencesHelper.getString(
                mcontext,
                Constants.SharedPrefs.User.USER_ID,
                "0"
            )!!
                .toInt()//localStorage(this@ProfileActivity).loggedInUser.customerId
            val callServiceProviders = APIClient.getApiInterface().getCompleteProfile(myUserId)
            callServiceProviders.enqueue(object : Callback<CustomerCompleteProfile> {
                override fun onResponse(
                    call: Call<CustomerCompleteProfile>,
                    response: Response<CustomerCompleteProfile>,
                ) {
                    destroyDialog()
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data!!.data != null) {
                            profile = data.data
                            localStorage(mcontext).saveCompleteCustomer(profile)

                            rq.serviceproviderExperience = profile.experience
                            rq.serviceproviderWorkingAs = profile.workingAs
                            view!!.txt_view_working_as.text = profile.workingAs
                            rq.serviceproviderId = profile.id!!.toString()
                            rq.serviceproviderName = profile.name
                            rq.serviceproviderLastname = profile.lastname
                            rq.serviceproviderDob = profile.dob
                            if (profile.dob != null)
                                view!!.edt_age.text =
                                    getFormattedDateResponse(profile.dob.toString())
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
                            imageUri = profile.image
                            view!!.fullnameET.setText(profile.name.toString())
                            if (profile.state != null) {
                                view!!.tvGovernorate_EditProfLay.text = profile.state.toString()
                            } else
                                view!!.tvGovernorate_EditProfLay.text = ""
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
                            getCountry()
                        }
                    } else {
                        showToastMsg("Error in loading Complete profile")
                    }
                }

                override fun onFailure(call: Call<CustomerCompleteProfile>, t: Throwable) {
                    destroyDialog()
                }
            })
        }
    }

    fun bindOfferedServiceRecyclerView(body: ServicesResponse?) {
        val linearLayoutManager = LinearLayoutManager(mcontext)
        servicemanSelectedServiceAdapter = ServiceOfferAdapter(body?.data, mcontext, this)
        view!!.recycler_view.layoutManager = linearLayoutManager
        view!!.recycler_view.adapter = servicemanSelectedServiceAdapter
        view!!.recycler_view.isNestedScrollingEnabled = false
    }

    private fun getPreServices() {
        APIClient.token =
            SharedPreferencesHelper.getString(mcontext, Constants.SharedPrefs.User.AUTH_TOKEN, "")
        val homeCall = APIClient.getApiInterface().getExistingServiceDetailsWithHeaderAndExperience(
            "Bearer " + APIClient.token,
            SharedPreferencesHelper.getString(mcontext, Constants.SharedPrefs.User.USER_ID, "0")!!
                .toInt()
        )
        homeCall.enqueue(object : Callback<ServicesResponse> {
            override fun onResponse(
                call: Call<ServicesResponse>,
                response: Response<ServicesResponse>,
            ) {
                if (response.isSuccessful) {
                    bindOfferedServiceRecyclerView(response.body()!!)
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

    fun showToastMsg(msg: String) {
        Toast.makeText(mcontext, msg, Toast.LENGTH_LONG).show()
    }

    var addressType = "1"

    private fun openAddressTypePopUp() {
        val dialog = Dialog(mcontext, android.R.style.Theme_Translucent_NoTitleBar)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val window = dialog.window
        dialog.setContentView(R.layout.addresstype_popup)
        val wlp = window!!.attributes
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
                tvAddressType_EditProfLay.text = mcontext.resources.getString(R.string.house)
                etBuildingNo_EditProfLay.hint = mcontext.resources.getString(R.string.house_no)
                etFloorNo_EditProfLay.visibility = View.GONE
                etAptOffNo_EditProfLay.visibility = View.GONE
            } else if (checkedId == R.id.rbApartment_AddressTypePopUp) {
                etBuildingNo_EditProfLay.setText("")
                etFloorNo_EditProfLay.setText("")
                etAptOffNo_EditProfLay.setText("")
                etAvenue_EditProfLay.setText("")
                addressType = "2"
                tvAddressType_EditProfLay.text = mcontext!!.resources.getString(R.string.apartment)
                etBuildingNo_EditProfLay.hint = mcontext!!.resources.getString(R.string.building_no)
                etAptOffNo_EditProfLay.hint = mcontext!!.resources.getString(R.string.apartment)
                etFloorNo_EditProfLay.visibility = View.VISIBLE
                etAptOffNo_EditProfLay.visibility = View.VISIBLE
            } else if (checkedId == R.id.rbOffice_AddressTypePopUp) {
                etBuildingNo_EditProfLay.setText("")
                etFloorNo_EditProfLay.setText("")
                etAptOffNo_EditProfLay.setText("")
                etAvenue_EditProfLay.setText("")
                addressType = "3"
                tvAddressType_EditProfLay.text = mcontext!!.resources.getString(R.string.office)
                etBuildingNo_EditProfLay.hint = mcontext!!.resources.getString(R.string.building_no)
                etAptOffNo_EditProfLay.hint = mcontext!!.resources.getString(R.string.office_no)
                etFloorNo_EditProfLay.visibility = View.VISIBLE
                etAptOffNo_EditProfLay.visibility = View.VISIBLE
            }
        }
        ivClose_AddressPopUp.setOnClickListener { v: View? -> dialog.dismiss() }
        btnContinue_AddressPopUp.setOnClickListener { v: View? -> dialog.dismiss() }
        dialog.show()
    }

    override fun onDPUpdated(mData: ImageUpdateResponse) {
        destroyDialog()
        if (mData.status == 1) {
            val mServiceManId =
                SharedPreferencesHelper.getString(mcontext, Constants.SharedPrefs.User.USER_ID, "")
            if (mServiceManId != null) {
                mPresenter.getCompleteProfile(mServiceManId)
            }
            showToastMsg(mData.message!!)
        }
    }

    override fun onFailed(msg: String) {
        destroyDialog()
        showToastMsg(msg)
    }

    override fun onProfileUpdated(mData: CustomerCompleteProfileAfterUpdate) {
        destroyDialog()
        if (mData.status == 1) {
            showToastMsg(mData.message)
            setDisable()
            loadProfile()
        } else {
            showToastMsg(mData.message)
        }
    }

    override fun onCompleteProfileReceived(metaData: CustomerCompleteProfile) {
        if (metaData.status == 1) {
            localStorage(mcontext).saveCompleteCustomer(metaData.data)
            activity?.finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                val resultUri = result.uri
                imageUri = resultUri.path.toString()
                serviceManProfile.setImageURI(resultUri)
                isNewImage = true

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }

    fun getCaptureImageOutputUri(context: Context): Uri? {
        var outputFileUri: Uri? = null
        val getImage: File = context.externalCacheDir!!
        outputFileUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(
                context, BuildConfig.APPLICATION_ID.toString() + ".provider",
                File(getImage.path, "pickImageResult.jpeg")
            )
        } else {
            Uri.fromFile(File(getImage.path, "pickImageResult.jpeg"))
        }
        return outputFileUri
    }

    private fun getCountry() {
        val homeCall = APIClient.getApiInterface().country
        homeCall.enqueue(object : Callback<CountryPojo> {
            override fun onResponse(
                call: Call<CountryPojo>,
                response: Response<CountryPojo>,
            ) {
                if (response.isSuccessful) {

                    bindDataCountry(response.body()!!)
                }
            }

            override fun onFailure(call: Call<CountryPojo>, t: Throwable) {

            }
        })
    }

    fun bindDataCountry(countryPojo: CountryPojo) {
        mCountryList = countryPojo.countryPojoArray
        val mCountryListDummy: ArrayList<String> = ArrayList()
        if (mCountryList?.size!! > 0) {
            for (i in mCountryList!!.indices) {
                mCountryListDummy.add(mCountryList?.get(i)?.name!!)
            }
        }
        val adapter = spinnerAdapter(this.context, R.layout.custom_state_spinner_list)
        adapter.addAll(mCountryListDummy)
        adapter.add(context?.resources?.getString(R.string.india))
        spCountry1_EditProfLay.adapter = adapter
        spCountry1_EditProfLay.setSelection(adapter.count)

        for (i in 0 until mCountryList!!.size) {
            when (mCountryList!![i]?.name) {
                profile.country -> {
                    country_id = mCountryList!![i]!!.phonecode.toString()
                    country = mCountryList!![i]!!.name.toString()
                    view!!.spCountry1_EditProfLay.setSelection(i)
                }
            }
        }
    }

    override fun onEditClick(position: Int, serviceWithSubService: ServiceWithSubService?) {

    }

    override fun onDeleteClick(position: Int, serviceData: ServiceData?) {}
}
