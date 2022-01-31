package com.in10mServiceMan.ui.activities.profile

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.in10mServiceMan.models.*
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.tracking_map.MapTrackingActivity
import com.in10mServiceMan.ui.apis.APIClient
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.View
import com.amazonaws.mobile.client.AWSMobileClient
import com.in10mServiceMan.models.viewmodels.ServiceWithSubService
import com.in10mServiceMan.ui.activities.profile_services.ProfileServices
import com.in10mServiceMan.ui.activities.services.ServicesActivity
import com.in10mServiceMan.ui.activities.services.ServicesResponse
import com.in10mServiceMan.ui.activities.sub_services.SubServicesActivity
import com.in10mServiceMan.ui.apis.AmazonUploadTask
import com.in10mServiceMan.ui.base.In10mBaseActivity
import com.in10mServiceMan.ui.interfaces.EditTextValuePass
import com.in10mServiceMan.ui.listener.EditSubServicesListener
import com.in10mServiceMan.ui.listener.RemoveServiceExperienceListener
import com.in10mServiceMan.utils.*

import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class ProfileActivity : In10mBaseActivity(), ImageFetcher.OnImageAddedCallback,
    RemoveServiceExperienceListener, EditTextValuePass, EditSubServicesListener, IProfileView {
    override fun onCompleteProfileReceived(metaData: CustomerCompleteProfile) {
        if (metaData.status == 1) {
            localStorage(this).saveCompleteCustomer(metaData.data)
            finish()
        }
    }

    override fun onProfileUpdated(mData: CustomerCompleteProfileAfterUpdate) {
        destroyDialog()
        if (mData.status == 1) {
            val mAuthToken =
                SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")
            val mServiceManId =
                SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "")

            if (imageUri.isNotEmpty()) {
                showProgressDialog("")
                mPresenter.updateProfilePicture(
                    mAuthToken.toString(),
                    mServiceManId.toString(), imageUri
                )
            } else {
                mPresenter.getCompleteProfile(mServiceManId.toString())
            }
        } else {
            ShowToast(mData.message)
        }
    }

    override fun onDPUpdated(mData: ImageUpdateResponse) {
        destroyDialog()
        if (mData.status == 1) {
            val mServiceManId =
                SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "")
            mPresenter.getCompleteProfile(mServiceManId.toString())
            ShowToast(mData?.message)
        }
    }

    override fun onFailed(msg: String) {
        destroyDialog()
        ShowToast(msg)
    }

    override fun removeService(position: Int) {
        if (position < serviceWithSubServices.size)
            serviceWithSubServices.removeAt(position)
        loadServicemanExperience()
    }

    override fun onDataPass(serviceId: Int, EditTextValue: String?) {
        val serviceWithSubService = getServiceWithId(serviceId)
        for (i in serviceWithSubServices.indices) {
            val aa = serviceWithSubServices[i]
            if (aa.serviceId == serviceId) {

                var `val`: Int
                `val` = try {
                    Integer.parseInt(EditTextValue)
                } catch (nfe: NumberFormatException) {
                    1
                }

                //    serviceWithSubService!!.experience = `val`
                serviceWithSubServices[i] = serviceWithSubService!!
            }
        }
    }

    override fun onEditClick(position: Int, serviceWithSubService: ServiceWithSubService?) {
        showProgressDialog("")
        APIClient.getApiInterface()!!.getServiceDetails(serviceWithSubService?.service?.serviceId!!)
            .enqueue(object : Callback<ResponseServiceWithSubService> {
                override fun onResponse(
                    call: Call<ResponseServiceWithSubService>,
                    response: Response<ResponseServiceWithSubService>
                ) {
                    destroyDialog()
                    if (response.isSuccessful && (if (response.body() != null) response.body()!!.data.size else 0) > 0) {
                        val json = Gson().toJson(response.body()!!.data[0])
                        val intent = Intent(this@ProfileActivity, SubServicesActivity::class.java)
                        intent.putExtra("service", json)
                        startActivityForResult(intent, REQUEST_CODE_SELECTION_SERVICES_SUBSERVICES)
                    }

                }

                override fun onFailure(call: Call<ResponseServiceWithSubService>, t: Throwable) {
                    destroyDialog()
                }
            })
    }

    private fun getServiceWithId(serviceId: Int): Service? {
        for (i in serviceWithSubServices.indices) {
            val aa = serviceWithSubServices[i]
            if (serviceId == aa.serviceId) {
                return aa
            }
        }
        return null
    }


    private var servicemanSelectedServiceAdapter: ServiceOfferAdapter? = null
    private var serviceWithSubServices = java.util.ArrayList<Service>()
    private val REQUEST_CODE_SELECTION_SERVICES_SUBSERVICES = 111

    private var myUserId = 0
    var mImageUploadPath: String? = null

    override fun onImageAdded(path: String, requestCode: Int) {
        mImageUploadPath = path
        PicassoCache.getPicassoInstance(this@ProfileActivity).load(File(mImageUploadPath)).fit()
            .into(serviceManProfile)

        if (mImageUploadPath != null && mImageUploadPath!!.isNotEmpty()) {
            showProgressDialog("")
            uploadImages(mImageUploadPath, fullnameET.text.toString(), mobileET.text.toString())
        }

    }

    private fun uploadImages(mFilePath: String?, mName: String, mEmail: String) {
        if (mFilePath != null) {

            var mAmazonUploadTask = AmazonUploadTask(
                arrayListOf(mFilePath),
                object : AmazonUploadTask.AmazonUploadTaskListener {
                    override fun uploadTaskProgress(mCurrentUploadIndex: Int, mSize: Int) {

                    }

                    override fun uploadTaskFailed(mMessage: String) {
                        destroyDialog()
                        Toast.makeText(
                            this@ProfileActivity,
                            "Failed to upload images",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun uploadTaskSuccess(
                        mUrlList: ArrayList<String>,
                        mUrlHashMap: HashMap<String, String>
                    ) {
                        destroyDialog()
                        mImageUploadPath = mUrlList.get(0)
                        rq.serviceproviderImage = mImageUploadPath
                    }
                },
                Constants.Bucket.USER_FOLDER,
                mEmail
            )
            mAmazonUploadTask.execute()
        }
    }

    var profile: CompleteProfile = CompleteProfile()
    internal var rq = RequestUpdateServiceMan()
    private val imageFetcher = ImageFetcher()
    var imageUri: String = ""
    private lateinit var alertDialog1: AlertDialog
    private val mPresenter = ProfilePresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)

        btnSaveProfile.visibility = View.GONE
        btnEditProfile.visibility = View.VISIBLE
        btnChangeImage.visibility = View.GONE
        fullnameET.isEnabled = false
        mobileET.isEnabled = false
        edt_age.isEnabled = false
        emailET.isEnabled = false

        btnEditProfile.setOnClickListener {
            btnSaveProfile.visibility = View.VISIBLE
            btnEditProfile.visibility = View.GONE
            btnChangeImage.visibility = View.VISIBLE
            fullnameET.isEnabled = true
            mobileET.isEnabled = true
            edt_age.isEnabled = true
            emailET.isEnabled = true
        }

        /*Crashlytics.getInstance().crash()*/
        loadProfile()
        getPreServices()

        button_close.setOnClickListener {
            finish()
        }
        btnNavServices.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, ProfileServices::class.java))
        }
        btnSaveProfile.setOnClickListener {

            val mAuthToken =
                SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")
            val mServiceManId =
                SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "")

            /*edt_age1.setOnTouchListener(object: View.OnTouchListener {
               override fun onTouch(v:View, event:MotionEvent):Boolean {
                    if (event.action === MotionEvent.ACTION_UP)
                    {
                        val cal = Calendar.getInstance()
                        val startDateYear = cal.get(Calendar.YEAR)
                        val startDateMonth = cal.get(Calendar.MONTH)
                        val startDateDay = cal.get(Calendar.DAY_OF_MONTH)

                        val dpd_startdate = DatePickerDialog(this@ProfileActivity, R.style.CalendarThemeOne, DatePickerDialog.OnDateSetListener { v, myear, mmonth, mdayOfMonth ->
                            val month = mmonth + 1

                            edt_age.setText("$mdayOfMonth-$month-$myear")//"$myear-$month-$mdayOfMonth"


                        }, startDateYear, startDateMonth, startDateDay)
                        // dpd_startdate.datePicker.minDate = System.currentTimeMillis() - 1000
                        dpd_startdate.show()
                        return true
                    }
                    return false
                }
            })*/
            edt_age.setOnClickListener()
            {
                val cal = Calendar.getInstance()
                val startDateYear = cal.get(Calendar.YEAR)
                val startDateMonth = cal.get(Calendar.MONTH)
                val startDateDay = cal.get(Calendar.DAY_OF_MONTH)

                val dpd_startdate = DatePickerDialog(
                    this@ProfileActivity,
                    R.style.CalendarThemeOne,
                    DatePickerDialog.OnDateSetListener { v, myear, mmonth, mdayOfMonth ->
                        val month = mmonth + 1

                        edt_age.setText("$month-$mdayOfMonth-$myear")//"$myear-$month-$mdayOfMonth"


                    },
                    startDateYear,
                    startDateMonth,
                    startDateDay
                )
                // dpd_startdate.datePicker.minDate = System.currentTimeMillis() - 1000
                dpd_startdate.show()
            }

            val email = emailET.text.toString().trim()
            val mobile = mobileET.text.toString().trim()
            val fullname = fullnameET.text.toString().trim()
            val dob = getFormattedDateRequest(edt_age.text.toString().trim())
            if (fullname.isEmpty()) {
                ShowToast(resources.getString(R.string.enter_the_name))
            } else if (mobile.isEmpty() || mobile.length != 10) {
                ShowToast(resources.getString(R.string.enter_valid_mobile_number))
            } else if (dob.isEmpty()) {
                ShowToast(resources.getString(R.string.please_select_a_dob))
            } else if (!isValidEmail(email)) {
                ShowToast(resources.getString(R.string.enter_valid_email_address))
            } else {

                rq.serviceproviderId =
                    SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "0")
                rq.serviceproviderName = fullname
                rq.serviceproviderMobile = mobile
                rq.serviceproviderCountryCode = "+1"
                rq.serviceproviderEmail = email
                rq.serviceproviderLastname = profile.lastname
                rq.serviceproviderAddress1 = profile.address1
                rq.serviceproviderAdddress2 = profile.adddress2
                rq.serviceproviderStreetName = profile.streetName
                rq.serviceproviderPincode = profile.pincode
                rq.serviceproviderWorkingAs = profile.workingAs
                rq.serviceproviderExperience =
                    profile.experience//edit_text_total_eperiance.text.toString()
                rq.serviceproviderDob = dob
                rq.serviceproviderCity = profile.city
                rq.serviceproviderCountry = profile.country
                rq.serviceproviderCivilId = profile.civilId
                rq.serviceproviderLanguage = profile.language
                rq.serviceproviderLatitude = profile.latitude
                rq.serviceproviderLongitude = profile.longitude
                rq.serviceproviderGender = profile.gender

                showProgressDialog("")
                mPresenter.updateProfile(mAuthToken.toString(), rq)
            }
            /*else if (imageUri.isEmpty()) {
            ShowToast("Please select an Image")
        }*/
        }
        initAmazonAWS()
        btnChangeImage.setOnClickListener {
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).setFixAspectRatio(true)
                .setOutputCompressQuality(50).start(this@ProfileActivity)
        }
        autocomplete_choose_service.setOnClickListener { v ->
            val intentStartServiceSelection =
                Intent(this@ProfileActivity, ServicesActivity::class.java)
            startActivityForResult(
                intentStartServiceSelection,
                REQUEST_CODE_SELECTION_SERVICES_SUBSERVICES
            )
        }

        choose_service.setOnClickListener { v ->

            val intentStartServiceSelection =
                Intent(this@ProfileActivity, ServicesActivity::class.java)
            startActivityForResult(
                intentStartServiceSelection,
                REQUEST_CODE_SELECTION_SERVICES_SUBSERVICES
            )
            //startActivityForResult(REQUEST_CODE_SELECTION_SERVICES_SUBSERVICES,new Intent(mContext,SelectServiceActivity.class));
        }
