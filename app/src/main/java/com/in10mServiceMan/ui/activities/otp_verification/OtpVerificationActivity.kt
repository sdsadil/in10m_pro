package com.in10mServiceMan.ui.activities.otp_verification

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.in10mServiceMan.models.CustomerCompleteProfile
import com.in10mServiceMan.models.RequestVerifyOTP
import com.in10mServiceMan.models.ResponseVerifyMobile
import com.in10mServiceMan.R
import com.in10mServiceMan.ui.activities.BaseActivity
import com.in10mServiceMan.ui.activities.service_man_details.ServiceManDetailsActivity
import com.in10mServiceMan.ui.activities.tracking_map.MapTrackingActivity
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.utils.LoadingDialog
import com.in10mServiceMan.utils.localStorage
import kotlinx.android.synthetic.main.activity_otp_verification.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtpVerificationActivity : BaseActivity() {

    var alreadyRegistered = 0
    internal lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verification)
        val otp = intent.getStringExtra("otp")
        alreadyRegistered = intent.getIntExtra("registered", 0)
        Toast.makeText(this, "OTP is $otp", Toast.LENGTH_LONG).show()
        tvOTP.text = "OTP is $otp"

        loadingDialog = LoadingDialog(this)

        button_close.setOnClickListener {
            finish()
        }
        lvVerifyBtn.setOnClickListener {
            verifyMobile(otpET.text.toString())
        }
        val mobile = intent.getStringExtra("mobileNo")
        //SMS Code been sent to the registered number 32****34
        textView23.text = "SMS Code been sent to the registered number " + replaceLastFour(mobile.toString())
    }

    fun replaceLastFour(s: String): String {
        val length = s.length
        //Check whether or not the string contains at least four characters; if not, this method is useless
        return if (length < 4) "****" else s.substring(0, length - 4) + "****"
    }

    private fun somethingWentWrong(msg: String = "Something went wrong") {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    private fun verifyMobile(otpText: String) {

        if (!otpText.isBlank()) {

            showProgressDialog("")
            val mobile = intent.getStringExtra("mobileNo")

            // Log.i("eee112", mobile)

            val loginAPI = APIClient()

            val requestVerifyMobile = RequestVerifyOTP()
            requestVerifyMobile.code = "91"
            requestVerifyMobile.mobile = mobile.toString();
            requestVerifyMobile.name = ""
            requestVerifyMobile.otp = otpText.toString()

            val callServicProviders = APIClient.getApiInterface().postOtpMobile(requestVerifyMobile)
            callServicProviders.enqueue(object : Callback<ResponseVerifyMobile> {
                override fun onResponse(call: Call<ResponseVerifyMobile>, response: Response<ResponseVerifyMobile>) {

                    destroyDialog()
                    // Log.i("eee11", Gson().toJson(requestVerifyMobile))
                    // Log.i("eee12", Gson().toJson(call.request().url()))
                    if (response.isSuccessful) {
                        val data2 = response.body()
                        if (data2!!.data.size > 0 && data2.data[0].customerData.size > 0) {
                            val customerUser = data2.data[0].customerData[0]
                            val storage = localStorage(this@OtpVerificationActivity)
                            storage.token = data2.data[0].apiToken
                            storage.setUserLogin()
                            storage.saveLoggedInUser(customerUser)
                            loginAPI.publicAccessToken = data2.data[0].apiToken
                            if (alreadyRegistered == 1 && !customerUser.customerName.isBlank()) {
                                loadingDialog.showProgressDialog("")
                                // get complete customer and go to home page
                                loadExistingProfile()
                            } else {
                                startActivity(Intent(this@OtpVerificationActivity, ServiceManDetailsActivity::class.java))
                                finishAffinity()
                            }

                        } else {
                            somethingWentWrong(data2.message)
                        }
                        // Log.i("eeee3", Gson().toJson(response.body()))

                    } else {
                        //  Log.i("eeee1",response.body().toString())
                        somethingWentWrong()
                    }
                }

                override fun onFailure(call: Call<ResponseVerifyMobile>, t: Throwable) {
                    //  Log.i("eeee2","Request Failure")
                    destroyDialog()
                    somethingWentWrong()
                }
            })
        } else {
            Toast.makeText(this, "OTP is required", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadExistingProfile() {
        val user = localStorage(this).loggedInUser
        APIClient.getApiInterface()!!.getCompleteProfile(user.customerId!!).enqueue(object : Callback<CustomerCompleteProfile> {
            override fun onResponse(call: Call<CustomerCompleteProfile>, response: Response<CustomerCompleteProfile>) {
                if (response.isSuccessful) {
                    val profile = response.body()!!.data
                    localStorage(this@OtpVerificationActivity).saveCompleteCustomer(profile)
                    startActivity(Intent(this@OtpVerificationActivity, MapTrackingActivity::class.java))
                    finishAffinity()
                }
            }

            override fun onFailure(call: Call<CustomerCompleteProfile>, t: Throwable) {

            }
        })

    }
}