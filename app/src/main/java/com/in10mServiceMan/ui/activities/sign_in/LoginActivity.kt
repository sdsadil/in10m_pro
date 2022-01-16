package com.in10mServiceMan.ui.activities.sign_in

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.google.android.material.textfield.TextInputEditText
import androidx.appcompat.app.AlertDialog
import android.text.Html
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_login.*
import android.util.Patterns
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.google.gson.Gson
import com.in10mServiceMan.Models.CustomerCompleteProfile
import com.in10mServiceMan.ui.activities.BaseActivity
import com.in10mServiceMan.ui.activities.company_registration.CompanyResourceActivity
import com.in10mServiceMan.ui.activities.company_registration.CompanySignupActivity
import com.in10mServiceMan.ui.activities.signup.AccountType
import com.in10mServiceMan.ui.activities.tracking_map.MapTrackingActivity
import com.in10mServiceMan.ui.apis.LoginAPI
import com.in10mServiceMan.ui.base.In10mBaseActivity
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.in10mServiceMan.utils.localStorage


class LoginActivity : BaseActivity(), ILoginView {
    override fun onCompleteProfileReceived(metaData: CustomerCompleteProfile) {
        Log.e("Cmplt Profile Response",Gson().toJson(metaData))
        destroyDialog()
        if (metaData.status == 1) {
            if (user == 2 && companyId != null) {
                localStorage(this).saveCompleteCustomer(metaData.data)
                if (mRegistrationStep == 1) {
                    startActivity(Intent(this@LoginActivity, CompanySignupActivity::class.java).putExtra("step", "1"))
                } else if (mRegistrationStep == 2) {
                    startActivity(Intent(this@LoginActivity, CompanySignupActivity::class.java).putExtra("step", "2"))
                } else if (mRegistrationStep == 3) {
                    startActivity(Intent(this@LoginActivity, CompanySignupActivity::class.java).putExtra("step", "3"))
                } else {
                    localStorage(this).saveCompleteCustomer(metaData.data)
                    startActivity(Intent(this@LoginActivity, MapTrackingActivity::class.java))
                }
            } else if (user == 3) {
                localStorage(this).saveCompleteCustomer(metaData.data)
                if (mRegistrationStep == 1) {
                    startActivity(Intent(this@LoginActivity, CompanySignupActivity::class.java).putExtra("step", "1"))
                } else if (mRegistrationStep == 2) {
                    startActivity(Intent(this@LoginActivity, CompanySignupActivity::class.java).putExtra("step", "2"))
                } else if (mRegistrationStep == 3) {
                    startActivity(Intent(this@LoginActivity, CompanySignupActivity::class.java).putExtra("step", "3"))
                } else {
                    localStorage(this).saveCompleteCustomer(metaData.data)
                    startActivity(Intent(this@LoginActivity, MapTrackingActivity::class.java))
                }
            } else {
                if (mRegistrationStep == 1) {
                    startActivity(Intent(this@LoginActivity, SignUpActivity::class.java).putExtra("step", "1"))
                } else if (mRegistrationStep == 2) {
                    startActivity(Intent(this@LoginActivity, SignUpActivity::class.java).putExtra("step", "2"))
                } else if (mRegistrationStep == 3) {
                    localStorage(this).saveCompleteCustomer(metaData.data)
                    startActivity(Intent(this@LoginActivity, MapTrackingActivity::class.java))
                    //startActivity(Intent(this@LoginActivity, SignUpActivity::class.java).putExtra("step", "3"))
                } else {
                    localStorage(this).saveCompleteCustomer(metaData.data)
                    startActivity(Intent(this@LoginActivity, MapTrackingActivity::class.java))
                }
            }
        } else {
            showToast(metaData.message)
        }
    }

