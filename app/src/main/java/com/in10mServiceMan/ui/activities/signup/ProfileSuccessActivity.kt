package com.in10mServiceMan.ui.activities.signup

import android.content.Intent
import android.os.Bundle
import com.in10mServiceMan.models.CustomerCompleteProfile
import com.in10mServiceMan.R

import com.in10mServiceMan.ui.activities.sign_in.ILoginView
import com.in10mServiceMan.ui.activities.sign_in.LinkSendResponse
import com.in10mServiceMan.ui.activities.sign_in.LoginPresenter
import com.in10mServiceMan.ui.activities.sign_in.LoginResponse
import com.in10mServiceMan.ui.activities.dashboard.DashboardActivity
import com.in10mServiceMan.ui.base.In10mBaseActivity
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.in10mServiceMan.utils.localStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile_success.*

class ProfileSuccessActivity : In10mBaseActivity(), ILoginView {

    override fun onLoginCompleted(mResponse: LoginResponse) {
    }

    override fun onFailed(msg: String) {
    }

    override fun onResetLinkSend(mResposne: LinkSendResponse) {
    }

    override fun onCompleteProfileReceived(metaData: CustomerCompleteProfile) {
        if (metaData.status == 1) {
            localStorage(this).saveCompleteCustomer(metaData.data)
            val intent = Intent(baseContext, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    val mPresenter = LoginPresenter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_success)

        val background = object : Thread() {
            override fun run() {
                try {
                    sleep(3000)
                    SharedPreferencesHelper.getString(this@ProfileSuccessActivity, Constants.SharedPrefs.User.USER_ID, "0")
                        ?.let { mPresenter.getCompleteProfile(it) }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        background.start()
        if (!SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.PROFILE_PICTURE, "").isNullOrEmpty()) {
            val url = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.PROFILE_PICTURE, "")
            Picasso.get().load(url).placeholder(R.drawable.dummy_user).fit().into(userAvatarSuccess)
        } else {
            Picasso.get().load(R.drawable.dummy_user).placeholder(R.drawable.dummy_user).fit().into(userAvatarSuccess)
        }
        var mFullName = SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.FIRST_NAME, "") + " " + SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.LAST_NAME, "")
        userNameSuccess.text = mFullName
    }
}
