package com.in10mServiceMan.ui.activities.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

import com.in10mServiceMan.R
import com.in10mServiceMan.db.Dummy
import com.in10mServiceMan.db.ServiceMan
import com.in10mServiceMan.ui.activities.confirm_address.ConfirmAddressActivity
import com.in10mServiceMan.ui.activities.my_bookings.MyBookingsActivity
import com.in10mServiceMan.ui.activities.profile.ProfileActivity
import com.in10mServiceMan.ui.fragments.home.ServiceManCallBack
import com.in10mServiceMan.ui.fragments.home.ServiceManDetails
import com.in10mServiceMan.utils.Mapss
import com.in10mServiceMan.utils.RequestCode
import com.in10mServiceMan.utils.WPrefs
import com.yarolegovich.slidingrootnav.SlidingRootNav
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import kotlinx.android.synthetic.main.activity_home_main.*
import kotlinx.android.synthetic.main.drawyer_layout.*


class HomeActivity : AppCompatActivity(), NavigationAdapter.NavigationCallbacks, OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, Mapss.RouteStatus, SelectServiceManAdapter.SelectServiceManCallBack, ServiceManCallBack {

    var permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    var permsRequestCode = 1

    override fun companyPros() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun myEarnings() {
    }

    override fun logout() {
    }

    override fun bookNow() {
        //Toast.makeText(this@HomeActivity,"Book Now called", Toast.LENGTH_SHORT).show()
        proceedBookNow()
    }


    override fun callNow() {

    }

    override fun showOnMap() {

    }

    override fun cancelBooking() {

    }


    override fun about() {
        //  slidingRootNav?.closeMenu(true)
        //  handler.postDelayed({ startActivity(Intent(this, AddCardActivity::class.java)) }, navigationDelay)
    }

    override fun privacy() {
        //   slidingRootNav?.closeMenu(true)
        //  handler.postDelayed({ startActivity(Intent(this, FareDetailsActivity::class.java)) }, navigationDelay)
    }


    override fun settings() {
        // slidingRootNav?.closeMenu(true)
        // handler.postDelayed({ startActivity(Intent(this, SettingsActivity::class.java)) }, navigationDelay)
    }

    override fun contactUs() {
        // slidingRootNav?.closeMenu(true)
        //  handler.postDelayed({ startActivity(Intent(this, InviteFriendsActivity::class.java)) }, navigationDelay)
    }

    override fun myAccount() {

        slidingRootNav?.closeMenu(true)
        handler.postDelayed({ startActivity(Intent(this, ProfileActivity::class.java)) }, navigationDelay)
    }

    override fun myBookings() {

        slidingRootNav?.closeMenu(true)
        handler.postDelayed({ startActivity(Intent(this, MyBookingsActivity::class.java)) }, navigationDelay)
    }

    private var slidingRootNav: SlidingRootNav? = null


    private var handler = Handler()

    private val navigationDelay = 180L

    private var isPickupSelected: Boolean? = null

    private var isOptionConfirmed = false

    private var isLocationSelected = false

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    val mZoomLevel = 16f

    // var mLastLocation: Location? = null
    var mCurrLocationMarker: Marker? = null
    var map: GoogleMap? = null

