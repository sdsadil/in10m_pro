package com.in10mServiceMan.ui.activities.privacy_policy

import android.annotation.SuppressLint
import com.in10mServiceMan.ui.base.In10mBaseActivity
import android.os.Bundle
import android.view.View
import com.in10mServiceMan.R
import kotlinx.android.synthetic.main.activity_privacy_policy.*
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.in10mServiceMan.ui.accound_edit.policy_and_terms_api.IPrivacyPolicyView
import com.in10mServiceMan.ui.accound_edit.policy_and_terms_api.PolicyAndTermsResponse
import com.in10mServiceMan.ui.accound_edit.policy_and_terms_api.PrivacyPolicyPresenter
import com.in10mServiceMan.ui.apis.LoginAPI
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper

class PrivacyPolicyActivity : In10mBaseActivity(), IPrivacyPolicyView {
    override fun onPrivacyPolicyCompleted(mPost: PolicyAndTermsResponse) {
        destroyDialog()
        if (mPost.status == 1)  {
            accept_PP.visibility = View.GONE
            Constants.GlobalSettings.privacyPolicy = false
        }
    }

    override fun onPrivacyPolicyFailed(msg: String) {
        destroyDialog()
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    val privacyPolicyPresenter = PrivacyPolicyPresenter(this)

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)
        button_close.setOnClickListener {
            finish()
        }
        privacyPolicyWV.settings.javaScriptEnabled = true
        privacyPolicyWV.loadUrl("https://docs.google.com/viewer?embedded=true&url=" + LoginAPI.privacyPolicy)
        privacyPolicyWV.webViewClient = myWebViewClient()

        if (Constants.GlobalSettings.privacyPolicy) {
            accept_PP.visibility = View.VISIBLE
        }

        accept_PP.setOnClickListener {
            showProgressDialog("")
            privacyPolicyPresenter.privacyPolicy(
                SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "0")!!.toInt(), 2)
        }
    }

    inner class myWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }
}
