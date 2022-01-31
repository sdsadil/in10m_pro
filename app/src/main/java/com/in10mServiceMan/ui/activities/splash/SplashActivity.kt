package com.in10mServiceMan.ui.activities.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.tracking_map.MapTrackingActivity
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.utils.localStorage
import com.in10mServiceMan.ui.activities.intro.IntroActivity
import com.in10mServiceMan.ui.activities.sign_in.LoginActivity
import com.in10mServiceMan.ui.base.In10mBaseActivity
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper

class SplashActivity : In10mBaseActivity() {

    var isLoggedIn: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
//        Fabric.with(this, Crashlytics())

        val storage = localStorage(this)

/*
        val isLoggedIn = storage.isLoggedIn
*/
        isLoggedIn = !SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "").isNullOrEmpty()

        if (isLoggedIn && (localStorage(this).completeCustomer != null)) {
            val loginAPI = APIClient()
            loginAPI.publicAccessToken = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")//storage.token
            // open home page
            val intent1 = Intent(this@SplashActivity, MapTrackingActivity::class.java)
            proceedNext(intent1)
        } else {
            // skip select language
            // val intent1= Intent(this@SplashActivity, SelectLanguage::class.java)
            if (!storage.introDone) {
                val intent1 = Intent(this@SplashActivity, IntroActivity::class.java)
                proceedNext(intent1)
            } else {
                val intent1 = Intent(this@SplashActivity, LoginActivity::class.java)//EnterPhoneNumberActivity
                proceedNext(intent1)
            }
        }
    }

    private fun proceedNext(intent1: Intent) {
        Handler().postDelayed({
            startActivity(intent1)
            finish()

        }, 3000)
    }
}
