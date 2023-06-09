package com.in10mServiceMan.ui.activities.dashboard

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.media.MediaPlayer
import android.os.*
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.database.*
import com.google.gson.JsonElement
import com.in10mServiceMan.R
import com.in10mServiceMan.enums.BookingStatus
import com.in10mServiceMan.models.*
import com.in10mServiceMan.models.viewmodels.CommonApiResponse
import com.in10mServiceMan.ui.activities.BackButtonHandler
import com.in10mServiceMan.ui.activities.CallScreenActivity
import com.in10mServiceMan.ui.activities.MenuNavigation
import com.in10mServiceMan.ui.activities.SinchService
import com.in10mServiceMan.ui.activities.about.AboutActivity
import com.in10mServiceMan.ui.activities.company_pros.CompanyPros
import com.in10mServiceMan.ui.activities.contact_us.ContactUs
import com.in10mServiceMan.ui.activities.home.NavigationAdapter
import com.in10mServiceMan.ui.activities.my_bookings.service_history.ServiceHistoryActivity
import com.in10mServiceMan.ui.activities.payment.PaymentInitilizeResponse
import com.in10mServiceMan.ui.activities.payment.PaymentRequestClass
import com.in10mServiceMan.ui.activities.privacy_policy.PrivacyPolicyActivity
import com.in10mServiceMan.ui.activities.rating.CustomerRating
import com.in10mServiceMan.ui.activities.rating.ReviewsActivity
import com.in10mServiceMan.ui.activities.rating.ReviewsResponse
import com.in10mServiceMan.ui.activities.sign_in.LoginActivity
import com.in10mServiceMan.ui.activities.termsandcondition.TermsAndCondition
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.ui.base.In10mBaseActivity
import com.in10mServiceMan.ui.complete_profile_api.CompleteProfilePresenter
import com.in10mServiceMan.ui.complete_profile_api.CompleteProfileResponse
import com.in10mServiceMan.ui.complete_profile_api.ICompleteProfileView
import com.in10mServiceMan.ui.fragments.home.ServiceManCallBack
import com.in10mServiceMan.utils.*
import com.in10mServiceMan.utils.SharedPreferencesHelper.getBoolean
import com.mapbox.api.directions.v5.models.DirectionsResponse
import com.mapbox.api.directions.v5.models.DirectionsRoute
import com.mapbox.geojson.Point
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute
//import com.onesignal.OSDeviceState
import com.sinch.android.rtc.MissingPermissionException
import com.sinch.android.rtc.SinchError
import com.squareup.picasso.Picasso
import com.yarolegovich.slidingrootnav.SlidingRootNav
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_invoice.*
import kotlinx.android.synthetic.main.activity_map_tracking.*
import kotlinx.android.synthetic.main.arrived_lay.*
import kotlinx.android.synthetic.main.drawyer_layout.*
import kotlinx.android.synthetic.main.home_bottom_buttons.*
import kotlinx.android.synthetic.main.home_bottom_buttons.btnDirection_HomeBottomBtn
import kotlinx.android.synthetic.main.startservice_lay.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : In10mBaseActivity(), NavigationAdapter.NavigationCallbacks,
    OnMapReadyCallback, Mapss.RouteStatus,
    ServiceManCallBack, GoogleMap.OnCameraMoveStartedListener,
    GoogleMap.OnCameraMoveListener,
    GoogleMap.OnCameraMoveCanceledListener,
    GoogleMap.OnCameraIdleListener, SinchService.StartFailedListener, ICompleteProfileView {

    var permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.MODIFY_AUDIO_SETTINGS,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.CALL_PHONE
    )
    private var permsRequestCode = 1
    private var polyline: Polyline? = null
    var currentWorkStatus: Int = 0
    var thread = Thread()

    private var slidingRootNav: SlidingRootNav? = null
    private var handler = Handler()
    private val navigationDelay = 180L
    private var isLocationSelected = false
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    val mZoomLevel = 16f
    var mCurrLocationMarker: Marker? = null
    private var destinationMarker: Marker? = null
    var map: GoogleMap? = null
    private var map2: GoogleMap? = null

    /*var serviceManList = Dummy.getServiceManList()*/
    var userLocation = LatLng(29.374009, 47.976461)
    var destinationLocation = LatLng(29.374009, 47.976461)
    var mapss: Mapss? = null
    private val REQUEST_CHECK_SETTINGS = 1
    var currentStatus = 1
    var userid = 0
    lateinit var mDatabase: DatabaseReference//Serviceman node
    lateinit var mDatabaseCustomer: DatabaseReference

    lateinit var mDatabasePayment: DatabaseReference
    lateinit var dbServiceMan: DatabaseReference

    var request: firebaseRequestModel? = null
    var freeEstimate = 1
    var estimationFee = 0.0
    var bookingId = 0
    var bookingAccpted = false
    private lateinit var locationCallback: LocationCallback
    var freezCurrentLocation = false
    var dbUpdated = false
    private lateinit var buttonHandler: BackButtonHandler
    private var showingDashBoard = false
    var myBookingId: String? = ""
    var customerID: String? = ""
    var serviceId: String? = ""

    private lateinit var destinationPosition: Point
    private lateinit var orginPosition: Point
    private var currentRoute: DirectionsRoute? = null
    private val completeProfilePresenter = CompleteProfilePresenter(this)

    var sec = 0
    var min = 0
    var hr = 0
    private var timer: CountDownTimer? = null
    private var mSavedState: Bundle? = null
    private var canceledByTheUser = false
    var isJobDone = false
    var customerProfile = CompleteProfile()
    private var mAlertDialog: android.app.AlertDialog? = null

    private lateinit var estimateAcceptKeyRef: DatabaseReference
    private lateinit var estimateAcceptKeyListener: ValueEventListener

    private lateinit var userNameTVM: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!hasPermissions(this, *permissions)) {
            ActivityCompat.requestPermissions(this, permissions, permsRequestCode)
        }

        Mapbox.getInstance(
            applicationContext,
            "pk.eyJ1IjoiZWp6b2FwcCIsImEiOiJjanQ0ZHB6cmoxMjgwM3lsbDJyZG1seGF0In0.c5QD_sqpK2TLKd2bg_jShQ"
        )
        setContentView(R.layout.activity_map_tracking)

        showProgressDialog("")
        completeProfilePresenter.completeProfile(
            SharedPreferencesHelper.getString(
                this,
                Constants.SharedPrefs.User.USER_ID,
                "0"
            )!!.toInt()
        )

        mSavedState = savedInstanceState

        buttonHandler = BackButtonHandler(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        btnServicesDashBoardToggle.setOnClickListener {
            if (showingDashBoard) {
                btnServicesDashBoardToggle.setImageResource(R.drawable.ic__ionicons_svg_md_arrow_dropup)
                LVdashboard.visibility = View.GONE
                showingDashBoard = false
            } else {
                btnServicesDashBoardToggle.setImageResource(R.drawable.ic__ionicons_svg_md_arrow_dropdown)
                LVdashboard.visibility = View.VISIBLE
                showingDashBoard = true
            }
        }
        var lastLat = 0.0
        var lastLng = 0.0

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    if (!dbUpdated) {
                        updateMyLocationToDB(
                            location.latitude.toString(),
                            location.longitude.toString()
                        )
                        dbUpdated = true
                    }

                    if (map != null) {
                        userLocation = LatLng(location.latitude, location.longitude)
                        if (mCurrLocationMarker == null) {
                            val markerOptions = MarkerOptions()
                            markerOptions.position(userLocation)
                            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.user_icon_two))
                            mCurrLocationMarker = map!!.addMarker(markerOptions)
                        }
                        mCurrLocationMarker?.position = userLocation
                        if (!freezCurrentLocation)
                            map!!.animateCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    userLocation,
                                    mZoomLevel
                                ), 1000, null
                            )

                        if (bookingId != 0 && bookingAccpted && !isJobDone) {
                            if (lastLat != location.latitude || lastLng != location.longitude) {
                                request!!.servicemen_lat = location.latitude.toString()
                                request!!.servicemen_long = location.longitude.toString()
                                //mDatabase.child(bookingId.toString()).setValue(request)
                                mDatabase.child(bookingId.toString()).child("servicemen_lat")
                                    .setValue(location.latitude.toString())
                                mDatabase.child(bookingId.toString()).child("servicemen_long")
                                    .setValue(location.longitude.toString())

                                mDatabaseCustomer.child("servicemen_lat")
                                    .setValue(location.latitude.toString())
                                mDatabaseCustomer.child("servicemen_long")
                                    .setValue(location.longitude.toString())

                                /*if (!thread.isAlive && !isLocationReayForChange) {
                                    thread = Thread(Runnable {
                                        Thread.sleep(15000)
                                        this@MapTrackingActivity.runOnUiThread(Runnable {
                                            isLocationReayForChange = true
                                            thread.interrupt()
                                        })
                                    })
                                    thread.start()
                                }

                                if (isLocationReayForChange) {
                                    isLocationReayForChange = false
                                    mDatabaseCustomer.child("servicemen_lat").setValue(location.latitude.toString())
                                    mDatabaseCustomer.child("servicemen_long").setValue(location.longitude.toString())
                                }*/
                                lastLat = location.latitude
                                lastLng = location.longitude
                                drawPolyLine()
                            }
                        }
                    }
                }
            }
        }

        requestCV.visibility = View.GONE
        imgBtnNavigate.visibility = View.INVISIBLE
        val fm = supportFragmentManager
        val mapFragment = SupportMapFragment.newInstance()

        fm.beginTransaction().replace(R.id.mapContainer, mapFragment).commit()
        mapFragment.getMapAsync(this@DashboardActivity)

        initDrawer(savedInstanceState)

        centerGPSIV.setOnClickListener {
            onLocationFetched()
            freezCurrentLocation = false
        }
        llCancel_HomeBottomBtn.setOnClickListener {
            callRequestCancelApi()
        }
        closeCV1.setOnClickListener {
            callRequestCancelApi()
        }
        closeCV.setOnClickListener {
            callRequestCancelApi()
        }
        llStartService_StartServiceLay.setOnClickListener {
            updateTOStart()
        }
        llFinishService_StartServiceLay.setOnClickListener {
            serviceDone1()
        }
        llCall_HomeBottomBtn.setOnClickListener {
            callToCustomer()
        }
        ivCall1.setOnClickListener {
            callToCustomer()
        }
        ivCall1_StartServiceLay.setOnClickListener {
            callToCustomer()
        }
        ivCall_MapTrackingLay.setOnClickListener {
            callToCustomer()
        }
        llEstimate_HomeBottomBtn.setOnClickListener {
            askEstimate()
        }
        btnMaximizeRequestCV.setOnClickListener {
            requestCV.visibility = View.VISIBLE
//            imgBtnNavigate.visibility = View.VISIBLE
            imgBtnNavigate.visibility = View.INVISIBLE
            btnMaximizeRequestCV.visibility = View.GONE
        }
        imgBtnNavigate.setOnClickListener {
            orginPosition = Point.fromLngLat(userLocation.longitude, userLocation.latitude)
            destinationPosition =
                Point.fromLngLat(destinationLocation.longitude, destinationLocation.latitude)
            getRoute(destinationPosition, orginPosition)
        }

        btnDirection_HomeBottomBtn.setOnClickListener {
            orginPosition = Point.fromLngLat(userLocation.longitude, userLocation.latitude)
            destinationPosition =
                Point.fromLngLat(destinationLocation.longitude, destinationLocation.latitude)
            getRoute(destinationPosition, orginPosition)
        }
        llDirection_HomeBottomBtn.setOnClickListener {
            orginPosition = Point.fromLngLat(userLocation.longitude, userLocation.latitude)
            destinationPosition =
                Point.fromLngLat(destinationLocation.longitude, destinationLocation.latitude)
            getRoute(destinationPosition, orginPosition)
        }

        DutyChangeImage.setOnClickListener {
            vibratePhone()
            val mediaPlayer: MediaPlayer? = MediaPlayer.create(this, R.raw.beyond_doubt)
            mediaPlayer?.start()
            //if (!bookingAccpted) {
            if (currentWorkStatus != BookingStatus.Accepted || currentWorkStatus != BookingStatus.Arrived ||
                currentWorkStatus != BookingStatus.Ongoing || currentWorkStatus != BookingStatus.Reached
            ) {
                if (currentStatus == 1) {
                    currentStatus = 0
                    DutyChangeImage.setImageResource(R.drawable.offduty)
                } else {
                    currentStatus = 1
                    DutyChangeImage.setImageResource(R.drawable.onduty)

                }
                changeStatusOfServiceMan()
            } else {
                //ShowToast("You have ongoing service")
                showAlertDutyChangeAfterWorkAccepted()
                changeStatusOfServiceMan()
            }
        }
        //userid = localStorage(this).completeCustomer.id
        userid = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "0")!!
            .toInt()
        val path = "bookings/service_men/$userid"
        mDatabase = FirebaseDatabase.getInstance().getReference(path)

        // Read from the database
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.hasChildren()
                if (value) {
                    val booking = dataSnapshot.children
                    booking.forEach {
                        if (bookingId != it.key!!.toInt() && !bookingAccpted && currentStatus == 1) {
                            try {
                                request = it.getValue(firebaseRequestModel::class.java)
//                                bookingId = it.key!!.toInt()
                                bookingId = request?.booking_id!!.toInt()
                                customerID = request!!.customer_id
                                serviceId = request!!.service_id
                            } catch (e: Exception) {
                                e.printStackTrace()
                            } finally {
                                if (request == null) {
                                    Toast.makeText(
                                        this@DashboardActivity,
                                        resources.getString(R.string.invalid_booking_data),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    it.ref.setValue(null)
                                    // RequestCanceled(false)
                                    makeUserFree()
                                }
                            }

                            var stts = 0
                            try {
                                stts = request?.status!!.toInt()
                            } catch (ex: java.lang.Exception) {
                                ex.printStackTrace()
                            }
                            when (stts) {
                                BookingStatus.Customer_Canceled -> {
                                    canceledByTheCustomer()
                                    currentWorkStatus = BookingStatus.Customer_Canceled
                                }
                                BookingStatus.Servicemen_Canceled -> {
                                    callRequestCancelApi()
                                    currentWorkStatus = BookingStatus.Servicemen_Canceled
                                }
                                BookingStatus.Requested -> {
                                    setupRequest(it.key!!.toInt())
                                    currentWorkStatus = BookingStatus.Requested
                                }
                                BookingStatus.Accepted -> {
                                    setupRequest(it.key!!.toInt())
                                    drawPolyLine()
//                                    callRequestAcceptFdB(false)

                                    bookingAccpted = true
                                    llCall_HomeBottomBtn.visibility = View.VISIBLE
                                    llArrived_HomeBottomBtn.visibility = View.VISIBLE
                                    arrivedLay.visibility = View.VISIBLE
                                    selectedCV.visibility = View.GONE
                                    requestCV.visibility = View.GONE
                                    llAccept_HomeBottomBtn.visibility = View.GONE
                                    tv1111.visibility = View.GONE
                                    closeCV.visibility = View.GONE
                                    ServiceManNameTOP.text = getString(R.string.you_are_on_the_way)
//                                    imgBtnNavigate.visibility = View.VISIBLE
                                    imgBtnNavigate.visibility = View.INVISIBLE
                                    btnCancel_HomeBottomBtn.text = getString(R.string.cancel)
                                    currentWorkStatus = BookingStatus.Accepted
                                    btnMaximizeRequestCV.visibility = View.GONE
                                }
                                BookingStatus.Arrived -> {
                                    setupRequest(it.key!!.toInt())
                                    drawPolyLine()
//                                    updateToArrived(false)

                                    currentWorkStatus = BookingStatus.Arrived

                                    if (SharedPreferencesHelper.getString(
                                            this@DashboardActivity,
                                            Constants.SharedPrefs.User.ESTIMATE_ACCEPTED,
                                            "false"
                                        ).toString() == "false"
                                    ) {
                                        llEstimate_HomeBottomBtn.visibility = View.VISIBLE
                                        startfinishlay.visibility = View.GONE
                                        imgBtnNavigate.visibility = View.GONE
                                        ServiceManNameTOP.text =
                                            resources.getString(R.string.you_have_reached)

                                        llAccept_HomeBottomBtn.visibility = View.GONE
                                        llCall_HomeBottomBtn.visibility = View.GONE
                                        llArrived_HomeBottomBtn.visibility = View.GONE
                                        arrivedLay.visibility = View.GONE
                                        requestCV.visibility = View.VISIBLE
                                        llCancel_HomeBottomBtn.visibility = View.GONE
                                        tvTitle_MapTracking1.visibility = View.GONE
                                    } else {
                                        llEstimate_HomeBottomBtn.visibility = View.GONE
                                        ivCall_MapTrackingLay.visibility = View.GONE
                                        requestCV.visibility = View.GONE
                                        selectedCV.visibility = View.GONE
                                        startfinishlay.visibility = View.VISIBLE
                                        tvWorkScope_StartServiceLay.text =
                                            SharedPreferencesHelper.getString(
                                                this@DashboardActivity,
                                                Constants.SharedPrefs.User.ESTIMATE_DESCRIPTION,
                                                ""
                                            ).toString()
                                        llStartService_StartServiceLay.visibility = View.VISIBLE
                                        llFinishService_StartServiceLay.visibility = View.GONE
                                        tvTitle_StartServiceLay.text =
                                            resources.getString(R.string.approved_estimate_for)
                                    }
                                    ivCall_MapTrackingLay.visibility = View.VISIBLE

                                    btnMaximizeRequestCV.visibility = View.GONE
                                    btnCancel_HomeBottomBtn.text = getString(R.string.cancel)
                                    request!!.status = BookingStatus.Arrived.toString()
                                    bookingAccpted = true
                                }
                                BookingStatus.Ongoing -> {
                                    setupRequest(it.key!!.toInt())
                                    drawPolyLine()
//                                    updateTOStart(false)
                                    currentWorkStatus = BookingStatus.Ongoing
                                    tvTitle_StartServiceLay.text =
                                        resources.getString(R.string.job_in_progress)
                                    llCall_HomeBottomBtn.visibility = View.VISIBLE
                                    llAccept_HomeBottomBtn.visibility = View.GONE
                                    requestCV.visibility = View.GONE
                                    requestCV1.visibility = View.GONE
                                    selectedCV.visibility = View.GONE
                                    startfinishlay.visibility = View.VISIBLE
                                    llStartService_StartServiceLay.visibility = View.GONE
                                    llFinishService_StartServiceLay.visibility = View.VISIBLE

                                    imgBtnNavigate.visibility = View.GONE
                                    btnCancel_HomeBottomBtn.text = getString(R.string.cancel)
                                    llCancel_HomeBottomBtn.isEnabled = false
                                    timerStartCreation()

                                    request!!.status = BookingStatus.Ongoing.toString()
                                    bookingAccpted = true
                                    mDatabase.child(bookingId.toString()).child("status")
                                        .setValue(BookingStatus.Ongoing.toString())
                                    mDatabaseCustomer.child("status")
                                        .setValue(BookingStatus.Ongoing.toString())
                                }
                                BookingStatus.Complete -> {
//                                    serviceDone1(false)
                                    currentWorkStatus = BookingStatus.Complete

                                    val pathCustomer =
                                        "bookings/customer/" + request!!.customer_id + "/" + bookingId
                                    mDatabaseCustomer =
                                        FirebaseDatabase.getInstance().getReference(pathCustomer)
                                    mDatabaseCustomer.child("status")
                                        .setValue(BookingStatus.Complete.toString())
                                    mDatabase.child(bookingId.toString()).child("status")
                                        .setValue(BookingStatus.Complete.toString())//Serviceman status update
                                    isJobDone = true
                                    SharedPreferencesHelper.putString(
                                        this@DashboardActivity,
                                        Constants.SharedPrefs.User.ESTIMATE_ACCEPTED,
                                        "false"
                                    )
                                    makePaymentInitialize(request!!.booking_id.toString(),
                                        request!!.customer_id,
                                        estimationFee,
                                        request!!.service_id)
                                    /*startActivity(
                                        Intent(
                                            this@DashboardActivity,
                                            InvoiceActivity::class.java
                                        ).putExtra("bookingId", request!!.booking_id)
                                            .putExtra("customerId", request!!.customer_id)
                                            .putExtra("tripCharge", estimationFee)
                                            .putExtra("serviceId", request!!.service_id)
                                    )
                                    makePaymentInitialize(request!!.booking_id.toString(),
                                        request!!.customer_id,
                                        estimationFee,
                                        request!!.service_id)
                                    SharedPreferencesHelper.putString(
                                        this@DashboardActivity,
                                        Constants.SharedPrefs.User.ESTIMATE_ACCEPTED,
                                        "false"
                                    )
                                    overridePendingTransition(0, 0)*/
                                    /*requestCV.visibility = View.INVISIBLE
                                    DutyChangeImage.visibility = View.VISIBLE
                                    bookingAccpted = false
                                    bookingId = 0
                                    isJobDone = true
                                    removeDestinationMarker()
                                    if (mapss != null)
                                        mapss!!.removePolyLine()
                                    freezCurrentLocation = false
                                    loadDashboardCount()
                                    val user =
                                        localStorage(this@DashboardActivity).completeCustomer
                                    if (user != null) ServiceManNameTOP.text =
                                        (resources.getString(R.string.hello) + " " + user.name)
                                    selectedCV.visibility = View.VISIBLE

                                    llAccept_HomeBottomBtn.visibility = View.VISIBLE
                                    llCall_HomeBottomBtn.visibility = View.GONE
                                    llArrived_HomeBottomBtn.visibility = View.GONE
                                    arrivedLay.visibility = View.GONE

                                    startfinishlay.visibility = View.GONE

                                    imgBtnNavigate.visibility = View.GONE
                                    btnCancel_HomeBottomBtn.text = getString(R.string.cancel)
                                    llCancel_HomeBottomBtn.isEnabled = false

                                    llElapsedTimerTV_StartServiceLay.visibility = View.GONE
                                    tvWorkScope_StartServiceLay.visibility = View.GONE
                                    //changeStatusOfServiceMan()
                                    map?.clear()*/


                                }
                            }


                        } else if (bookingId == it.key!!.toInt()) {
                            if (it.getValue(firebaseRequestModel::class.java)?.status!! == BookingStatus.Customer_Canceled.toString()) {
                                canceledByTheCustomer()
                            }
                        }
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                error.message
            }

        })

        llAccept_HomeBottomBtn.setOnClickListener {
            callRequestAcceptFdB()
        }

        llArrived_HomeBottomBtn.setOnClickListener {
            updateToArrived()
        }
        llArrived_HomeBottomBtn1.setOnClickListener {
//            updateToArrived()

            //New Update on 04-03-2023
            estimateDb()
            updateTOStart()
        }

        loadDashboardCount()
        updateDeviceIdOnServer()
        changeStatusOfServiceMan()

        val userType =
            SharedPreferencesHelper.getInt(this, Constants.SharedPrefs.User.PERSON_TYPE, 2)
        if (userType == 3) {
            showCompanyServiceman()
        }
    }

    private fun setupRequest(key: Int) {
        freezCurrentLocation = true
        bookingId = key
        val pathCustomer = "bookings/customer/" + request!!.customer_id + "/" + bookingId
        mDatabaseCustomer = FirebaseDatabase.getInstance().getReference(pathCustomer)

        mDatabaseCustomer.ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(p0: DataSnapshot) {
                val custREQ = p0.getValue(firebaseRequestModel::class.java)
                try {
                    destinationLocation =
                        LatLng(custREQ?.latitude!!.toDouble(), custREQ.longitute!!.toDouble())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                updateDestinationMarker()
            }
        })
        apptFloorTV.text =
            "Appt no. ${request!!.customer_appt} , floor no. ${request!!.customer_appt}"
        customerID = request!!.customer_id
        serviceId = request!!.service_id
        loadCustomerProfile(request!!.customer_id)
        loadServiceDetails(request!!.service_id)
        addressTV.text = request!!.address
        addressTV1.text = request!!.address
        val isLangArabic = getBoolean(
            this,
            Constants.SharedPrefs.User.IS_LANG_ARB, false
        )!!
        when {
            isLangArabic -> {
                serviceRequestedTV.text = request!!.service_ar_name
                serviceRequestedTV1.text = request!!.service_ar_name
            }
            else -> {
                serviceRequestedTV.text = request!!.service_name
                serviceRequestedTV1.text = request!!.service_name
            }
        }
        val cName = "${getString(R.string.sub_service)} ${request!!.sub_service_name}"
        subServiceRequestedTV.text = cName
        subserviceRequestedTV1.text = cName
        llCancel_HomeBottomBtn.setTag(R.string.request_id, key.toString())
        llAccept_HomeBottomBtn.setTag(R.string.request_id2, key.toString())
        btnCancel_HomeBottomBtn.text = getString(R.string.reject)
        llCancel_HomeBottomBtn.isEnabled = true
        llCancel_HomeBottomBtn.visibility = View.VISIBLE
        requestCV.visibility = View.VISIBLE
        imgBtnNavigate.visibility = View.GONE
    }

    private fun loadServiceDetails(service_id: String?) {
        var serviceID = 0
        try {
            serviceID = service_id!!.toInt()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        if (serviceID != 0) {
            val callServicProviders = APIClient.getApiInterface().getServiceDetails(serviceID)
            callServicProviders.enqueue(object : Callback<ResponseServiceWithSubService> {
                override fun onResponse(
                    call: Call<ResponseServiceWithSubService>,
                    response: Response<ResponseServiceWithSubService>,
                ) {
                    if (response.isSuccessful) {
                        val serviceWithSUbServices = response.body()!!.data!![0]
                        if (serviceWithSUbServices.serviceIcon != null && !serviceWithSUbServices.serviceIcon.isNullOrBlank()) {
                            Picasso.get().load(serviceWithSUbServices.serviceIcon)
                                .placeholder(R.drawable.icon_plumbing).into(imgServiceIcon)
                            Picasso.get().load(serviceWithSUbServices.serviceIcon)
                                .placeholder(R.drawable.icon_plumbing).into(imgServiceIcon1)
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseServiceWithSubService>, t: Throwable) {
                }
            })
        }

    }

    private fun canceledByTheCustomer() {
        makeUserFree()
    }

    private fun makeUserFree() {
        mDatabase.child(bookingId.toString()).setValue(null)
        mDatabase.child(bookingId.toString()).removeValue()
        request = null
        requestCV.visibility = View.INVISIBLE
        DutyChangeImage.visibility = View.VISIBLE
        bookingAccpted = false
        bookingId = 0
        isJobDone = false
        removeDestinationMarker()
        if (mapss != null)
            mapss!!.removePolyLine()
        freezCurrentLocation = false
        loadDashboardCount()
        val user = localStorage(this).completeCustomer
        if (user != null) ServiceManNameTOP.text =
            (resources.getString(R.string.hello) + " " + user.name)
        selectedCV.visibility = View.VISIBLE

        llAccept_HomeBottomBtn.visibility = View.VISIBLE
        llCall_HomeBottomBtn.visibility = View.GONE
        llArrived_HomeBottomBtn.visibility = View.GONE
        arrivedLay.visibility = View.GONE
        requestCV.visibility = View.VISIBLE
        startfinishlay.visibility = View.GONE
        imgBtnNavigate.visibility = View.GONE
        btnCancel_HomeBottomBtn.text = getString(R.string.cancel)
        changeStatusOfServiceMan()
        map?.clear()
        reLoadPage()
    }

    private fun loadCustomerProfile(custId: String?) {
        val callServiceProviders = APIClient.getApiInterface().getCustomerProfile(custId!!.toInt())
        callServiceProviders.enqueue(object : Callback<CustomerCompleteProfileAfterUpdate> {
            override fun onResponse(
                call: Call<CustomerCompleteProfileAfterUpdate>,
                response: Response<CustomerCompleteProfileAfterUpdate>,
            ) {
                if (response.isSuccessful) {
                    customerProfile = response.body()!!.data!![0]
                    val cName: String = if (customerProfile.lastname != null)
                        customerProfile.name + " " + customerProfile.lastname
                    else
                        customerProfile.name
                    requestorName.text = cName
                    requestorName1.text = cName
                    requestorName1_StartServiceLay.text = cName

                    if (customerProfile.image != null && customerProfile.image.isNotEmpty()) {
                        Picasso.get().load(customerProfile.image)
                            .placeholder(R.drawable.dummy_user).fit().into(serviceManIVR)
                        Picasso.get().load(customerProfile.image)
                            .placeholder(R.drawable.dummy_user).fit()
                            .into(serviceManIVR1)
                        Picasso.get().load(customerProfile.image)
                            .placeholder(R.drawable.dummy_user).fit()
                            .into(serviceManIVR1_StartServiceLay)
                    }
                }
            }

            override fun onFailure(call: Call<CustomerCompleteProfileAfterUpdate>, t: Throwable) {
                t.printStackTrace()
            }
        })


        APIClient.getApiInterface().getCompleteProfile(userid)
            .enqueue(object : Callback<CustomerCompleteProfile> {
                override fun onResponse(
                    call: Call<CustomerCompleteProfile>,
                    response: Response<CustomerCompleteProfile>,
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data!!.data != null) {
                            try {
                                freeEstimate = data.data.free_estimate
                                estimationFee = data.data.estimation_fee
                                // set below values to test paid trip of serviceman
                                //  freeEstimate=0
                                //  estimationFee=5.0
                            } catch (ex: java.lang.Exception) {
                                ex.printStackTrace()
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<CustomerCompleteProfile>, t: Throwable) {

                }
            })

    }

    private fun showCompanyServiceman() {
        val header =
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")
        val compId =
            SharedPreferencesHelper.getInt(this, Constants.SharedPrefs.User.PERSON_COMPANY_NAME, 0)
        val callServiceProviders =
            APIClient.getApiInterface()
                .getServicemanLocation(header, userid.toString(), compId.toString())
        callServiceProviders.enqueue(object : Callback<CompanyServicemanLocationResponse> {
            override fun onResponse(
                call: Call<CompanyServicemanLocationResponse>,
                response: Response<CompanyServicemanLocationResponse>,
            ) {
                if (response.isSuccessful) {
                    val cData = response.body()!!.data

                    if (cData != null)
                        setDataToMarker(cData)
                }
            }

            override fun onFailure(call: Call<CompanyServicemanLocationResponse>, t: Throwable) {

            }
        })
    }

    private fun setDataToMarker(cData: List<ServicemanLocationData?>) {
        val markerOptions = MarkerOptions()
        if (cData.isNotEmpty()) {
            for (i in cData.indices) {
                val latLng =
                    LatLng(cData[i]!!.latitude?.toDouble()!!, cData[i]!!.longitude?.toDouble()!!)
                var marker =
                    (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                        R.layout.custom_marker,
                        null
                    )

                markerOptions.position(latLng)
                markerOptions.title(cData[i]!!.name)
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_c))
                markerOptions.position

                val mInfo = InfoWindowData()
                mInfo.image = cData[i]?.image
                mInfo.name = cData[i]?.name
                if (!cData[i]?.services.isNullOrEmpty())
                    mInfo.serviceName = cData[i]?.services!![0]?.serviceName
                Constants.GlobalSettings.owner = localStorage(this).completeCustomer.name
                Constants.GlobalSettings.image = localStorage(this).completeCustomer.image

            }
        }
    }

    private fun loadDashboardCount() {
        val userType =
            SharedPreferencesHelper.getInt(this, Constants.SharedPrefs.User.PERSON_TYPE, 2)
        if (userType == 3) {
            ServiceManName.text = resources.getString(R.string.services_completed)
        } else {
            ServiceManName.text = resources.getString(R.string.work_status)
        }
        if (userType == 3) {
            val callServiceProviders = APIClient.getApiInterface().getFinishedJobCount(userid)
            callServiceProviders.enqueue(object : Callback<CompanyHomeCountResponse> {
                override fun onResponse(
                    call: Call<CompanyHomeCountResponse>,
                    response: Response<CompanyHomeCountResponse>,
                ) {
                    if (response.isSuccessful) {
                        val cData = response.body()!!.data
                        countTodayTV.text = getCountVal(cData!!.jobsDay!!)
                        countMonthTV.text = getCountVal(cData.jobsMonth!!)
                        countYearTV.text = getCountVal(cData.jobsYear!!)
                    }
                }

                override fun onFailure(call: Call<CompanyHomeCountResponse>, t: Throwable) {

                }
            })
        } else {
            val callServicProviders = APIClient.getApiInterface().getCountDashBoard(userid)
            callServicProviders.enqueue(object : Callback<HomeCountResponse> {
                override fun onResponse(
                    call: Call<HomeCountResponse>,
                    response: Response<HomeCountResponse>,
                ) {
                    if (response.isSuccessful) {
                        if (response.body()!!.data.size > 0) {
                            val cData = response.body()!!.data[0]
                            countTodayTV.text = getCountVal(cData!!.dateWise)
                            countMonthTV.text = getCountVal(cData.monthWise)
                            countYearTV.text = getCountVal(cData.yearWise)
                        } else {
                            countTodayTV.text = ""
                            countMonthTV.text = ""
                            countYearTV.text = ""
                        }
                    }
                }

                override fun onFailure(call: Call<HomeCountResponse>, t: Throwable) {

                }
            })
        }
    }

    private fun getCountVal(cVal: Int): String {
        var aa = ""
        aa = when {
            cVal < 10 -> {
                "0$cVal"
            }
            else -> {
                cVal.toString()
            }
        }
        return aa
    }

    private fun updateDestinationMarker() {
        if (map != null) {
            val markerOptions = MarkerOptions()
            markerOptions.position(destinationLocation)
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
            destinationMarker = map!!.addMarker(markerOptions)
            // map!!.animateCamera(CameraUpdateFactory.newLatLngZoom(destinationLocation, 12f), 1000, null)
            bookingAccpted = true

            val builder = LatLngBounds.Builder()
            if (destinationMarker != null)
                builder.include(destinationMarker!!.position)
            builder.include(userLocation)
            val bounds = builder.build()
            val width = resources.displayMetrics.widthPixels
            val height = resources.displayMetrics.heightPixels
            val padding = (width * 0.10).toInt() // offset from edges of the map 10% of screen
            val cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding)
            map!!.animateCamera(cu)
        }
    }

    private fun vibratePhone() {
        val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // void vibrate (VibrationEffect vibe)
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    200,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            // This method was deprecated in API level 26
            vibrator.vibrate(200)
        }
    }

    private fun removeDestinationMarker() {
        if (destinationMarker != null) {
            destinationMarker?.remove()
        }
    }

    private fun drawPolyLine() {
        if (polyline != null) {
            polyline?.remove()
        }

        if (mapss == null)
            mapss = Mapss(map, userLocation, destinationLocation, this@DashboardActivity, this)
        else {
            mapss!!.updateRoute(map, userLocation, destinationLocation)
        }
    }

    private fun callRequestCancelApi() {
        if (llCancel_HomeBottomBtn.getTag(R.string.request_id) != null) {

            showProgressDialog("")

            mDatabaseCustomer.child("status").setValue(BookingStatus.Servicemen_Canceled.toString())
            canceledByTheUser = true

            val loc = UpdateBookingStatus()
            loc.id = bookingId
            loc.status = BookingStatus.Servicemen_Canceled.toString()

            val callServicProviders = APIClient.getApiInterface().updateBookingStatus(loc)
            callServicProviders.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    destroyDialog()
                    mDatabase.child(bookingId.toString()).setValue(null).addOnSuccessListener {
                        makeUserFree()
                        currentWorkStatus = BookingStatus.Servicemen_Canceled
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    destroyDialog()
                    mDatabase.child(bookingId.toString()).setValue(null).addOnSuccessListener {
                        makeUserFree()
                    }
                }
            })
        }

        requestCV.visibility = View.INVISIBLE
    }

    private fun callRequestAcceptFdB(showMsg: Boolean = true) {
        val servicemenImage = localStorage(this@DashboardActivity).completeCustomer.image
        request?.servicemen_image = servicemenImage

        request!!.status = BookingStatus.Accepted.toString()

        if (llAccept_HomeBottomBtn.getTag(R.string.request_id2) != null) {
            if (showMsg) {
                showProgressDialog("")
                val loc = UpdateBookingStatus()
                loc.id = bookingId
                loc.status = BookingStatus.Accepted.toString()
                val callServiceProviders = APIClient.getApiInterface().updateBookingStatus(loc)
                callServiceProviders.enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        destroyDialog()
                        if (response.isSuccessful) {
                            bookingAccpted = true
                            llCall_HomeBottomBtn.visibility = View.VISIBLE
                            llAccept_HomeBottomBtn.visibility = View.GONE
                            tv1111.visibility = View.GONE
                            closeCV.visibility = View.GONE
                            llArrived_HomeBottomBtn.visibility = View.VISIBLE
                            arrivedLay.visibility = View.VISIBLE
                            requestCV.visibility = View.GONE
                            selectedCV.visibility = View.GONE
                            ServiceManNameTOP.text = getString(R.string.you_are_on_the_way)
//                            imgBtnNavigate.visibility = View.VISIBLE
                            imgBtnNavigate.visibility = View.INVISIBLE
                            btnCancel_HomeBottomBtn.text = getString(R.string.cancel)

                            btnMaximizeRequestCV.visibility = View.GONE
                            mDatabaseCustomer.child("status")
                                .setValue(BookingStatus.Accepted.toString())
                            mDatabaseCustomer.child("servicemen_image").setValue(servicemenImage)
                            mDatabase.child(bookingId.toString()).child("status")
                                .setValue(BookingStatus.Accepted.toString())
                            mDatabase.child(bookingId.toString()).child("servicemen_image")
                                .setValue(servicemenImage)
                            currentWorkStatus = BookingStatus.Accepted
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        destroyDialog()
                    }
                })
            }
        }
    }

    private fun updateToArrived(showMsg: Boolean = true) {
        if (showMsg) {
            showProgressDialog("")
            val loc = UpdateBookingStatus()
            loc.id = bookingId
            loc.status = BookingStatus.Arrived.toString()

            val callServiceProviders = APIClient.getApiInterface().updateBookingStatus(loc)
            callServiceProviders.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    destroyDialog()
                    if (response.isSuccessful) {
                        imgBtnNavigate.visibility = View.GONE
                        ServiceManNameTOP.text = resources.getString(R.string.you_have_reached)
                        llArrived_HomeBottomBtn.visibility = View.GONE
                        arrivedLay.visibility = View.GONE
                        llCancel_HomeBottomBtn.visibility = View.GONE
                        requestCV.visibility = View.VISIBLE
                        llEstimate_HomeBottomBtn.visibility = View.VISIBLE
                        tvTitle_MapTracking1.visibility = View.GONE
                        ivCall_MapTrackingLay.visibility = View.VISIBLE
                        startfinishlay.visibility = View.GONE
                        btnCancel_HomeBottomBtn.text = getString(R.string.cancel)
                        btnMaximizeRequestCV.visibility = View.GONE

                        request!!.status = BookingStatus.Arrived.toString()
                        bookingAccpted = true
                        mDatabaseCustomer.child("status")
                            .setValue(BookingStatus.Arrived.toString())

                        mDatabase.child(bookingId.toString()).child("status")
                            .setValue(BookingStatus.Arrived.toString())
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    destroyDialog()
                }
            })
        }

    }

    private fun askEstimate() {
        // on success asking
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.popup_service_estimate, null)
        val mBuilder = android.app.AlertDialog.Builder(this).setView(mDialogView)
        mAlertDialog = mBuilder.show()
        mAlertDialog!!.setCanceledOnTouchOutside(false)
        mAlertDialog!!.setCancelable(false)

        mAlertDialog!!.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val button = mDialogView.findViewById(R.id.estimateServiceBtn) as ConstraintLayout
        val layoutInput = mDialogView.findViewById(R.id.layoutInput) as ConstraintLayout
        val layoutProgress = mDialogView.findViewById(R.id.layoutProgress) as ConstraintLayout
        layoutInput.visibility = View.VISIBLE
        layoutProgress.visibility = View.GONE
        val scopeET = mDialogView.findViewById(R.id.DescriptionET) as AppCompatEditText
        val amountET = mDialogView.findViewById(R.id.feePayDescriptionET) as AppCompatEditText

        button.setOnClickListener {
            val scope = scopeET.text.toString()
            val amt = amountET.text.toString()
            when {
                scope.isEmpty() -> {
                    Toast.makeText(
                        this@DashboardActivity,
                        resources.getString(R.string.please_enter_work_scope),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                amt.isEmpty() -> {
                    Toast.makeText(
                        this@DashboardActivity,
                        resources.getString(R.string.please_enter_price_estimate),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    val estimateFreeKey = "freeEstimate"
                    val estimateFeeKey = "estimationFee"
                    val estimateAMTKey = "estimate_amount"
                    val estimateScopeKey = "work_scope"
                    val estimateAcceptKey = "estimate_accepted"
                    layoutInput.visibility = View.GONE
                    layoutProgress.visibility = View.VISIBLE

                    mDatabase.child(bookingId.toString()).child(estimateFreeKey)
                        .setValue(freeEstimate)
                    mDatabase.child(bookingId.toString()).child(estimateFeeKey)
                        .setValue(estimationFee)

                    mDatabaseCustomer.child(estimateFreeKey).setValue(freeEstimate)
                    mDatabaseCustomer.child(estimateFeeKey).setValue(estimationFee)

                    mDatabase.child(bookingId.toString()).child(estimateAMTKey).setValue(amt)
                    mDatabase.child(bookingId.toString()).child(estimateScopeKey).setValue(scope)
                    mDatabaseCustomer.child(estimateAMTKey).setValue(amt)
                    mDatabaseCustomer.child(estimateScopeKey).setValue(scope)
                    SharedPreferencesHelper.putString(
                        this@DashboardActivity,
                        Constants.SharedPrefs.User.ESTIMATE_AMOUNT,
                        amt
                    )
                    SharedPreferencesHelper.putString(
                        this@DashboardActivity,
                        Constants.SharedPrefs.User.ESTIMATE_DESCRIPTION,
                        scope
                    )
                    estimateAcceptKeyRef =
                        mDatabase.child(bookingId.toString()).child(estimateAcceptKey).ref
                    estimateAcceptKeyListener =
                        estimateAcceptKeyRef.addValueEventListener(object : ValueEventListener {
                            override fun onCancelled(databaseError: DatabaseError) {

                            }

                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    val status = dataSnapshot.getValue(String::class.java)
                                    if (status != null && status != "") {
                                        onEstimateChange(status)
                                    }
                                }
                            }
                        })
                }
            }
        }

    }

    private fun onEstimateChange(acceptStatus: String) {
        if (::estimateAcceptKeyRef.isInitialized && ::estimateAcceptKeyListener.isInitialized) {
            estimateAcceptKeyRef.removeEventListener(estimateAcceptKeyListener)
        }
        mAlertDialog?.dismiss()
        if (acceptStatus == "true") {
            SharedPreferencesHelper.putString(
                this@DashboardActivity,
                Constants.SharedPrefs.User.ESTIMATE_ACCEPTED,
                "true"
            )
            llEstimate_HomeBottomBtn.visibility = View.GONE
            ivCall_MapTrackingLay.visibility = View.GONE
//            llStart_HomeBottomBtn.visibility = View.VISIBLE
            requestCV.visibility = View.GONE
            selectedCV.visibility = View.GONE
            startfinishlay.visibility = View.VISIBLE
            tvWorkScope_StartServiceLay.text =
                SharedPreferencesHelper.getString(
                    this@DashboardActivity,
                    Constants.SharedPrefs.User.ESTIMATE_DESCRIPTION,
                    ""
                ).toString()
            llStartService_StartServiceLay.visibility = View.VISIBLE
            llFinishService_StartServiceLay.visibility = View.GONE
            tvTitle_StartServiceLay.text = resources.getString(R.string.approved_estimate_for)
        } else if (acceptStatus == "false" && freeEstimate == 0) {
            // pay charge of the trip
            ShowToast(resources.getString(R.string.home_owner_rejected_the_estimate))
            serviceDone1()
        } else if (acceptStatus == "false" && freeEstimate == 1) {
            canceledByTheCustomer()
        }
    }

    private fun updateTOStart(showMsg: Boolean = true) {
        if (showMsg) {
            showProgressDialog("")
            val loc = UpdateBookingStatus()
            loc.id = bookingId
            loc.status = BookingStatus.Ongoing.toString()

            val callServiceProviders = APIClient.getApiInterface().updateBookingStatus(loc)
            callServiceProviders.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    destroyDialog()
                    if (response.isSuccessful) {
                        tvTitle_StartServiceLay.text = resources.getString(R.string.job_in_progress)
                        arrivedLay.visibility = View.GONE
                        requestCV.visibility = View.GONE
                        selectedCV.visibility = View.GONE
                        startfinishlay.visibility = View.VISIBLE

                        llStartService_StartServiceLay.visibility = View.GONE
                        llFinishService_StartServiceLay.visibility = View.VISIBLE

                        imgBtnNavigate.visibility = View.GONE
                        btnCancel_HomeBottomBtn.text = getString(R.string.cancel)
                        llCancel_HomeBottomBtn.isEnabled = false
                        timerStartCreation()

                        request!!.status = BookingStatus.Ongoing.toString()
                        bookingAccpted = true
                        mDatabase.child(bookingId.toString()).child("status")
                            .setValue(BookingStatus.Ongoing.toString())
                        mDatabaseCustomer.child("status").setValue(BookingStatus.Ongoing.toString())
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    destroyDialog()
                }
            })
        }

    }

    private fun estimateDb() {
        val estimateFreeKey = "freeEstimate"
        val estimateFeeKey = "estimationFee"
        val estimateAMTKey = "estimate_amount"
        val estimateScopeKey = "work_scope"
        val estimateAcceptKey = "estimate_accepted"

        mDatabase.child(bookingId.toString()).child(estimateFreeKey)
            .setValue(freeEstimate)
        mDatabase.child(bookingId.toString()).child(estimateFeeKey)
            .setValue(estimationFee)

        mDatabaseCustomer.child(estimateFreeKey).setValue(freeEstimate)
        mDatabaseCustomer.child(estimateFeeKey).setValue(estimationFee)

        mDatabase.child(bookingId.toString()).child(estimateAMTKey).setValue("1")
        mDatabase.child(bookingId.toString()).child(estimateScopeKey).setValue("scope")
        mDatabaseCustomer.child(estimateAMTKey).setValue("1")
        mDatabaseCustomer.child(estimateScopeKey).setValue("scope")
        SharedPreferencesHelper.putString(
            this@DashboardActivity,
            Constants.SharedPrefs.User.ESTIMATE_AMOUNT,
            "1"
        )
        SharedPreferencesHelper.putString(
            this@DashboardActivity,
            Constants.SharedPrefs.User.ESTIMATE_DESCRIPTION,
            "scope"
        )
    }

    private fun serviceDone1(showMsg: Boolean = true) {
        if (timer != null) {
            timer?.cancel()
            llElapsedTimerTV_StartServiceLay.visibility = View.VISIBLE
            tvWorkScope_StartServiceLay.visibility = View.GONE
            sec = 0
            min = 0
            hr = 0
        }
        if (showMsg) {
            showProgressDialog("")
            val loc = UpdateBookingStatus()
            loc.id = bookingId
            loc.status = BookingStatus.Complete.toString()

            myBookingId = bookingId.toString()
            val callServicProviders = APIClient.getApiInterface().updateBookingStatus(loc)

            callServicProviders.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    destroyDialog()
                    if (response.isSuccessful) {

                        val pathCustomer =
                            "bookings/customer/" + request!!.customer_id + "/" + bookingId
                        mDatabaseCustomer =
                            FirebaseDatabase.getInstance().getReference(pathCustomer)
                        mDatabaseCustomer.child("status")
                            .setValue(BookingStatus.Complete.toString())
                        mDatabase.child(bookingId.toString()).child("status")
                            .setValue(BookingStatus.Complete.toString())//Serviceman status update
                        isJobDone = true

                        //mDatabase.child(bookingId.toString()).setValue(null)
                        /*startActivity(
                            Intent(
                                this@DashboardActivity,
                                InvoiceActivity::class.java
                            ).putExtra("bookingId", myBookingId)
                                .putExtra("customerId", customerID)
                                .putExtra("tripCharge", estimationFee)
                                .putExtra("serviceId", serviceId)
                        )
                        overridePendingTransition(0, 0)
*/
                        makePaymentInitialize(myBookingId!!, customerID, estimationFee, serviceId)
                        //finish()
                        /*requestCV.visibility = View.INVISIBLE
                        DutyChangeImage.visibility = View.VISIBLE
                        bookingAccpted = false
                        bookingId = 0
                        isJobDone = true
                        removeDestinationMarker()
                        if (mapss != null)
                            mapss!!.removePolyLine()
                        freezCurrentLocation = false
                        loadDashboardCount()
                        val user = localStorage(this@DashboardActivity).completeCustomer
                        if (user != null) ServiceManNameTOP.text =
                            (resources.getString(R.string.hello) + " " + user.name)
                        selectedCV.visibility = View.VISIBLE

                        llAccept_HomeBottomBtn.visibility = View.VISIBLE
                        llCall_HomeBottomBtn.visibility = View.GONE
                        llArrived_HomeBottomBtn.visibility = View.GONE
                        arrivedLay.visibility = View.GONE
                        startfinishlay.visibility = View.GONE
                        llEnd_HomeBottomBtn.visibility = View.GONE
                        imgBtnNavigate.visibility = View.GONE
                        btnCancel_HomeBottomBtn.text = getString(R.string.cancel)
                        llCancel_HomeBottomBtn.isEnabled = false

                        llElapsedTimerTV_StartServiceLay.visibility = View.VISIBLE
                        tvWorkScope_StartServiceLay.visibility = View.GONE
                        //changeStatusOfServiceMan()
                        map?.clear()*/
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    destroyDialog()
                }
            })
        }
    }

    private fun reLoadPage() {
        /*val refresh = Intent(this@MapTrackingActivity, MapTrackingActivity::class.java)
        handler.postDelayed({
            startActivity(refresh)
            this@MapTrackingActivity.finish()
        }, 250L)*/
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    private fun changeStatusOfServiceMan() {
        val loc = UpdateServemanWorkingStatus()
        loc.id = userid
        loc.workingStatus = currentStatus.toString()

        val callServiceProviders = APIClient.getApiInterface().updateServiceWorkingStatus(loc)
        callServiceProviders.enqueue(object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun mLogoutWithChangeStatus() {
        currentStatus = 0
        val loc = UpdateServemanWorkingStatus()
        loc.id = userid
        loc.workingStatus = currentStatus.toString()

        val callServiceProviders =
            APIClient.getApiInterface().updateServiceWorkingStatusWithHeader(loc)
        callServiceProviders.enqueue(object : Callback<CommonApiResponse> {
            override fun onResponse(
                call: Call<CommonApiResponse>,
                response: Response<CommonApiResponse>,
            ) {
                if (response.isSuccessful) {
                    try {
                        if (response.body()!!.status == 1) {
                            localStorage(this@DashboardActivity).logoutUser()
                            SharedPreferencesHelper.clearPreferences(this@DashboardActivity)
                            setLanguage(this@DashboardActivity, "en")
                            startActivity(
                                Intent(
                                    this@DashboardActivity,
                                    LoginActivity::class.java
                                )
                            )
                            finishAffinity()
                            overridePendingTransition(0, 0)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } else {
                    localStorage(this@DashboardActivity).logoutUser()
                    SharedPreferencesHelper.clearPreferences(this@DashboardActivity)
                    startActivity(Intent(this@DashboardActivity, LoginActivity::class.java))
                    finishAffinity()
                    overridePendingTransition(0, 0)
                }

            }

            override fun onFailure(call: Call<CommonApiResponse>, t: Throwable) {
                try {
                    localStorage(this@DashboardActivity).logoutUser()
                    SharedPreferencesHelper.clearPreferences(this@DashboardActivity)
                    startActivity(Intent(this@DashboardActivity, LoginActivity::class.java))
                    finishAffinity()
                    overridePendingTransition(0, 0)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        })
    }

    private fun updateMyLocationToDB(lat: String, lon: String) {
        val loc = UpdateServemanLocation()
        loc.id = userid
        loc.latitude = lat
        loc.longitude = lon

        val callServiceProviders = APIClient.getApiInterface().updateServiceManLocation(loc)
        callServiceProviders.enqueue(object : Callback<ServiceProviderLocationUpdate> {
            override fun onResponse(
                call: Call<ServiceProviderLocationUpdate>,
                response: Response<ServiceProviderLocationUpdate>,
            ) {
                if (response.code() == 401 && response.message() == "Unauthorized") {
                    onSessionExpired()
                }
            }

            override fun onFailure(call: Call<ServiceProviderLocationUpdate>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun callToCustomer() {
        try {
            val call =
                sinchServiceInterface.callUser(request?.customer_name + "-" + request?.customer_mobile)
                    ?: // Service failed for some reason, show a Toast and abort
                    // Toast.makeText(this, "Service is not started. Try stopping the service and starting it again before " + "placing a call.", Toast.LENGTH_LONG).show()
                    return

            val callId = call.callId
            val callScreen = Intent(this, CallScreenActivity::class.java)
            callScreen.putExtra(SinchService.CALL_ID, callId)
            callScreen.putExtra("profile_image", customerProfile.image)
            callScreen.putExtra(
                "customer_name",
                customerProfile.name + " " + SupportCode.getNullStringEmpty(customerProfile.lastname)
                    .trim()
            )

            startActivity(callScreen)
        } catch (e: MissingPermissionException) {
            ActivityCompat.requestPermissions(this, arrayOf(e.requiredPermission), 0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        if (requestCode == permsRequestCode) {// If request is cancelled, the result arrays are empty.
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onLocationFetched()
            }
        }
    }


    private fun initDrawer(savedInstanceState: Bundle?) {
        val navigationView: View =
            LayoutInflater.from(this).inflate(R.layout.drawyer_layout, null, false)
        val thumbSection = navigationView.findViewById(R.id.thumbsContentLL) as LinearLayout
        val mProfileImage = navigationView.findViewById(R.id.serviceManIV) as CircleImageView
        val mProfileName = navigationView.findViewById(R.id.userNameTVM) as AppCompatTextView
        val termsCondition =
            navigationView.findViewById(R.id.termsandConditionTV) as AppCompatTextView

        if (localStorage(this).completeCustomer != null) {
            val mDetails = localStorage(this).completeCustomer
            if (mDetails.image != null)
                Picasso.get().load(mDetails.image).placeholder(R.drawable.dummy_user)
                    .error(R.drawable.dummy_user).into(mProfileImage)
            mProfileName.text = mDetails.name
        }

        thumbSection.setOnClickListener {
            showProgressDialog("")
            val userId =
                SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "")
            val header =
                SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")
            val callServiceProviders =
                APIClient.getApiInterface().getOwnReviews("Bearer $header", userId)
            callServiceProviders.enqueue(object : Callback<ReviewsResponse> {
                override fun onResponse(
                    call: Call<ReviewsResponse>,
                    response: Response<ReviewsResponse>,
                ) {
                    destroyDialog()
                    if (response.isSuccessful) {
                        val reviewsData = response.body()
                        if (response.code() == 401 && response.message() == "Unauthorized") {
                            onSessionExpired()
                        } else {
                            if (reviewsData?.status == 1) {

                                if (reviewsData.data!!.isNullOrEmpty()) {
                                    slidingRootNav?.closeMenu(true)
                                    Toast.makeText(
                                        this@DashboardActivity,
                                        resources.getString(R.string.no_reviews_found),
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    slidingRootNav?.closeMenu(true)
                                    handler.postDelayed(
                                        { openactivity(ReviewsActivity()) },
                                        navigationDelay
                                    )
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ReviewsResponse>, t: Throwable) {
                    destroyDialog()
                }
            })
        }

        termsCondition.setOnClickListener {
            slidingRootNav?.closeMenu(true)
            handler.postDelayed(
                {
                    startActivity(Intent(this, TermsAndCondition::class.java))
                    overridePendingTransition(0, 0)
                },
                navigationDelay
            )
        }

        slidingRootNav = SlidingRootNavBuilder(this)
            .withMenuOpened(false)
            .withContentClickableWhenMenuOpened(false)
            .withSavedState(savedInstanceState)
            .withMenuView(navigationView)

            // .withDragDistance(140) //Horizontal translation of a view. Default == 180dp
            // .withRootViewScale(0.7f) //Content view's scale will be interpolated between 1f and 0.7f. Default == 0.65f;
            // .withRootViewElevation(10) //Content view's elevation will be interpolated between 0 and 10dp. Default == 8.
            //  .withRootViewYTranslation(4) //Content view's translationY will be interpolated between 0 and 4. Default == 0

            .inject()

        slidingRootNav?.layout?.setOnTouchListener { _, event ->

            if (event.action == MotionEvent.ACTION_DOWN) {
                if (slidingRootNav != null && slidingRootNav!!.isMenuOpened) {
                    slidingRootNav?.closeMenu(true)

                } else {
                    slidingRootNav?.openMenu(true)
                }

                true
            } else {
                false
            }
        }


        navigationRV.layoutManager = LinearLayoutManager(this)
        navigationRV.adapter = NavigationAdapter(this, this)

        menuIV.setOnClickListener {
            if (!isLocationSelected) {
                slidingRootNav?.openMenu(true)
            } else {
                // hideBottomWagonSelectionCard()
                //  isLocationSelected = false
                // toggleLocationCard(true)
                menuIV.setImageResource(R.drawable.ic_navigation)
                // toggleLocationCard(true)
            }
        }
        userNameTVM = navigationView.findViewById(R.id.userNameTVM)
        MenuNavigation(this, handler).loadUserDetails(navigationView, ServiceManNameTOP)
    }

    override fun onResume() {
        super.onResume()
        completeProfilePresenter.completeProfile(
            SharedPreferencesHelper.getString(
                this,
                Constants.SharedPrefs.User.USER_ID,
                "0"
            )!!.toInt()
        )
        startLocationUpdates()
        /* initDrawer(mSavedState)*/
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        val locationRequest = LocationRequest()
            .setInterval((20 * 1000).toLong())
            .setFastestInterval((10 * 1000).toLong())
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        /* fusedLocationClient.removeLocationUpdates(
             locationRequest,
             locationCallback,
             null *//* Looper *//*
        )*/
        fusedLocationClient.removeLocationUpdates(locationCallback)

    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    fun openactivity(activity: Activity) {
        val intent = Intent(this, activity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    private fun checkLocationServices() {
        val mLocationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval((20 * 1000).toLong())
            .setFastestInterval((10 * 1000).toLong())

        val settingsBuilder = LocationSettingsRequest.Builder()
            .addLocationRequest(mLocationRequest)
        settingsBuilder.setAlwaysShow(true)

        val result = LocationServices.getSettingsClient(this)
            .checkLocationSettings(settingsBuilder.build())

        result.addOnCompleteListener { task ->
            try {
                var response = task.getResult(ApiException::class.java)
                // All location settings are satisfied. The client can initialize location
                // requests here.
                onLocationFetched()
            } catch (ex: ApiException) {
                when (ex.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        // Location settings are not satisfied. But could be fixed by showing the
                        // user a dialog.
                        try {
                            val resolvableApiException = ex as ResolvableApiException
                            resolvableApiException.startResolutionForResult(
                                this@DashboardActivity,
                                REQUEST_CHECK_SETTINGS
                            )
                        } catch (e: Exception) {
                            // Ignore the error.
                        }
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                    }
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.let { LocationSettingsStates.fromIntent(it) }
        when (requestCode) {
            REQUEST_CHECK_SETTINGS -> {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        // All required changes were successfully made
                        onLocationFetched()
                    }
                    Activity.RESULT_CANCELED -> {
                        // The user was asked to change settings, but chose not to
                    }
                }
            }
        }
    }

    private fun onLocationFetched() {
        dbUpdated = false
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                //val location=LatLng(29.374009, 47.976461)
                if (map != null) {

                    map!!.setOnCameraIdleListener(this)
                    map!!.setOnCameraMoveStartedListener(this)
                    map!!.setOnCameraMoveListener(this)
                    map!!.setOnCameraMoveCanceledListener(this)

                    userLocation = LatLng(location.latitude, location.longitude)
                    if (mCurrLocationMarker != null) {
                        mCurrLocationMarker?.remove()
                    }

                    updateMyLocationToDB(
                        location.latitude.toString(),
                        location.longitude.toString()
                    )
                    val latLng = userLocation

                    val markerOptions = MarkerOptions()
                    markerOptions.position(latLng)
                    markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.user_icon_two))//
                    mCurrLocationMarker = map!!.addMarker(markerOptions)


                    map!!.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(latLng, mZoomLevel),
                        1000,
                        null
                    )

                }
            } else {
                checkLocationServices()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        with(googleMap) {
            map = googleMap
            setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this@DashboardActivity,
                    R.raw.map_style
                )
            )
//            moveCamera(CameraUpdateFactory.newLatLngZoom(SYDNEY, ZOOM_LEVEL))
//            addMarker(MarkerOptions().position(SYDNEY)).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
            map2 = googleMap
            setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this@DashboardActivity,
                    R.raw.map_style
                )
            )
            onLocationFetched()
        }

    }

    override fun onCameraMoveStarted(reason: Int) {
        when (reason) {
            GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE -> {
                freezCurrentLocation = true
                if (bookingAccpted && bookingId != 0) {
                    requestCV.visibility = View.INVISIBLE
                    btnMaximizeRequestCV.visibility = View.INVISIBLE
                    selectedCV.visibility = View.INVISIBLE
                    imgBtnNavigate.visibility = View.INVISIBLE
                }
                // Toast.makeText(this, "The user gestured on the map.",
                //     Toast.LENGTH_SHORT).show();
            }
            GoogleMap.OnCameraMoveStartedListener
                .REASON_API_ANIMATION,
            -> {
                // Toast.makeText(this, "The user tapped something on the map.",
                //       Toast.LENGTH_SHORT).show();
            }
            GoogleMap.OnCameraMoveStartedListener
                .REASON_DEVELOPER_ANIMATION,
            -> {
                //  Toast.makeText(this, "The app moved the camera.",
                //        Toast.LENGTH_SHORT).show();
            }
        }
    }

    override fun onCameraMove() {
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCameraMoveCanceled() {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCameraIdle() {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBackPressed() {
        //Display alert message when back button has been pressed
        buttonHandler.onClick()
        return
    }

    private fun getRoute(origin: Point, destination: Point) {
        NavigationRoute.builder(this)//Mapbox.getAccessToken()!!
            .accessToken("pk.eyJ1IjoiaW4xMG1hcCIsImEiOiJjang2MHp3ZnQwN2FtNDhvZ2tobTVremduIn0.Lrn9_3WMVW46j0OSSbBtaA")
            .origin(origin)
            .destination(destination)
            .build()
            .getRoute(object : Callback<DirectionsResponse> {
                override fun onResponse(
                    call: Call<DirectionsResponse>,
                    response: Response<DirectionsResponse>,
                ) {
                    // You can get the generic HTTP info about the response
                    if (response.body() == null) {
                        return
                    } else if (response.body()!!.routes().size < 1) {
                        return
                    }
                    currentRoute = response.body()!!.routes()[0]

                    val simulateRoute = false
                    val options = NavigationLauncherOptions.builder()
                        .directionsRoute(currentRoute)
                        .shouldSimulateRoute(simulateRoute)
                        .build()
                    NavigationLauncher.startNavigation(this@DashboardActivity, options)
                    // Draw the route on the map
//                    if (navigationMapRoute != null) {
//                        navigationMapRoute!!.removeRoute()
//                    } else {
//                        navigationMapRoute =
//                            NavigationMapRoute(null, mapView, mapboxMap, R.style.NavigationMapRoute)
//                    }
//                    navigationMapRoute!!.addRoute(currentRoute)
                }

                override fun onFailure(call: Call<DirectionsResponse>, throwable: Throwable) {

                }
            })
    }

    private fun timerStartCreation() {
        workStatusTimerTV_StartServiceLay.text = "0:0:0"
        sec = 0
        min = 0
        hr = 0
        llElapsedTimerTV_StartServiceLay.visibility = View.VISIBLE
        tvWorkScope_StartServiceLay.visibility = View.GONE
        timer = object : CountDownTimer(300000000, 1000) {
            var hour = ""
            var minute = ""
            var second = ""
            override fun onTick(millisUntilFinished: Long) {
                sec++
                if (sec == 59) {
                    min++
                    sec = 0
                }
                if (min == 59) {
                    min = 0
                    hr++
                }
                if (hr == 23) {
                    hr = 0
                }

                workStatusTimerTV_StartServiceLay.text = "$hr : $min : $sec"
                hour = ""
                minute = ""
                second = ""
            }

            override fun onFinish() {
            }
        }.start()
    }

    private fun showAlertDutyChangeAfterWorkAccepted() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage(resources.getString(R.string.are_you_sure_to_cancel_the_booking))
        builder.setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
            callRequestCancelApi()
        }
        builder.setNegativeButton(resources.getString(R.string.no)) { dialog, _ ->
            dialog.dismiss()
        }
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    override fun onStart() {
        Constants.GlobalSettings.fromIA = false
        super.onStart()
    }

    private fun hasPermissions(context: Context?, vararg permissions: String?): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        permission!!
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return false
                }
            }
        }
        return true
    }

    override fun onCompleteProfileCompleted(mPost: CompleteProfileResponse) {
        destroyDialog()
        if (mPost.status == 1) {
            if (mPost.data.privacy_policy_accept == 0) {
                Constants.GlobalSettings.privacyPolicy = true
            }

            if (mPost.data.terms_condition_accept == 0) {
                Constants.GlobalSettings.termsConditions = true
            }

            bannerCard_HO.visibility = View.GONE
            ServiceManNameTOP.text =
                (resources.getString(R.string.hello) + " " + mPost.data.name)
            userNameTVM.text = mPost.data.name

            /*when {
                mPost.data.privacy_policy_accept == 0 -> {
                    bannerCard_HO.visibility = View.VISIBLE
                    header_HO.text = resources.getString(R.string.privacy_updated)
                    subHeader_HO.text = resources.getString(R.string.desc_privacy_updated)
                    yes_HO.setOnClickListener {
                        openactivity(PrivacyPolicyActivity())
                    }
                }
                mPost.data.terms_condition_accept == 0 -> {
                    bannerCard_HO.visibility = View.VISIBLE
                    header_HO.text = resources.getString(R.string.terms_updated)
                    subHeader_HO.text = resources.getString(R.string.desc_terms_updated)
                    yes_HO.setOnClickListener {
                        openactivity(TermsAndCondition())
                    }
                }
                else -> {
                    bannerCard_HO.visibility = View.GONE
                }
            }*/
        }
    }

    override fun onCompleteProfileFailed(msg: String) {
        destroyDialog()
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun onStartFailed(error: SinchError?) {
        // Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
    }

    override fun onStarted() {
    }

    override fun onServiceConnected() {
        sinchServiceInterface.setStartListener(this)
        initCalling()
    }

    private fun initCalling() {
        if (localStorage(this).completeCustomer != null) {
            val user = localStorage(this).completeCustomer

            var callerSelfId: String = user.name

            if (!user.lastname.isNullOrEmpty()) {
                callerSelfId = callerSelfId + " " + user.lastname
            }
            if (!user.mobile.isNullOrEmpty()) {
                callerSelfId = callerSelfId + "-" + user.mobile
            }


            if (callerSelfId != sinchServiceInterface.userName) {
                sinchServiceInterface.stopClient()
            }

            if (!sinchServiceInterface.isStarted) {
                sinchServiceInterface.startClient(callerSelfId)
            }
        }
    }

    private fun updateDeviceIdOnServer() {
        /*OneSignal.idsAvailable { userId, registrationId ->
            var text = "OneSignal UserID:\n$userId\n\n"

            text += if (registrationId != null)
                "Google Registration Id:\n$registrationId"
            else
                "Google Registration Id:\nCould not subscribe for push"
            val requestUpdateDeviceUser = RequestUpdateDeviceUser()
            requestUpdateDeviceUser.userId = userid
            requestUpdateDeviceUser.status = 1
            requestUpdateDeviceUser.deviceId = userId

            val callServiceProviders = LoginAPI.loginUser().saveDeviceId(requestUpdateDeviceUser)
            callServiceProviders.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful)
                        Log.d("Response from backend", response.body().toString())
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {

                }
            })
        }*/

//        val device: OSDeviceState? = OneSignal.getDeviceState()
//        val userId: String = device!!.userId
//        val registrationId: String = device.emailUserId
//        val registrationId: String = device.userId

//        var text = "OneSignal UserID:\n$userId\n\n"
//        text += "Google Registration Id:\n$registrationId"
//        val requestUpdateDeviceUser = RequestUpdateDeviceUser()
//        requestUpdateDeviceUser.userId = userid
//        requestUpdateDeviceUser.status = 1
//        requestUpdateDeviceUser.deviceId = userId
//
//        val callServiceProviders = APIClient.getApiInterface().saveDeviceId(requestUpdateDeviceUser)
//        callServiceProviders.enqueue(object : Callback<Void> {
//            override fun onResponse(call: Call<Void>, response: Response<Void>) {
//            }

//            override fun onFailure(call: Call<Void>, t: Throwable) {
//
//            }
//        })

    }

    override fun jobDone1() {

    }

    override fun jobDone2(polyline1: Polyline) {
        polyline = polyline1
    }

    override fun bookNow() {
    }

    override fun callNow() {
        callToCustomer()
    }

    override fun showOnMap() {
        // show on map

    }

    override fun cancelBooking() {

    }

    override fun about() {
        slidingRootNav?.closeMenu(true)
        handler.postDelayed(
            {
                startActivity(Intent(this, AboutActivity::class.java))
                overridePendingTransition(0, 0)
            },
            navigationDelay
        )
    }

    override fun privacy() {
        slidingRootNav?.closeMenu(true)
        handler.postDelayed(
            {
                startActivity(Intent(this, PrivacyPolicyActivity::class.java))
                overridePendingTransition(0, 0)
            },
            navigationDelay
        )
    }

    override fun settings() {
        /*slidingRootNav?.closeMenu(true)
        handler.postDelayed({ startActivity(Intent(this, SettingsActivity::class.java)) }, navigationDelay)*/
        languageChangeDialogView()
    }

    override fun contactUs() {
        slidingRootNav?.closeMenu(true)
        handler.postDelayed({
            startActivity(Intent(this, ContactUs::class.java))
            overridePendingTransition(0, 0)
        }, navigationDelay)
    }

    override fun myAccount() {
        slidingRootNav?.closeMenu(true)
        MenuNavigation(this, handler).OpenMyAccount()
    }

    override fun myBookings() {
        val userType =
            SharedPreferencesHelper.getInt(this, Constants.SharedPrefs.User.PERSON_TYPE, 2)
        if (userType == 3) {
            slidingRootNav?.closeMenu(true)
            handler.postDelayed(
                {
                    startActivity(Intent(this, ServiceHistoryActivity::class.java))
                    overridePendingTransition(0, 0)
                },
                navigationDelay
            )
            //handler.postDelayed({ startActivity(Intent(this, CompanyServiceHistoryActivity::class.java)) }, navigationDelay)
        } else {
            slidingRootNav?.closeMenu(true)
            handler.postDelayed(
                {
                    startActivity(Intent(this, ServiceHistoryActivity::class.java))
                    overridePendingTransition(0, 0)
                },
                navigationDelay
            )
        }

    }

    override fun logout() {
        try {
            val builder =
                AlertDialog.Builder(this@DashboardActivity,
                    R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog)
            builder.setTitle(resources.getString(R.string.log_out))
            builder.setMessage(resources.getString(R.string.desc_logout))
            builder.setPositiveButton(
                resources.getString(R.string.log_out)
            ) { _, _ ->
                mLogoutWithChangeStatus()
            }
            builder.setNegativeButton(
                resources.getString(R.string.cancel)
            ) { dialog, _ ->
                dialog.cancel()
            }
            builder.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun myEarnings() {
        //Disabled for Pay Subscription
        /*slidingRootNav?.closeMenu(true)
              handler.postDelayed(
                  {
                      startActivity(Intent(this, EarningsActivity::class.java))
                      overridePendingTransition(0, 0)
                  },
                  navigationDelay
              )*/
    }

    override fun companyPros() {
        slidingRootNav?.closeMenu(true)
        handler.postDelayed(
            {
                startActivity(Intent(this, CompanyPros::class.java))
                overridePendingTransition(0, 0)
            },
            navigationDelay
        )
    }

    private fun makePaymentInitialize(
        myBookingId: String,
        customerID: String?,
        estimationFee: Double,
        serviceId: String?,
    ) {
        showProgressDialog("")
        val header =
            SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")
        val userId = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "")
        var totalAmount = "1"
        val workDescription = "workDescription"
        val paymentType = "1"

        val callServiceProviders = APIClient.getApiInterface()
            .paymentInitilize(header,
                myBookingId,
                totalAmount,
                paymentType,
                userId,
                workDescription)

        val request = PaymentRequestClass()

        callServiceProviders.enqueue(object : Callback<PaymentInitilizeResponse> {
            override fun onResponse(
                call: Call<PaymentInitilizeResponse>,
                response: Response<PaymentInitilizeResponse>,
            ) {

                if (response.isSuccessful) {
                    if (response.body()?.status == 1) {
                        request.booking_Id = myBookingId
                        if (response.body()!!.data!!.applicationFee != null)
                            request.applicationFee = response.body()!!.data!!.applicationFee!!
                        else
                            request.applicationFee = 0.0
                        if (response.body()!!.data!!.feePercent != null)
                            request.fee_percent = response.body()!!.data!!.feePercent!!
                        else
                            request.fee_percent = ""
                        // request.total_amount = totalAmount
                        request.customer_id = customerID
                        request.pay_status = false
                        request.accepted_status = true
                        request.editing_status = false
                        request.work_scope = workDescription
                        if (response.body()!!.data!!.serviceFee != null)
                            request.service_amount =
                                response.body()!!.data!!.servicemanServiceAmount
                        else
                            request.service_amount = 0.0
                        request.payment_type = paymentType.toInt()
                        if (response.body()!!.data!!.processingFee != null)
                            request.processingFee = response.body()!!.data!!.processingFee!!
                        else
                            request.processingFee = 0.0
                        request.service_man_id = userId?.toInt()
                        if (response.body()!!.data!!.tax != null)
                            request.tax = response.body()!!.data!!.tax!!.toDouble()
                        else
                            request.tax = 0.0
                        if (response.body()!!.data!!.taxPercent != null)
                            request.tax_percent = response.body()!!.data!!.taxPercent!!
                        else
                            request.tax_percent = 0.0
                        if (response.body()!!.data!!.totalAmount != null) {
                            request.total_amount = response.body()!!.data!!.totalAmount.toString()
                            totalAmount = response.body()!!.data!!.totalAmount.toString()
                        } else
                            request.total_amount = ""


                        val paymentNodePath = "payments/$customerID"//"payments/$bookingId"
                        mDatabasePayment =
                            FirebaseDatabase.getInstance().getReference(paymentNodePath)

                        val serviceManNodePath = "bookings/service_men/$userid"
                        dbServiceMan =
                            FirebaseDatabase.getInstance().getReference(serviceManNodePath)

                        try {
                            mDatabasePayment.child(myBookingId).setValue(null)
                            mDatabasePayment.child(myBookingId).setValue(request)
                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                        }

                        checkPayment(myBookingId, customerID, serviceId, paymentType)

                        /*SharedPreferencesHelper.putString(
                            this@DashboardActivity,
                            Constants.SharedPrefs.User.ESTIMATE_AMOUNT,
                            ""
                        )
                        SharedPreferencesHelper.putString(
                            this@DashboardActivity,
                            Constants.SharedPrefs.User.ESTIMATE_DESCRIPTION,
                            ""
                        )
                        startActivity(
                            Intent(this@DashboardActivity, CustomerRating::class.java).putExtra(
                                "bookingId",
                                myBookingId
                            )
                                .putExtra("customerId", customerID)
                                .putExtra("serviceId", serviceId)
                        )*/
                    } else {
                        ShowToast(response.body()?.message!!)
                    }
                }
            }

            override fun onFailure(call: Call<PaymentInitilizeResponse>, t: Throwable) {
                destroyDialog()

            }
        })
    }

    fun checkPayment(
        bookingId: String,
        customerId: String?,
        serviceId: String?,
        paymentSelected: String,
    ) {
        mDatabasePayment.ref.addChildEventListener(object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val data = p0.getValue(PaymentRequestClass::class.java)
                if (data?.booking_Id == bookingId) {

                    if (!data.accepted_status) {
                        Toast.makeText(
                            this@DashboardActivity,
                            "Customer has requested for a re-quote. Please update amount and generate new invoice",
                            Toast.LENGTH_LONG
                        ).show()
                    } else if (data.pay_status == true) {
                        destroyDialog()
                        requestCV.visibility = View.INVISIBLE
                        DutyChangeImage.visibility = View.VISIBLE
                        bookingAccpted = false
                        isJobDone = true
                        removeDestinationMarker()
                        if (mapss != null)
                            mapss!!.removePolyLine()
                        freezCurrentLocation = false
                        loadDashboardCount()
                        val user = localStorage(this@DashboardActivity).completeCustomer
                        if (user != null) ServiceManNameTOP.text =
                            (resources.getString(R.string.hello) + " " + user.name)
                        selectedCV.visibility = View.VISIBLE

                        llAccept_HomeBottomBtn.visibility = View.VISIBLE
                        llCall_HomeBottomBtn.visibility = View.GONE
                        llArrived_HomeBottomBtn.visibility = View.GONE
                        arrivedLay.visibility = View.GONE
                        startfinishlay.visibility = View.GONE
                        llEnd_HomeBottomBtn.visibility = View.GONE
                        imgBtnNavigate.visibility = View.GONE
                        btnCancel_HomeBottomBtn.text = getString(R.string.cancel)
                        llCancel_HomeBottomBtn.isEnabled = false

                        llElapsedTimerTV_StartServiceLay.visibility = View.VISIBLE
                        tvWorkScope_StartServiceLay.visibility = View.GONE
                        //changeStatusOfServiceMan()
                        map?.clear()

                        mDatabasePayment.child(bookingId).removeValue()
                        dbServiceMan.child(bookingId).removeValue()

                        SharedPreferencesHelper.putString(
                            this@DashboardActivity,
                            Constants.SharedPrefs.User.ESTIMATE_AMOUNT,
                            ""
                        )
                        SharedPreferencesHelper.putString(
                            this@DashboardActivity,
                            Constants.SharedPrefs.User.ESTIMATE_DESCRIPTION,
                            ""
                        )
                        startActivity(
                            Intent(this@DashboardActivity, CustomerRating::class.java).putExtra(
                                "bookingId",
                                bookingId
                            )
                                .putExtra("customerId", customerId)
                                .putExtra("serviceId", serviceId)
                        )
                        overridePendingTransition(0, 0)
                    }
                }
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildRemoved(p0: DataSnapshot) {
                try {
                    val data = p0.getValue(PaymentRequestClass::class.java)

                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }

        })
    }
}
