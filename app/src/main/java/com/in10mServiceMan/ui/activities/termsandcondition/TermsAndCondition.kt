package com.in10mServiceMan.ui.activities.termsandcondition

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.github.barteksc.pdfviewer.util.FitPolicy
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.accound_edit.policy_and_terms_api.ITermsConditionsView
import com.in10mServiceMan.ui.accound_edit.policy_and_terms_api.PolicyAndTermsResponse
import com.in10mServiceMan.ui.accound_edit.policy_and_terms_api.TermsConditionsPresenter
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.ui.base.In10mBaseActivity
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_privacy_policy.*
import kotlinx.android.synthetic.main.activity_terms_and_condition.*
import kotlinx.android.synthetic.main.activity_terms_and_condition.button_close
import kotlinx.android.synthetic.main.activity_terms_and_condition.pdfView

class TermsAndCondition : In10mBaseActivity(), ITermsConditionsView, OnPageChangeListener,
    OnLoadCompleteListener, OnPageErrorListener {
    var SAMPLE_FILE = "terms_condition_pro.pdf"
    val termsConditionsPresenter = TermsConditionsPresenter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms_and_condition)
        button_close.setOnClickListener {
            finish()
        }
        termsAndConditionWV.settings.javaScriptEnabled = true
        termsAndConditionWV.loadUrl("https://docs.google.com/viewer?embedded=true&url=" + APIClient.termsAndCondition)
        termsAndConditionWV.webViewClient = myWebViewClient()

        if (Constants.GlobalSettings.termsConditions) {
            accept_TC.visibility = View.VISIBLE
        }

        accept_TC.setOnClickListener {
            showProgressDialog("")
            termsConditionsPresenter.termsConditions(
                SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "0")!!
                    .toInt(), 2)
        }

        displayFromAsset(SAMPLE_FILE)
    }

    inner class myWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }

    override fun onTermsConditionsCompleted(mPost: PolicyAndTermsResponse) {
        destroyDialog()
        if (mPost.status == 1) {
            accept_TC.visibility = View.GONE
            Constants.GlobalSettings.termsConditions = false
        }
    }

    override fun onTermsConditionsFailed(msg: String) {
        destroyDialog()
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    private fun displayFromAsset(assetFileName: String) {
        pdfView.fromAsset(assetFileName)
            .defaultPage(0)
            .onPageChange(this)
            .enableAnnotationRendering(true)
            .onLoad(this)
            .scrollHandle(DefaultScrollHandle(this))
            .spacing(10) // in dp
            .onPageError(this)
            .pageFitPolicy(FitPolicy.BOTH)
            .load()
    }

    override fun onPageChanged(page: Int, p1: Int) {
        Log.e("onPageChanged", "onPageChanged $page")
    }

    override fun loadComplete(p0: Int) {
        val meta = pdfView.documentMeta
    }

    override fun onPageError(p0: Int, t: Throwable?) {
        t?.printStackTrace()
    }
}
