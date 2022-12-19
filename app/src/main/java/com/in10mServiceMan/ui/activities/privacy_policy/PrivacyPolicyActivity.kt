package com.in10mServiceMan.ui.activities.privacy_policy

import android.annotation.SuppressLint
import com.in10mServiceMan.ui.base.In10mBaseActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.in10mServiceMan.R
import kotlinx.android.synthetic.main.activity_privacy_policy.*
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.github.barteksc.pdfviewer.util.FitPolicy
import com.in10mServiceMan.ui.accound_edit.policy_and_terms_api.IPrivacyPolicyView
import com.in10mServiceMan.ui.accound_edit.policy_and_terms_api.PolicyAndTermsResponse
import com.in10mServiceMan.ui.accound_edit.policy_and_terms_api.PrivacyPolicyPresenter
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper

class PrivacyPolicyActivity : In10mBaseActivity(), IPrivacyPolicyView, OnPageChangeListener,
    OnLoadCompleteListener, OnPageErrorListener {

    val privacyPolicyPresenter = PrivacyPolicyPresenter(this)
    var SAMPLE_FILE = "privacypolicy_pro.pdf"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)
        button_close.setOnClickListener {
            onBackPressed()
        }
        privacyPolicyWV.settings.javaScriptEnabled = true
        privacyPolicyWV.loadUrl("https://docs.google.com/viewer?embedded=true&url=" + APIClient.privacyPolicy)
        privacyPolicyWV.webViewClient = myWebViewClient()

        if (Constants.GlobalSettings.privacyPolicy) {
            accept_PP.visibility = View.VISIBLE
        }

        accept_PP.setOnClickListener {
            showProgressDialog("")
            privacyPolicyPresenter.privacyPolicy(
                SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "0")!!
                    .toInt(), 2
            )
        }
        displayFromAsset(SAMPLE_FILE)
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

    inner class myWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }

    override fun onPrivacyPolicyCompleted(mPost: PolicyAndTermsResponse) {
        destroyDialog()
        if (mPost.status == 1) {
            accept_PP.visibility = View.GONE
            Constants.GlobalSettings.privacyPolicy = false
        }
    }

    override fun onPrivacyPolicyFailed(msg: String) {
        destroyDialog()
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
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