    override fun onLoginCompleted(mResponse: LoginResponse) {
        Log.e("Login Response",Gson().toJson(mResponse))
        destroyDialog()
        if (mResponse.status == 1) {

            val gson = Gson()
            val responseString = gson.toJson(mResponse?.data)

            Log.d("Login Response data", responseString)
            Log.d("Registration step", mResponse?.data!![0]?.customerData!![0]?.registrationStep.toString())

            LoginAPI().publicAccessToken = mResponse?.data!![0]?.apiToken!!.toString()

            SharedPreferencesHelper.putString(this, Constants.SharedPrefs.User.USER_ID, mResponse?.data!![0]?.customerData!![0]?.servicePersonId.toString())
            SharedPreferencesHelper.putString(this, Constants.SharedPrefs.User.AUTH_TOKEN, mResponse?.data!![0]?.apiToken!!)
            SharedPreferencesHelper.putString(this, Constants.SharedPrefs.User.EMAIL, mResponse?.data!![0]?.customerData!![0]?.servicePersonEmail!!)
            try {
                if (mResponse?.data!![0]?.customerData!![0]?.servicePersonCompany != null)
                    SharedPreferencesHelper.putInt(this, Constants.SharedPrefs.User.PERSON_COMPANY_NAME, mResponse?.data!![0]?.customerData!![0]?.servicePersonCompany!!)
                if (mResponse?.data!![0]?.customerData!![0]?.servicePersonType != null)
                    SharedPreferencesHelper.putInt(this, Constants.SharedPrefs.User.PERSON_TYPE, mResponse?.data!![0]?.customerData!![0]?.servicePersonType!!)
                if (mResponse?.data!![0]?.customerData!![0]?.servicePersonAddress1 != null) {
                    SharedPreferencesHelper.putString(this, Constants.SharedPrefs.User.SM_ADDRESS_ONE, mResponse?.data!![0]?.customerData!![0]?.servicePersonAddress1!!)
                    Constants.GlobalSettings.streetName=mResponse?.data!![0]?.customerData!![0]?.servicePersonAddress1!!
                }
                if (mResponse?.data!![0]?.customerData!![0]?.servicePersonAddress2 != null) {
                    SharedPreferencesHelper.putString(this, Constants.SharedPrefs.User.SM_ADDRESS_TWO, mResponse?.data!![0]?.customerData!![0]?.servicePersonAddress2!!)
                    Constants.GlobalSettings.aptNo=mResponse?.data!![0]?.customerData!![0]?.servicePersonAddress2!!
                }
                if (mResponse?.data!![0]?.customerData!![0]?.servicePersonStreetName != null) {
                    SharedPreferencesHelper.putString(this, Constants.SharedPrefs.User.SM_STREET_NAME, mResponse?.data!![0]?.customerData!![0]?.servicePersonStreetName!!)
                }
                if (mResponse?.data!![0]?.customerData!![0]?.servicePersonCity != null) {
                    SharedPreferencesHelper.putString(this, Constants.SharedPrefs.User.SM_CITY, mResponse?.data!![0]?.customerData!![0]?.servicePersonCity!!)
                    Constants.GlobalSettings.cityName=mResponse?.data!![0]?.customerData!![0]?.servicePersonCity!!
                }
                if (mResponse?.data!![0]?.customerData!![0]?.servicePersonState != null) {
                    SharedPreferencesHelper.putString(this, Constants.SharedPrefs.User.SM_STATE, mResponse?.data!![0]?.customerData!![0]?.servicePersonState!!)
                    Constants.GlobalSettings.stateName=mResponse?.data!![0]?.customerData!![0]?.servicePersonState!!
                }
                if (mResponse?.data!![0]?.customerData!![0]?.servicePersonZipcode != null) {
                    SharedPreferencesHelper.putString(this, Constants.SharedPrefs.User.SM_ZIP_CODE, mResponse?.data!![0]?.customerData!![0]?.servicePersonZipcode!!)
                    Constants.GlobalSettings.zipCode=mResponse?.data!![0]?.customerData!![0]?.servicePersonZipcode!!
                }
                if (mResponse?.data!![0]?.customerData!![0]?.servicePersonPaymntType != null) {
                    SharedPreferencesHelper.putInt(this, Constants.SharedPrefs.User.SM_PAYMENT_TYPE, mResponse?.data!![0]?.customerData!![0]?.servicePersonPaymntType!!)
                    Constants.GlobalSettings.paymentType=mResponse?.data!![0]?.customerData!![0]?.servicePersonPaymntType!!
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            SharedPreferencesHelper.putString(this, Constants.SharedPrefs.User.COMPLETE_LOGIN_DETAILS, responseString)

            mRegistrationStep = mResponse?.data!![0]?.customerData!![0]?.registrationStep!!
            user = mResponse?.data!![0]?.customerData!![0]?.servicePersonType!!
            if (mResponse?.data!![0]?.customerData!![0]?.servicePersonCompany == null) {
                companyId = null
            } else {
                companyId = mResponse?.data!![0]?.customerData!![0]?.servicePersonCompany!!.toString()
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
        if (mResposne?.status == 1) {
            showToast(mResposne?.message!!)
        } else {
            showToast("Something went wrong!!")
        }
    }

    private var mRegistrationStep: Int = 0
    private var user: Int = 1
    private var companyId: String? = null
    private val mPresenter = LoginPresenter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        registerHereTV.text = Html.fromHtml("<u>Register here</u>")
        forgotPassTV.setOnClickListener {
            displayAlertDialog()
        }
        registerHereTV.setOnClickListener {
            startActivity(Intent(this@LoginActivity, AccountType::class.java))
        }
        loginClickLL.setOnClickListener {
            if (!isValidEmail(usernameLoginET.text!!)) {
                ShowToast("Enter your username")
            } else if (passwordLoginET.text!!.isEmpty()) {
                ShowToast("Enter your password")
            } else {
                showProgressDialog("")
                mPresenter.userLogin(usernameLoginET.text.toString(), passwordLoginET.text.toString())
            }
        }
    }

    private fun displayAlertDialog() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.custom_layout_reset, null)
        val mBuilder = AlertDialog.Builder(this).setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mAlertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mAlertDialog.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        val email = mDialogView.findViewById(R.id.resetEmailET) as TextInputEditText
        val button = mDialogView.findViewById(R.id.resetClickLL) as LinearLayout
        button.setOnClickListener {

            if (isValidEmail(email.text.toString())) {
                mPresenter.sendResetLink(email.text.toString())
            } else {
                showToast("Enter valid Email address")
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

}
