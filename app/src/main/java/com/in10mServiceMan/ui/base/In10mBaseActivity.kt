package com.in10mServiceMan.ui.base


import android.os.Bundle
import com.in10mServiceMan.ui.activities.BaseActivity

import com.in10mServiceMan.ui.apis.APIClient

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater.from
import android.widget.TextView
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.sign_in.LoginActivity
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.in10mServiceMan.utils.localStorage
import kotlinx.android.synthetic.main.custom_session_expire_layout.*


open class In10mBaseActivity : BaseActivity(), IBaseView {
    var mBasePresenter: BasePresenter? = null
    override fun onSessionExpired() {
        if (!isFinishing || !!isDestroyed) {
            try {
                /* val builder = AlertDialog.Builder(this@In10mBaseActivity, R.style.AlertDialogDanger)
                 builder.setTitle("Session expired")
                 builder.setMessage("Please re-login to use application")
                 builder.setPositiveButton("OK"
                 ) { dialog, which ->
                     SharedPreferencesHelper.clearPreferences(this)
                     localStorage(this).logoutUser()
                     startActivity(Intent(this, LoginActivity::class.java))
                     finishAffinity()
                 }
                 builder.show()*/
                sessionOutCallLogin()
            } catch (e: Exception) {
            }
        }
    }

    override fun onSessionValid() {

    }

    override fun onResume() {
        super.onResume()
        if (!APIClient().publicAccessToken.isNullOrEmpty()) {
            val userId = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "")
            val header = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")

            mBasePresenter?.checkSession(header.toString(), userId.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBasePresenter = BasePresenter(this)
    }

    private fun sessionOutCallLogin() {
        val mDialogView = from(this).inflate(R.layout.custom_session_expire_layout, null)
        val mBuilder = AlertDialog.Builder(this@In10mBaseActivity).setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mAlertDialog.setCanceledOnTouchOutside(false)
        mAlertDialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val button = mDialogView.findViewById<TextView>(R.id.buttonOk)

        button.setOnClickListener {
            SharedPreferencesHelper.clearPreferences(this)
            localStorage(this).logoutUser()
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }
    }
}