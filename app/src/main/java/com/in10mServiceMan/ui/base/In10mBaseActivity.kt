package com.in10mServiceMan.ui.base


import android.annotation.TargetApi
import android.os.Bundle
import com.in10mServiceMan.ui.activities.BaseActivity

import com.in10mServiceMan.ui.apis.APIClient

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater.from
import android.widget.TextView
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.sign_in.LoginActivity
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.in10mServiceMan.utils.localStorage
import kotlinx.android.synthetic.main.custom_session_expire_layout.*
import java.util.*


open class In10mBaseActivity : BaseActivity(), IBaseView {
    var mBasePresenter: BasePresenter? = null
    override fun onSessionExpired() {
        if (!isFinishing || !!isDestroyed) {
            try {
                sessionOutCallLogin()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onSessionValid() {

    }

    override fun onResume() {
        super.onResume()
        if (!APIClient().publicAccessToken.isNullOrEmpty()) {
            val userId =
                SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.USER_ID, "")
            val header =
                SharedPreferencesHelper.getString(this, Constants.SharedPrefs.User.AUTH_TOKEN, "")

//            mBasePresenter?.checkSession(header.toString(), userId.toString())
        }
        setLangFunc1()
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

    fun setLangFunc1() {
        val isLangArabic = SharedPreferencesHelper.getBoolean(
            this,
            Constants.SharedPrefs.User.IS_LANG_ARB, false
        )!!
        if (isLangArabic) {
            setLanguage1(this, "ar")
        } else {
            setLanguage1(this, "en")
        }
    }

    fun setLanguage1(context: Context, languageCode: String?) {
        val localeNew = Locale(languageCode!!)
        Locale.setDefault(localeNew)
        val res = context.resources
        val newConfig = Configuration(res.configuration)
        newConfig.locale = localeNew
        newConfig.setLayoutDirection(localeNew)
        res.updateConfiguration(newConfig, res.displayMetrics)
        newConfig.setLocale(localeNew)
        context.createConfigurationContext(newConfig)

        /*val locale = Locale(languageCode!!)
        Locale.setDefault(locale)
        val config = Configuration()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            setSystemLocale(config, locale)
        } else {
            setSystemLocaleLegacy(config, locale)
        }*/
    }

    fun getSystemLocaleLegacy(config: Configuration): Locale? {
        return config.locale
    }

    @TargetApi(Build.VERSION_CODES.N)
    fun getSystemLocale(config: Configuration): Locale? {
        return config.locales[0]
    }

    fun setSystemLocaleLegacy(config: Configuration, locale: Locale?) {
        config.locale = locale
    }

    @TargetApi(Build.VERSION_CODES.N)
    fun setSystemLocale(config: Configuration, locale: Locale?) {
        config.setLocale(locale)
    }
}