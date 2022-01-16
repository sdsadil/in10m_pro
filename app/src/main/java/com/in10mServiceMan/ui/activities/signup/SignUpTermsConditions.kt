package com.in10mServiceMan.ui.activities.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.BaseActivity
import com.in10mServiceMan.utils.Constants
import kotlinx.android.synthetic.main.activity_sign_up_terms_conditions.*

class SignUpTermsConditions : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_terms_conditions)

        button_close.setOnClickListener {
            finish()
        }

        acceptButton.setOnClickListener {
            Constants.GlobalSettings.signupTerms = true
            finish()
        }
    }
}
