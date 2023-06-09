package com.in10mServiceMan.ui.activities.sign_in

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.in10mServiceMan.R
import com.in10mServiceMan.models.CustomerCompleteProfile
import com.in10mServiceMan.ui.activities.company_registration.CompanySignupActivity
import com.in10mServiceMan.ui.activities.dashboard.DashboardActivity
import com.in10mServiceMan.ui.activities.services.AvailableServices
import com.in10mServiceMan.ui.activities.signup.SignUpActivity
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.ui.base.In10mBaseActivity
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.in10mServiceMan.utils.localStorage
import kotlinx.android.synthetic.main.activity_company_pros_details.*
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : In10mBaseActivity(), ILoginView {

    private var mRegistrationStep: Int = 0
    private var user: Int = 1
    private var companyId: String? = null
    private val mPresenter = LoginPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        registerHereTV.text = Html.fromHtml(resources.getString(R.string.register_here_html))
        forgotPassTV.setOnClickListener {
            displayAlertDialog()
        }
        registerHereTV.setOnClickListener {
//            startActivity(Intent(this@LoginActivity, AccountType::class.java))

            SharedPreferencesHelper.putString(this, Constants.SharedPrefs.User.ACCOUNT_TYPE, "1")
            SharedPreferencesHelper.putInt(this, Constants.SharedPrefs.User.PERSON_TYPE, 2)
            startActivity(Intent(this, AvailableServices::class.java))
            overridePendingTransition(0, 0)
        }
        tvChooseLang_LoginLay.setOnClickListener {
            languageChangeDialogView()
        }
        loginClickLL.setOnClickListener {
            if (!isValidEmail(usernameLoginET.text!!)) {
                ShowToast(resources.getString(R.string.enter_your_username))
            } else if (passwordLoginET.text!!.isEmpty()) {
                ShowToast(resources.getString(R.string.enter_your_password))
            } else {
                showProgressDialog("")
                mPresenter.userLogin(
                    usernameLoginET.text.toString(),
                    passwordLoginET.text.toString()
                )
            }
        }

        ivTogglePswd_LoginLay.setOnClickListener {
            if (passwordLoginET.transformationMethod
                    .equals(PasswordTransformationMethod.getInstance())
            ) {
                Glide.with(this).load(R.drawable.ic_password_hide).into(ivTogglePswd_LoginLay)
                passwordLoginET.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            } else {
                Glide.with(this).load(R.drawable.ic_password_show).into(ivTogglePswd_LoginLay)
                passwordLoginET.transformationMethod =
                    PasswordTransformationMethod.getInstance()

            }
        }
    }

    private fun displayAlertDialog() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_layout_reset, null)
        val mBuilder = AlertDialog.Builder(this).setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mAlertDialog.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        val email = mDialogView.findViewById(R.id.resetEmailET) as AppCompatEditText
        val button = mDialogView.findViewById(R.id.resetClickLL) as LinearLayout
        button.setOnClickListener {

            if (isValidEmail(email.text.toString())) {
                mPresenter.sendResetLink(email.text.toString())
            } else {
                showToast(resources.getString(R.string.enter_valid_email_address))
            }
            mAlertDialog.cancel()
        }
    }

    private fun isValidEmail(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onCompleteProfileReceived(metaData: CustomerCompleteProfile) {
        destroyDialog()
        if (metaData.status == 1) {
            if (user == 2 && companyId != null) {
                localStorage(this).saveCompleteCustomer(metaData.data)
                when (mRegistrationStep) {
                    1 -> {
                        startActivity(
                            Intent(
                                this@LoginActivity,
                                CompanySignupActivity::class.java
                            ).putExtra("step", "1")
                        )
                        overridePendingTransition(0, 0)
                    }
                    2 -> {
                        startActivity(
                            Intent(
                                this@LoginActivity,
                                CompanySignupActivity::class.java
                            ).putExtra("step", "2")
                        )
                        overridePendingTransition(0, 0)
                    }
                    3 -> {
                        startActivity(
                            Intent(
                                this@LoginActivity,
                                CompanySignupActivity::class.java
                            ).putExtra("step", "3")
                        )
                        overridePendingTransition(0, 0)
                    }
                    else -> {
                        localStorage(this).saveCompleteCustomer(metaData.data)
                        startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                        overridePendingTransition(0, 0)
                    }
                }
            } else if (user == 3) {
                localStorage(this).saveCompleteCustomer(metaData.data)
                when (mRegistrationStep) {
                    1 -> {
                        startActivity(
                            Intent(
                                this@LoginActivity,
                                CompanySignupActivity::class.java
                            ).putExtra("step", "1")
                        )
                        overridePendingTransition(0, 0)
                    }
                    2 -> {
                        startActivity(
                            Intent(
                                this@LoginActivity,
                                CompanySignupActivity::class.java
                            ).putExtra("step", "2")
                        )
                        overridePendingTransition(0, 0)
                    }
                    3 -> {
                        startActivity(
                            Intent(
                                this@LoginActivity,
                                CompanySignupActivity::class.java
                            ).putExtra("step", "3")
                        )
                        overridePendingTransition(0, 0)
                    }
                    else -> {
                        localStorage(this).saveCompleteCustomer(metaData.data)
                        startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                        overridePendingTransition(0, 0)
                    }
                }
            } else {
                when (mRegistrationStep) {
                    1 -> {
                        startActivity(
                            Intent(
                                this@LoginActivity,
                                SignUpActivity::class.java
                            ).putExtra("step", "1")
                        )
                        overridePendingTransition(0, 0)
                    }
                    2 -> {
                        startActivity(
                            Intent(
                                this@LoginActivity,
                                SignUpActivity::class.java
                            ).putExtra("step", "2")
                        )
                        overridePendingTransition(0, 0)
                    }
                    3 -> {
                        localStorage(this).saveCompleteCustomer(metaData.data)
                        startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                        overridePendingTransition(0, 0)
                        //startActivity(Intent(this@LoginActivity, SignUpActivity::class.java).putExtra("step", "3"))
                    }
                    else -> {
                        localStorage(this).saveCompleteCustomer(metaData.data)
                        startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                        overridePendingTransition(0, 0)
                    }
                }
            }
        } else {
            showToast(metaData.message)
        }
    }

    override fun onLoginCompleted(mResponse: LoginResponse) {
        destroyDialog()
        if (mResponse.status == 1) {

            val gson = Gson()
            val responseString = gson.toJson(mResponse.data)

            APIClient().publicAccessToken = mResponse.data!![0]?.apiToken!!.toString()

            SharedPreferencesHelper.putString(
                this,
                Constants.SharedPrefs.User.USER_ID,
                mResponse.data[0]?.customerData!![0]?.servicePersonId.toString()
            )
            SharedPreferencesHelper.putString(
                this,
                Constants.SharedPrefs.User.AUTH_TOKEN,
                mResponse.data[0]?.apiToken!!
            )
            SharedPreferencesHelper.putString(
                this,
                Constants.SharedPrefs.User.EMAIL,
                mResponse.data[0]?.customerData!![0]?.servicePersonEmail!!
            )
            try {
                if (mResponse.data[0]?.customerData!![0]?.servicePersonCompany != null)
                    SharedPreferencesHelper.putInt(
                        this,
                        Constants.SharedPrefs.User.PERSON_COMPANY_NAME,
                        mResponse?.data!![0]?.customerData!![0]?.servicePersonCompany!!
                    )
                if (mResponse?.data!![0]?.customerData!![0]?.servicePersonType != null)
                    SharedPreferencesHelper.putInt(
                        this,
                        Constants.SharedPrefs.User.PERSON_TYPE,
                        mResponse?.data!![0]?.customerData!![0]?.servicePersonType!!
                    )
                if (mResponse?.data!![0]?.customerData!![0]?.servicePersonAddress1 != null) {
                    SharedPreferencesHelper.putString(
                        this,
                        Constants.SharedPrefs.User.SM_ADDRESS_ONE,
                        mResponse?.data!![0]?.customerData!![0]?.servicePersonAddress1!!
                    )
                    Constants.GlobalSettings.streetName =
                        mResponse?.data!![0]?.customerData!![0]?.servicePersonAddress1!!
                }
                if (mResponse?.data!![0]?.customerData!![0]?.servicePersonAddress2 != null) {
                    SharedPreferencesHelper.putString(
                        this,
                        Constants.SharedPrefs.User.SM_ADDRESS_TWO,
                        mResponse?.data!![0]?.customerData!![0]?.servicePersonAddress2!!
                    )
                    Constants.GlobalSettings.aptNo =
                        mResponse?.data!![0]?.customerData!![0]?.servicePersonAddress2!!
                }
                if (mResponse?.data!![0]?.customerData!![0]?.servicePersonStreetName != null) {
                    SharedPreferencesHelper.putString(
                        this,
                        Constants.SharedPrefs.User.SM_STREET_NAME,
                        mResponse?.data!![0]?.customerData!![0]?.servicePersonStreetName!!
                    )
                }
                if (mResponse?.data!![0]?.customerData!![0]?.servicePersonCity != null) {
                    SharedPreferencesHelper.putString(
                        this,
                        Constants.SharedPrefs.User.SM_CITY,
                        mResponse?.data!![0]?.customerData!![0]?.servicePersonCity!!
                    )
                    Constants.GlobalSettings.cityName =
                        mResponse?.data!![0]?.customerData!![0]?.servicePersonCity!!
                }
                if (mResponse?.data!![0]?.customerData!![0]?.servicePersonState != null) {
                    SharedPreferencesHelper.putString(
                        this,
                        Constants.SharedPrefs.User.SM_STATE,
                        mResponse?.data!![0]?.customerData!![0]?.servicePersonState!!
                    )
                    Constants.GlobalSettings.stateName =
                        mResponse?.data!![0]?.customerData!![0]?.servicePersonState!!
                }
                if (mResponse?.data!![0]?.customerData!![0]?.servicePersonZipcode != null) {
                    SharedPreferencesHelper.putString(
                        this,
                        Constants.SharedPrefs.User.SM_ZIP_CODE,
                        mResponse?.data!![0]?.customerData!![0]?.servicePersonZipcode!!
                    )
                    Constants.GlobalSettings.zipCode =
                        mResponse?.data!![0]?.customerData!![0]?.servicePersonZipcode!!
                }
                if (mResponse?.data!![0]?.customerData!![0]?.servicePersonPaymntType != null) {
                    SharedPreferencesHelper.putInt(
                        this,
                        Constants.SharedPrefs.User.SM_PAYMENT_TYPE,
                        mResponse?.data!![0]?.customerData!![0]?.servicePersonPaymntType!!
                    )
                    Constants.GlobalSettings.paymentType =
                        mResponse?.data!![0]?.customerData!![0]?.servicePersonPaymntType!!
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            SharedPreferencesHelper.putString(
                this,
                Constants.SharedPrefs.User.COMPLETE_LOGIN_DETAILS,
                responseString
            )

            mRegistrationStep = mResponse?.data!![0]?.customerData!![0]?.registrationStep!!
            user = mResponse?.data!![0]?.customerData!![0]?.servicePersonType!!
            if (mResponse?.data!![0]?.customerData!![0]?.servicePersonCompany == null) {
                companyId = null
            } else {
                companyId =
                    mResponse?.data!![0]?.customerData!![0]?.servicePersonCompany!!.toString()
            }
            // startActivity(Intent(this, CompanyResourceActivity::class.java))
            showProgressDialog("")
            mPresenter.getCompleteProfile(mResponse?.data!![0]?.customerData!![0]?.servicePersonId.toString())
        } else {
            ShowToast(mResponse.message)
        }
    }

    override fun onFailed(msg: String) {
        destroyDialog()
        showToast(msg)
    }

    override fun onResetLinkSend(mResposne: LinkSendResponse) {
        if (mResposne.status == 1) {
            showToast(mResposne.message!!)
        } else {
            showToast(resources.getString(R.string.something_went_wrong))
        }
    }
}