    var serviceManList = Dummy.getServiceManList()
    var markersList: MutableList<Marker> = ArrayList();
    var userLocation = LatLng(29.374009, 47.976461);
    var mapss: Mapss? = null

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_main)
        if (!hasPermissions(this, *permissions)) {
            ActivityCompat.requestPermissions(this, permissions, permsRequestCode)
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val fm = supportFragmentManager
        val mapFragment = SupportMapFragment.newInstance()

        fm.beginTransaction().replace(R.id.mapContainer, mapFragment).commit()
        mapFragment?.getMapAsync(this@HomeActivity)

        initDrawer(savedInstanceState)

        lstSrvIV.setOnClickListener {
            serviceManListRV.visibility = View.VISIBLE
            backIV.visibility = View.VISIBLE
            menuIV.visibility = View.INVISIBLE
            lstSrvIV.visibility = View.GONE

            removeRoute()
        }

        backIV.setOnClickListener {
            backButtonPressed()
        }

        closeCV.setOnClickListener {
            removeRoute()
        }

        centerGPSIV.setOnClickListener {
            if (map != null) {
                map!!.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, mZoomLevel), 1000, null)
            }
        }

        loadServiceMans()

        btnDetails.setOnClickListener {
            btnDetailsProceed()
        }
        lvBtnDetails.setOnClickListener {
            btnDetailsProceed()
        }

        btnBookNow.setOnClickListener {
            proceedBookNow()
        }
        lvBtnBookNow.setOnClickListener {
            proceedBookNow()
        }


    }

    private fun btnDetailsProceed() {
        val fragmentManager = supportFragmentManager
        val newFragment = ServiceManDetails()

        val bundle = Bundle()
        bundle.putInt("action", RequestCode.FromHome)
        newFragment.arguments = bundle

        val transaction = fragmentManager.beginTransaction()
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit()
    }

    private fun proceedBookNow() {
        startActivity(Intent(this, ConfirmAddressActivity::class.java))
    }

    private fun backButtonPressed() {
        serviceManListRV.visibility = View.GONE
        backIV.visibility = View.GONE
        menuIV.visibility = View.VISIBLE
        lstSrvIV.visibility = View.VISIBLE
    }

    private fun loadServiceMans() {
        val ListRV = findViewById<RecyclerView>(R.id.serviceManListRV)
        ListRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        ListRV.adapter = SelectServiceManAdapter(this, serviceManList, this)
    }


    private fun initDrawer(savedInstanceState: Bundle?) {
        val navigationView: View = LayoutInflater.from(this).inflate(R.layout.drawyer_layout, null, false);


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

        slidingRootNav?.layout?.setOnTouchListener { v, event ->

            if (event.action == MotionEvent.ACTION_DOWN) {
                if (slidingRootNav != null &&
                        slidingRootNav!!.isMenuOpened) {
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
    }


    @SuppressLint("MissingPermission")
    private fun onLocationFetched() {

//        fusedLocationClient.lastLocation
//                .addOnSuccessListener { location: Location? ->

        if (map != null) {

            //   mLastLocation = userLocation
            if (mCurrLocationMarker != null) {
                mCurrLocationMarker?.remove()
            }

            Log.i("EEEEEEEEEEEEEE", "7")

            //Place current location marker
            // val latLng = LatLng(location.latitude, location.longitude)
            val latLng = userLocation
            val markerOptions = MarkerOptions()
            markerOptions.position(latLng)
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
            mCurrLocationMarker = map!!.addMarker(markerOptions)


            serviceManList.forEach {
                val latLng2 = LatLng(it.latitude, it.Longitude)

                val markerOptions2 = MarkerOptions()
                markerOptions2.position(latLng2)
                markerOptions2.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_p))

                val custMarker = map!!.addMarker(markerOptions2);


                custMarker!!.tag = it;
                markersList.add(custMarker)
            }


            //move map camera
            map!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, mZoomLevel), 1000, null)

            map!!.setOnMarkerClickListener(this)
//                        mapView.getMapAsync {
//
//                            val markerIcon = IconFactory.getInstance(this).fromResource(R.drawable.ic_marker)
//                            val position = CameraPosition.Builder()
//                                    .target(LatLng(location.latitude, location.longitude)) // Sets the new camera position
//                                    .zoom(12.0) // Sets the zoom to level 10
//                                    .tilt(20.0) // Set the camera tilt to 20 degrees
//                                    .build() // Builds the CameraPosition object from the builder
//                            it.cameraPosition = position
//                            it.addMarker(MarkerOptions().icon(markerIcon).position(LatLng(location.latitude, location.longitude)))
//                        }
        }
        //}

    }


    // var polyLine:Polyline?=null;
    //  private lateinit var MapssCallback:Mapss.RouteStatus;

    @SuppressLint("SetTextI18n")
    override fun onMarkerClick(marker: Marker): Boolean {

        if (markersList.any { it.position == marker.position }) {
            selectServiceMan(marker.tag as ServiceMan)
        }
        return true
    }

    override fun selected(serviceMan: ServiceMan) {
        backButtonPressed()
        selectServiceMan(serviceMan)

    }

    fun selectServiceMan(serviceMan: ServiceMan) {
        val destination = LatLng(serviceMan.latitude, serviceMan.Longitude)
        if (mapss == null)
            mapss = Mapss(map, userLocation, destination, this@HomeActivity, this);
        else {
            mapss!!.updateRoute(map, userLocation, destination)
        }


        findViewById<ImageView>(R.id.serviceManIV).setImageResource(serviceMan.Image)

        ServiceManName.text = serviceMan.Name

        ratingTV.text = serviceMan.Rating.toString()

        titleTV.text = serviceMan.Title

        addressTV.text = serviceMan.Address
        timeTV.text = serviceMan.ETA.toString() + "mins"
        distanceTV.text = serviceMan.Distance.toString() + "km away"
    }

    fun removeRoute() {
        if (mapss != null) {
            mapss!!.removePolyLine()
        }
        selectedCV.visibility = View.GONE
    }

    override fun jobDone1() {
        // route downloaded
        //Log.i("eeee","Job done1")
    }

    override fun jobDone2(polyline: Polyline) {
        // route generated
        // Log.i("eeee","Job done2")
        selectedCV.visibility = View.VISIBLE
    }
    /*  fun drawPolyline( destination:LatLng){
           // Add a polyline to the map.
          polyLine = map!!.addPolyline((PolylineOptions())
                  .clickable(true)
                  .add(userLocation,
                           destination))

      }*/


    public override fun onStart() {
        super.onStart()
//        mapView.onStart()
    }

    public override fun onResume() {
        super.onResume()
//        mapView.onResume()
        WPrefs.setLogin(true)
    }

    public override fun onPause() {
        super.onPause()
//        mapView.onPause()
    }

    public override fun onStop() {
        super.onStop()
//        mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
//        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
//        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
//        mapView.onSaveInstanceState(outState!!)
    }

    override fun onBackPressed() {

        super.onBackPressed()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap ?: return
        with(googleMap) {
            map = googleMap
            setMapStyle(MapStyleOptions.loadRawResourceStyle(this@HomeActivity, R.raw.map_style))
//            moveCamera(CameraUpdateFactory.newLatLngZoom(SYDNEY, ZOOM_LEVEL))
//            addMarker(MarkerOptions().position(SYDNEY)).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
            onLocationFetched()
        }
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
}
