package com.in10mServiceMan.ui.activities.about

import com.in10mServiceMan.ui.base.In10mBaseActivity
import android.os.Bundle

import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.apis.LoginAPI
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.app_bar_transparent.*

class AboutActivity : In10mBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        button_close.setOnClickListener {
            finish()
        }
        aboutAppWV.settings.javaScriptEnabled = true
        aboutAppWV.loadUrl(LoginAPI.aboutUs)
        aboutAppWV.webViewClient = myWebViewClient()
    }
    inner class myWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }
}
