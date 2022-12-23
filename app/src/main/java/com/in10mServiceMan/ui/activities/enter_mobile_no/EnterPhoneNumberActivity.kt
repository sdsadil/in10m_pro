package com.in10mServiceMan.ui.activities.enter_mobile_no

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.gson.Gson
import com.in10mServiceMan.models.RequestVerifyMobile
import com.in10mServiceMan.models.ResponseVerifyMobile
import com.in10mServiceMan.R

import com.in10mServiceMan.ui.activities.otp_verification.OtpVerificationActivity
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.ui.base.In10mBaseActivity
import kotlinx.android.synthetic.main.activity_your_mobile_no.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnterPhoneNumberActivity : In10mBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_your_mobile_no)

        button_close.setOnClickListener {
            finish()
        }

        lvEnterBtn.setOnClickListener {
            val mobilenumberVal = mobileET.getText(true)
            if (!mobilenumberVal.isNullOrEmpty()) {

                if (!isValidMobile(mobilenumberVal.toString())) {
                    Toast.makeText(this, "Please enter valid mobile number", Toast.LENGTH_SHORT).show()
                } else {

                    showProgressDialog("")
                    val loginAPI = APIClient()

                    loginAPI.clearToken()
                    // call api to verify mobile
                    var requestVerifyMobile = RequestVerifyMobile()
                    requestVerifyMobile.code = "91"
                    requestVerifyMobile.mobile = mobilenumberVal.toString()
                    val callServicProviders = APIClient.getApiInterface().postVerifyMobile(requestVerifyMobile)
                    callServicProviders.enqueue(object : Callback<ResponseVerifyMobile> {
                        override fun onResponse(call: Call<ResponseVerifyMobile>, response: Response<ResponseVerifyMobile>) {
                            destroyDialog()
                            Log.e("Phone number", Gson().toJson(response.body()).toString())
                            if (response.isSuccessful) {
                                try {
                                    val data = response.body()
                                    if (data!!.data.size > 0 && data!!.data[0].customerData.size > 0) {
                                        loginAPI.publicAccessToken = data.data[0].apiToken
                                        val intentVerifyOtp = Intent(this@EnterPhoneNumberActivity, OtpVerificationActivity::class.java)
                                        intentVerifyOtp.putExtra("mobileNo", mobilenumberVal.toString())
                                        intentVerifyOtp.putExtra("otp", data!!.data[0].customerData[0].otp.toString())
                                        intentVerifyOtp.putExtra("registered", data!!.data[0].customerData[0].isRegistered)
                                        //  intentVerifyOtp.putExtra("otp", data!!.data[0].customerData[0].getOtp().toString())
                                        startActivity(intentVerifyOtp)
                                    } else {
                                        somethingWentWrong(data.message)
                                    }
                                } catch (e: Exception) {
                                    Toast.makeText(this@EnterPhoneNumberActivity, e.toString(), Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                somethingWentWrong()
                            }
                        }

                        override fun onFailure(call: Call<ResponseVerifyMobile>, t: Throwable) {
                            somethingWentWrong("Something went wrong.. Please try again")
                            destroyDialog()
                        }
                    })
                }

            } else {
                Toast.makeText(this, "Mobile number is required", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun isValidMobile(phone: String): Boolean {
        return if (phone.length != 10) {
            false;
        } else {
            android.util.Patterns.PHONE.matcher(phone).matches();
        }
    }

    private fun somethingWentWrong(msg: String = "Something went wrong") {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}