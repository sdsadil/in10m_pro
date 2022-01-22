package com.in10mServiceMan.ui.activities.services

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.in10mServiceMan.Models.HomeService
import com.in10mServiceMan.Models.Service
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.BaseActivity
import com.in10mServiceMan.ui.activities.home.NavigationAdapter
import com.in10mServiceMan.ui.activities.my_bookings.MyBookingsActivity
import com.in10mServiceMan.ui.activities.profile.ProfileActivity
import com.in10mServiceMan.ui.activities.sub_services.SubServicesActivity
import com.in10mServiceMan.ui.apis.LoginAPI
import com.in10mServiceMan.ui.base.In10mBaseActivity
import com.yarolegovich.slidingrootnav.SlidingRootNav
import kotlinx.android.synthetic.main.activity_services.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServicesActivity : In10mBaseActivity(), NavigationAdapter.NavigationCallbacks, ServicesAdapter.SelectedServiceCallback {
    override fun companyPros() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun myEarnings() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun logout() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun selectService(item: Service) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun removeSelectedService(item: Service) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val REQUEST_CODE_SELECTION_SERVICES_SUBSERVICES = 111
    override fun openSubService(item: Service) {

        val listType = object : TypeToken<Service>() {}.type
        val json = Gson().toJson(item, listType)

        val intent = Intent(this, SubServicesActivity::class.java)
        intent.putExtra("service", json)

        val myIntent = getIntent()
        myIntent.putExtra("service", json)

        setResult(Activity.RESULT_OK, myIntent)
        finish()
        /*if (item.subService.size > 0) {
            handler.postDelayed({
                // startActivity(intent)
                startActivityForResult(intent, REQUEST_CODE_SELECTION_SERVICES_SUBSERVICES)
            }, navigationDelay)
        } else {
            Toast.makeText(this, "This service has no any sub service", Toast.LENGTH_SHORT).show()
        }*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // super.onActivityResult(requestCode, resultCode, data)

        // Check which request we're responding to
        if (requestCode == REQUEST_CODE_SELECTION_SERVICES_SUBSERVICES && data != null) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                if (data.getStringExtra("service") != null) {
                    intent.putExtra("service", data.getStringExtra("service"))
                    setResult(Activity.RESULT_OK, data)
                    finish()
                }
            }
        }


    }

    override fun about() {
        //  slidingRootNav?.closeMenu(true)
        // handler.postDelayed({ startActivity(Intent(this, AboutActivity::class.java)) }, navigationDelay)
    }

    override fun privacy() {
        //  slidingRootNav?.closeMenu(true)
        // handler.postDelayed({ startActivity(Intent(this, PrivacyPolicyActivity::class.java)) }, navigationDelay)
    }


    override fun settings() {
        // slidingRootNav?.closeMenu(true)
        // handler.postDelayed({ startActivity(Intent(this, SettingsActivity::class.java)) }, navigationDelay)
        languageChangeDialogView()
    }

    override fun contactUs() {
        // slidingRootNav?.closeMenu(true)
        // handler.postDelayed({ startActivity(Intent(this, ContactUs::class.java)) }, navigationDelay)
    }

    override fun myAccount() {

        slidingRootNav?.closeMenu(true)
        handler.postDelayed({ startActivity(Intent(this, ProfileActivity::class.java)) }, navigationDelay)
    }

    override fun myBookings() {

        slidingRootNav?.closeMenu(true)
        handler.postDelayed({ startActivity(Intent(this, MyBookingsActivity::class.java)) }, navigationDelay)
    }

    /* override fun onMapReady(googleMap: GoogleMap?) {
         googleMap ?: return
         with(googleMap) {
             map = googleMap
             setMapStyle(com.google.android.gms.maps.model.MapStyleOptions.loadRawResourceStyle(this@ServicesActivity, com.in10mServiceMan.R.raw.map_style))
 //            moveCamera(CameraUpdateFactory.newLatLngZoom(SYDNEY, ZOOM_LEVEL))
 //            addMarker(MarkerOptions().position(SYDNEY)).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
             onLocationFetched()
         }
     }
     @SuppressLint("MissingPermission")
     @WithPermissions(permissions = [(Manifest.permission.ACCESS_FINE_LOCATION)])
     private fun onLocationFetched() {

         fusedLocationClient.lastLocation
                 .addOnSuccessListener { location: Location? ->

                     if (map != null && location != null) {

                         mLastLocation = location
                         if (mCurrLocationMarker != null) {
                             mCurrLocationMarker?.remove()
                         }

                         //Place current location marker
                         val latLng = LatLng(location.latitude, location.longitude)
                         val markerOptions = MarkerOptions()
                         markerOptions.position(latLng)
                         markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
                         mCurrLocationMarker = map!!.addMarker(markerOptions)

                         //move map camera
                         map!!.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, mZoomLevel), 1000, null)

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
                 }
     }

     private lateinit var fusedLocationClient: FusedLocationProviderClient*/
    // val mZoomLevel = 13f

    //  var mLastLocation: Location? = null
    //  var mCurrLocationMarker: Marker? = null
    //  var map: GoogleMap? = null
    private var slidingRootNav: SlidingRootNav? = null

    private var handler = Handler()

    private val navigationDelay = 180L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_services)
        // fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        //  val fm = supportFragmentManager
        //  val mapFragment = SupportMapFragment.newInstance()

        // fm.beginTransaction().replace(R.id.mapContainer, mapFragment).commit()
//        val mapFragment : SupportMapFragment? = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        //  mapFragment?.getMapAsync(this@ServicesActivity)

        menuIV.setOnClickListener {
            finish()
        }
        // initDrawer(savedInstanceState)
        setServiceList()

        // GoogleStaticImage(this,mapImageView).load()
        //  loadMap()
    }


    private fun setServiceList() {
        val homeCall = LoginAPI.loginUser().services
        homeCall.enqueue(object : Callback<HomeService> {
            override fun onResponse(call: Call<HomeService>, response: Response<HomeService>) {
                if (response.isSuccessful) {
                    bindData(response.body())
                } else {

                }
            }

            override fun onFailure(call: Call<HomeService>, t: Throwable) {

            }
        })


    }

    private fun bindData(body: HomeService?) {
        val ListRV = findViewById<RecyclerView>(R.id.servicesRV)
        ListRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        ListRV.adapter = ServicesAdapter(this, this, body)
    }

    /* private fun initDrawer(savedInstanceState: Bundle?) {
         val navigationView: View = LayoutInflater.from(this).inflate(R.layout.drawyer_layout, null, false);


         slidingRootNav = SlidingRootNavBuilder(this)
                 .withMenuOpened(false)
                 .withContentClickableWhenMenuOpened(false)
                 .withSavedState(savedInstanceState)
                 .withMenuView(navigationView)
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
         navigationRV.adapter = NavigationAdapter(this)

         menuIV.setOnClickListener {
             slidingRootNav?.openMenu(true)
         }
     }*/
}
