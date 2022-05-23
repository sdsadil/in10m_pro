package com.in10mServiceMan.ui.activities.about

import com.in10mServiceMan.ui.base.In10mBaseActivity
import android.os.Bundle

import android.webkit.WebView
import android.webkit.WebViewClient
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.apis.APIClient
import kotlinx.android.synthetic.main.activity_about.*
//TEST
class AboutActivity : In10mBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        button_close.setOnClickListener {
            finish()
        }
        aboutAppWV.settings.javaScriptEnabled = true
        aboutAppWV.loadUrl(APIClient.aboutUs)
        aboutAppWV.webViewClient = myWebViewClient()
    }
    inner class myWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }
}
