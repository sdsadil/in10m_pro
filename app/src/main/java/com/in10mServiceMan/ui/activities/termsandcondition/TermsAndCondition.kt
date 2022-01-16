package com.in10mServiceMan.ui.activities.termsandcondition

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.accound_edit.policy_and_terms_api.ITermsConditionsView
import com.in10mServiceMan.ui.accound_edit.policy_and_terms_api.PolicyAndTermsResponse
import com.in10mServiceMan.ui.accound_edit.policy_and_terms_api.TermsConditionsPresenter
import com.in10mServiceMan.ui.apis.LoginAPI
import com.in10mServiceMan.ui.base.In10mBaseActivity
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_terms_and_condition.*

class TermsAndCondition : In10mBaseActivity(), ITermsConditionsView {
    override fun onTermsConditionsCompleted(mPost: PolicyAndTermsResponse) {
        destroyDialog()
        if (mPost.status == 1)  {
            accept_TC.visibility = View.GONE
            Constants.GlobalSettings.termsConditions = false
        }
    }

    override fun onTermsConditionsFailed(msg: String) {
        destroyDialog()
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    val termsConditionsPresenter =  TermsConditionsPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_and_condition)
        button_close.setOnClickListener {
            finish()
        }
        termsAndConditionWV.settings.javaScriptEnabled = true
        termsAndConditionWV.loadUrl("https://docs.google.com/viewer?embedded=true&url=" + LoginAPI.termsAndCondition)
        termsAndConditionWV.webViewClient = myWebViewClient()

        if (Constants.GlobalSettings.termsConditions) {
            accept_TC.visibility = View.VISIBLE
        }

        accept_TC.setOnClickListener {
            showProgressDialog("")
            termsConditionsPresenter.termsConditions(
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