/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mobileET.addTextChangedListener(PhoneNumberFormattingTextWatcher("US"))
        }*/


    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return if (target == null) {
            false
        } else {
            android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    private fun getPreServices() {
        APIClient.Token =
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")
        val homeCall = APIClient.getApiInterface().getExistingServiceDetailsWithHeaderAndExperience(
            "Bearer " + APIClient.Token,
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "0")!!
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

    private fun loadExistingServices() {
        APIClient.getApiInterface()!!.getExistingServiceDetails(myUserId)
            .enqueue(object : Callback<HomeService> {
                override fun onResponse(call: Call<HomeService>, response: Response<HomeService>) {
                    if (response.isSuccessful && response.body() != null && response.body()!!.data != null && response.body()!!.data.size > 0) {
                        for (service in response.body()!!.data) {
                            /*val serviceWithSubService = ServiceWithSubService()
                            serviceWithSubService.service = service
                            serviceWithSubService.subServices = service.subService
                            if (service.subService.size > 0)
                                serviceWithSubService.experience = Integer.parseInt(service.subService[0].createdAt)
                            serviceWithSubServices.add(serviceWithSubService)*/
                            val serviceWithSubService = Service()
                            serviceWithSubService.subService = service.subService
                            /*if (service.subService.size > 0)
                                serviceWithSubService.experience = Integer.parseInt(service.subService[0].createdAt)*/
                            serviceWithSubServices.add(serviceWithSubService)
                        }
                        loadServicemanExperience()
                    }
                }

                override fun onFailure(call: Call<HomeService>, t: Throwable) {

                }
            })
    }

    private fun saveServices() {
        if (serviceWithSubServices.size > 0)
            run {

                val serviceWithServiceMEN = java.util.ArrayList<RequestLinkServiceWithServiceMan>()
                for (aa in serviceWithSubServices) {
                    /*for (bb in aa.subServices) {

                        val cc = RequestLinkServiceWithServiceMan()
                        cc.serviceId = aa.service.serviceId!!.toString()
                        cc.subServiceId = bb.id!!.toString()
                        cc.status = "1"
                        cc.totalExperience = aa.experience.toString()
                        cc.userId = myUserId.toString()
                        serviceWithServiceMEN.add(cc)
                    }*/
                }
                callApiTOLinkServicesAndSubService(serviceWithServiceMEN)
            }
        else {
            gotohome()
        }
    }

    private fun gotohome() {
        startActivity(Intent(this@ProfileActivity, MapTrackingActivity::class.java))
        finishAffinity()
    }

    private fun callApiTOLinkServicesAndSubService(servicemanBodyModel: java.util.ArrayList<RequestLinkServiceWithServiceMan>) {
        val loginServiceInterface = APIClient.getApiInterface()

        Log.i("eeeLINK", Gson().toJson(servicemanBodyModel))

        val call = loginServiceInterface!!.linkServiceAndSubService(servicemanBodyModel)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {

                // Toast.makeText(this@ProfileActivity, "Services updated successfully.", Toast.LENGTH_LONG).show()
                gotohome()
                // dataPasser.onDataPass(1);
                if (response.isSuccessful) {

                } else {
                    try {
                        Log.i("eeeLINKerror2", response.errorBody()!!.string())
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                gotohome()
                // Toast.makeText(this@ProfileActivity, "Something went wrong.. Please try again later.", Toast.LENGTH_LONG).show()
                // dataPasser.onDataPass(1);
            }
        })
    }

    private fun getValueImage() {
        imageFetcher.showSelectImageDialog(this, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        imageFetcher.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SELECTION_SERVICES_SUBSERVICES && resultCode == Activity.RESULT_OK) {
            if (data?.getStringExtra("service") != null) {
                val serviceWithSubService =
                    Gson().fromJson(data.getStringExtra("service"), Service::class.java)
                if (!checkIfAlreadyExist(serviceWithSubService)) {
                    serviceWithSubServices.add(serviceWithSubService)
                }
                loadServicemanExperience()
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri = result.uri
                imageUri = resultUri.path.toString()
                serviceManProfile.setImageURI(resultUri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }

    private fun checkIfAlreadyExist(serviceWithSubService: Service): Boolean {
        for (i in serviceWithSubServices.indices) {
            val aa = serviceWithSubServices[i]
            if (aa.serviceId == serviceWithSubService.serviceId) {
                serviceWithSubServices[i] = serviceWithSubService
                return true
            }
        }
        return false
    }

    fun loadServicemanExperience() {
        if (serviceWithSubServices.size == 0) {
            choose_service.visibility = View.VISIBLE
            autocomplete_choose_service.visibility = View.GONE
            recycler_view.visibility = View.GONE
        } else {
            choose_service.visibility = View.GONE
            autocomplete_choose_service.visibility = View.VISIBLE
            recycler_view.visibility = View.VISIBLE
        }
        Log.i("eeeServiceManServices", Gson().toJson(serviceWithSubServices))
        // servicemanSelectedServiceAdapter = ServicemanSelectedServiceAdapter(serviceWithSubServices, this, this, this, this)
        servicemanSelectedServiceAdapter!!.notifyDataSetChanged()
    }

    private fun initAmazonAWS() {
        AWSMobileClient.getInstance().initialize(this) {
            Log.d(
                "eeeYourMainActivity",
                "AWSMobileClient is instantiated and you are connected to AWS!"
            )
        }.execute()
    }

    private fun somethingWentWrong(msg: String = "Something went wrong") {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    private fun loadProfile() {
        /*val isLoggedIn = localStorage(this@ProfileActivity).isLoggedIn*/
        var isLoggedIn = false //localStorage(context).isLoggedIn
        isLoggedIn =
            !SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")
                .isNullOrEmpty()

        if (isLoggedIn) {
            myUserId =
                SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "0")!!
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
                            if (profile.experience != null) edit_text_total_eperiance.setText(
                                profile.experience.toString()
                            )
                            rq.serviceproviderWorkingAs = profile.workingAs
                            txt_view_working_as.text = profile.workingAs
                            rq.serviceproviderId = profile.id!!.toString()
                            rq.serviceproviderName = profile.name
                            rq.serviceproviderLastname = profile.lastname
                            rq.serviceproviderDob = profile.dob
                            if (profile.dob != null)
                                edt_age.setText(getFormattedDateResponse(profile.dob.toString()))
                            rq.serviceproviderMobile = profile.mobile
                            rq.serviceproviderCountryCode = profile.countryCode
                            rq.serviceproviderEmail = profile.email
                            rq.serviceproviderGender = profile.gender
                            if (profile.gender == "M") txt_view_gender.text =
                                resources.getString(R.string.male) else txt_view_gender.text =
                                resources.getString(
                                    R.string.female
                                )
                            rq.serviceproviderAddress1 = profile.address1
                            rq.serviceproviderAdddress2 = profile.adddress2
                            rq.serviceproviderCity = profile.city
                            rq.serviceproviderCountry = profile.country
                            rq.serviceproviderLanguage = "en"
                            rq.serviceproviderLatitude = profile.latitude
                            rq.serviceproviderLongitude = profile.longitude

                            rq.serviceproviderImage =
                                if (profile.image.isEmpty()) "http://35.180.58.90/development/in10m/in10m/public/images/users/default_user_avatar.png" else profile.image

                            //rq.setServiceproviderImage(profile.image)
                            rq.serviceproviderRating = profile.rating
                            rq.serviceproviderCivilId = profile.civilId
                            rq.serviceproviderLanguage = profile.language

                            rq.serviceproviderPincode = profile.pincode
                            rq.serviceproviderStreetName = profile.streetName

                            if (profile.lastname != null)
                                fullnameET.setText(profile.name.toString() + " " + profile.lastname)
                            else
                                fullnameET.setText(profile.name.toString())

                            mobileET.setText(profile.mobile.toString())
                            emailET.setText(if (profile.email == null) "" else profile.email)
                            apartmentNameET1.setText(if (profile.address1 == null) "" else profile.address1)
                            streetNameET1.setText(if (profile.streetName == null) "" else profile.streetName)
                            landMarkNameET1.setText(if (profile.adddress2 == null) "" else profile.adddress2)
                            pinCodeET1.setText(if (profile.pincode == null) "" else profile.pincode)
                            txt_view_working_as.text = profile.workingAs

                            Picasso.get().load(profile.image) // web image url
                                .fit().centerInside()
                                .rotate(0f)                    //if you want to rotate by 90 degrees give 90f
                                .error(R.drawable.dummy_user)
                                .placeholder(R.drawable.dummy_user)
                                .into(serviceManProfile)

                            txt_view_gender.setOnClickListener { view -> CreateAlertDialogWithRadioButtonGroup() }
                        }
                    } else {
                        Showtoast(resources.getString(R.string.error_in_loading))
                    }
                }

                override fun onFailure(call: Call<CustomerCompleteProfile>, t: Throwable) {

                }
            })
        }
    }

    private fun CreateAlertDialogWithRadioButtonGroup2() {

        val values = arrayOf<CharSequence>(" Individual ", " Company ")
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Select working as")

        builder.setSingleChoiceItems(values, -1) { dialog, item ->
            when (item) {
                0 -> {
                    txt_view_working_as.text = "Individual"
                }
                1 -> {
                    txt_view_working_as.text = "Company"
                }
            }
            alertDialog1.dismiss()
        }
        alertDialog1 = builder.create()
        alertDialog1.show()

    }

    fun CreateAlertDialogWithRadioButtonGroup() {
        // Toast.makeText(getActivity(),"hereDROPDOWN",Toast.LENGTH_SHORT).show();

        val values = arrayOf<CharSequence>(
            resources.getString(R.string.male),
            resources.getString(R.string.female)
        )

        val builder = AlertDialog.Builder(this)

        builder.setTitle(resources.getString(R.string.select_your_gender))

        builder.setSingleChoiceItems(values, -1) { dialog, item ->
            when (item) {
                0 -> {
                    txt_view_gender.text = resources.getString(R.string.male)

                }
                1 -> {
                    txt_view_gender.text = resources.getString(R.string.female)

                }
            }
            alertDialog1.dismiss()
        }
        alertDialog1 = builder.create()
        alertDialog1.show()

    }

    fun Showtoast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    fun bindOfferedServiceRecyclerView(body: ServicesResponse?) {
        val linearLayoutManager = LinearLayoutManager(this@ProfileActivity)
        servicemanSelectedServiceAdapter = ServiceOfferAdapter(body?.data, this@ProfileActivity)
        recycler_view.layoutManager = linearLayoutManager
        recycler_view.adapter = servicemanSelectedServiceAdapter
        recycler_view.isNestedScrollingEnabled = false
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

    override fun onBackPressed() {
        if (btnSaveProfile.visibility == View.VISIBLE) {
            btnEditProfile.visibility = View.VISIBLE
            btnSaveProfile.visibility = View.GONE
            btnChangeImage.visibility = View.GONE
            fullnameET.isEnabled = false
            mobileET.isEnabled = false
            edt_age.isEnabled = false
            emailET.isEnabled = false
        } else {
            super.onBackPressed()
        }
    }
}
