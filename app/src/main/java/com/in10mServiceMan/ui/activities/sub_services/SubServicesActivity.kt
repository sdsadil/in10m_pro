package com.in10mServiceMan.ui.activities.sub_services

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.in10mServiceMan.Models.Service
import com.in10mServiceMan.Models.SubService
import com.in10mServiceMan.Models.ViewModels.ServiceWithSubService
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.home.HomeActivity

import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_sub_services.*
import kotlinx.android.synthetic.main.item_services.view.*

class SubServicesActivity : AppCompatActivity(),SubServicesAdapter.SelectedSubServiceCallback {

  /*  override fun onMapReady(googleMap: GoogleMap?) {
        googleMap ?: return
        with(googleMap) {
            map = googleMap
            setMapStyle(com.google.android.gms.maps.model.MapStyleOptions.loadRawResourceStyle(this@SubServicesActivity, com.in10mServiceMan.R.raw.map_style))
//            moveCamera(CameraUpdateFactory.newLatLngZoom(SYDNEY, ZOOM_LEVEL))
//            addMarker(MarkerOptions().position(SYDNEY)).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker))
            onLocationFetched()
        }
    }*/
  /*  @SuppressLint("MissingPermission")
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
*/
    override fun ChoosenSubService(selectSubServiceIndex: ArrayList<Int>) {
        chosenServiceWithSubService!!.subServices.clear()
        for (item in selectSubServiceIndex) {
            chosenServiceWithSubService!!.subServices.add(chosenServiceWithSubService!!.service.subService.get(item))
        }
    }

    // private var chosenSubService: SubService? =null

    private var chosenServiceWithSubService:ServiceWithSubService?=null


  //  private var isLocationSelected = false


    private lateinit var fusedLocationClient: FusedLocationProviderClient
 //   val mZoomLevel = 13f

  //  var mLastLocation: Location? = null
  //  var mCurrLocationMarker: Marker? = null
  //  var map: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_services)

       // fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

      //  val fm = supportFragmentManager
      //  val mapFragment = SupportMapFragment.newInstance()

      //  fm.beginTransaction().replace(R.id.mapContainer, mapFragment).commit()
//        val mapFragment : SupportMapFragment? = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
      //  mapFragment?.getMapAsync(this@SubServicesActivity)

        // get service item from callee page

        chosenServiceWithSubService= ServiceWithSubService()

        val intent = intent

        val listType = object : TypeToken<Service>() { }.type


        val service = Gson().fromJson<Service>(intent.getStringExtra("service"), listType)

        chosenServiceWithSubService!!.service=service;
        chosenServiceWithSubService!!.subServices=ArrayList<SubService>()
        chosenServiceWithSubService!!.subServices.add(service.subService[0])
        setSubServiceList(service)
        textView2.text=service.serviceName;
        chosenServiceWithSubService!!.experience=1
        Picasso.get().load("http://"+service.serviceIcon).placeholder(R.drawable.icon_plumbing).fit().into(imageView)

        proceedImageView.setOnClickListener{

            if(chosenServiceWithSubService?.subServices!!.size>0)
            {
                val json = Gson().toJson(chosenServiceWithSubService)
                val intent1=intent
                intent.putExtra("service",json)
                setResult(Activity.RESULT_OK, intent1)
                finish()
            }else{
                Toast.makeText(this@SubServicesActivity,"Please choose any sub-service",Toast.LENGTH_SHORT).show()
            }

        }

        backIV.setOnClickListener{
            finish()
        }
    }
    private fun setSubServiceList(service: Service) {
        val ListRV=findViewById<RecyclerView>(R.id.SubServiceRV)
        ListRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        ListRV.adapter = SubServicesAdapter(this,this,service.subService)
    }
}
