package com.in10mServiceMan.ui.activities.confirm_address

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.location.Location
import com.in10mServiceMan.ui.base.In10mBaseActivity
import android.os.Bundle
import android.os.Handler
import androidx.constraintlayout.widget.ConstraintLayout
import android.view.Window
import android.widget.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.in10mServiceMan.R
import kotlinx.android.synthetic.main.activity_confirm_address.*
import android.view.WindowManager
import android.view.Gravity
import androidx.core.app.ActivityCompat
import com.in10mServiceMan.ui.activities.tracking_map.MapTrackingActivity
import android.content.pm.PackageManager

import android.os.Build

class ConfirmAddressActivity : In10mBaseActivity(), OnMapReadyCallback {
    var permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    var permsRequestCode = 1

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap ?: return
        with(googleMap) {
            map = googleMap
            setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this@ConfirmAddressActivity,
                    R.raw.map_style
                )
            )
            onLocationFetched()
        }
    }

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

                    map!!.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(latLng, mZoomLevel),
                        1000,
                        null
                    )

                }
            }
    }


    private var isLocationSelected = false

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    val mZoomLevel = 16f

    var mLastLocation: Location? = null
    var mCurrLocationMarker: Marker? = null
    var map: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_address)
        if (!hasPermissions(this, *permissions)) {
            ActivityCompat.requestPermissions(this, permissions, permsRequestCode)
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val fm = supportFragmentManager
        val mapFragment = SupportMapFragment.newInstance()
        fm.beginTransaction().replace(R.id.mapContainer, mapFragment).commit()
//        val mapFragment : SupportMapFragment? = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment.getMapAsync(this@ConfirmAddressActivity)


        backBtn.setOnClickListener {
            finish()
        }

        centerGPSIV.setOnClickListener {
            onLocationFetched()
        }

        // if not logged in
        presentSignInPopup()

        lvbtnConfirmLocation.setOnClickListener {
            presentConfirmLocationPopup()
        }
        btnConfirmLocation.setOnClickListener {
            presentConfirmLocationPopup()
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


    private fun presentConfirmLocationPopup() {
        val dialog = Dialog(this, android.R.style.Theme_Translucent_NoTitleBar)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_additional_details)


        val window = dialog.window
        val wlp = window!!.attributes

        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
        window.attributes = wlp
        dialog.window!!.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )


        val btnClose = dialog.findViewById<ImageView>(R.id.closeBtn)


        val btnBookNow = dialog.findViewById<LinearLayout>(R.id.lvBookNowBtn)
        val btnBookNow2 = dialog.findViewById<Button>(R.id.BookNowBtn)


        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        btnBookNow.setOnClickListener {
            proceedBookNow(dialog)
        }
        btnBookNow2.setOnClickListener {
            proceedBookNow(dialog)
        }
        dialog.show()
    }

    private fun proceedBookNow(dialog: Dialog) {
        dialog.dismiss()
        val dialog = Dialog(this, android.R.style.Theme_Translucent_NoTitleBar)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_request_confirmation)


        val window = dialog.window
        val wlp = window!!.attributes

        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
        window.attributes = wlp
        dialog.window!!.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )


        // val btnClose=dialog.findViewById<ImageView>(R.id.allsetBtnIV)

        //   btnClose.setOnClickListener {
        //    closeAllSet(dialog)
        //  }

        val handler = Handler()
        handler.postDelayed({

            dialog.dismiss()
            startActivity(Intent(this, MapTrackingActivity::class.java))
            finishAffinity() //close all recent activities
            // navigate to the serviceman tracking page
        }, 3000)

        dialog.show()
    }


    private fun presentSignInPopup() {
        val dialog = Dialog(this, android.R.style.Theme_Translucent_NoTitleBar)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_sign_in)


        val window = dialog.window
        val wlp = window!!.attributes

        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
        window.attributes = wlp
        dialog.window!!.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )


        val btnClose = dialog.findViewById<ImageView>(R.id.closeBtn)


        val mobileNumber = dialog.findViewById<EditText>(R.id.mobileET)
        val btnEnter = dialog.findViewById<LinearLayout>(R.id.lvEnterBtn)
        val btnEnterr = dialog.findViewById<Button>(R.id.EnterBtn)


        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        btnEnter.setOnClickListener {
            proceedToOTP(dialog, mobileNumber)
        }
        btnEnterr.setOnClickListener {
            proceedToOTP(dialog, mobileNumber)
        }
        dialog.show()
    }

    private fun proceedToOTP(dialog: Dialog, mobileNumber: EditText) {
        val mobilenumberVal = mobileNumber.text
        if (!mobilenumberVal.isNullOrEmpty()) {
            dialog.dismiss()
            Toast.makeText(this, "OTP is 1234", Toast.LENGTH_SHORT).show()
            // proceed to otp dialogue
            presentOtp()
        } else {
            Toast.makeText(this, "Mobile number is required", Toast.LENGTH_SHORT).show()
        }
    }

    private fun presentOtp() {
        val dialog = Dialog(this, android.R.style.Theme_Translucent_NoTitleBar)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_otp)


        val window = dialog.window
        val wlp = window!!.attributes

        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
        window.attributes = wlp
        dialog.window!!.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )


        val btnClose = dialog.findViewById<ImageView>(R.id.closeBtn)


        val otpET = dialog.findViewById<EditText>(R.id.otpET)
        val btnVerify = dialog.findViewById<LinearLayout>(R.id.lvVerifyBtn)
        val btnVerify2 = dialog.findViewById<Button>(R.id.VerifyBtn)


        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        btnVerify.setOnClickListener {
            allsetPopup(dialog, otpET)
        }
        btnVerify2.setOnClickListener {
            allsetPopup(dialog, otpET)
        }
        dialog.show()
    }

    private fun allsetPopup(dialog: Dialog, otpET: EditText) {
        val otpText = otpET.text
        if (!otpText.isNullOrBlank()) {
            dialog.dismiss()
            // present popup
            presentAllSetPopup()
        } else {
            Toast.makeText(this, "OTP is required", Toast.LENGTH_SHORT).show()
        }
    }

    private fun presentAllSetPopup() {

        val dialog = Dialog(this, android.R.style.Theme_Translucent_NoTitleBar)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.popup_allset)


        val window = dialog.window
        val wlp = window!!.attributes

        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
        window.attributes = wlp
        dialog.window!!.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.MATCH_PARENT
        )


        val btnClose = dialog.findViewById<ImageView>(R.id.allsetBtnIV)

        btnClose.setOnClickListener {
            closeAllSet(dialog)
        }

        val handler = Handler()
        handler.postDelayed({
            closeAllSet(dialog)
        }, 3000)

        dialog.show()

    }

    private fun closeAllSet(dialog: Dialog) {
        dialog.dismiss()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        }
    }
}
