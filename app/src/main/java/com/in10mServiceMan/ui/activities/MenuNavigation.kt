package com.in10mServiceMan.ui.activities

import android.R
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.gson.Gson
import com.in10mServiceMan.models.*
import com.in10mServiceMan.ui.apis.APIClient
import com.in10mServiceMan.utils.localStorage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.in10mServiceMan.R.id.*
import com.in10mServiceMan.ui.accound_edit.MyAccountEdit
import com.in10mServiceMan.ui.activities.rating.ReviewsActivity
import com.in10mServiceMan.utils.Constants
import com.in10mServiceMan.utils.SharedPreferencesHelper
import com.squareup.picasso.Picasso

class MenuNavigation(val context: Context, val handler: Handler) {

    var customerUser: CustomerUser = CustomerUser()

    fun loadUserDetails(navigationView: View, serviceManNameTOP: TextView) {
        /*val isLoggedIn = localStorage(context).isLoggedIn*/
        var isLoggedIn = false //localStorage(context).isLoggedIn
        isLoggedIn = !SharedPreferencesHelper.getString(this.context, Constants.SharedPrefs.User.AUTH_TOKEN, "").isNullOrEmpty()

        if (isLoggedIn) {
            val userId = SharedPreferencesHelper.getString(this.context, Constants.SharedPrefs.User.USER_ID, "0")!!
                .toInt()//localStorage(context).loggedInUser.customerId

            val callServicProviders = APIClient.getApiInterface().getCompleteProfile(userId)
            callServicProviders.enqueue(object : Callback<CustomerCompleteProfile> {
                override fun onResponse(call: Call<CustomerCompleteProfile>, response: Response<CustomerCompleteProfile>) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data!!.data != null) {
                            val profile = data.data
                            localStorage(context).saveCompleteCustomer(profile)
                            navigationView.findViewById<TextView>(userNameTVM).text = profile.name.toString()
                            navigationView.findViewById<TextView>(userNameTVM).text = profile.name.toString()
                            serviceManNameTOP.text = "Hello " + profile.name.toString()
                            navigationView.findViewById<TextView>(thumbsUpTVM).text = profile.totalTumbsUp.toString()
                            val asd = navigationView.findViewById<de.hdodenhof.circleimageview.CircleImageView>(serviceManIV)
                            /*Picasso.with(context).load(profile.image).placeholder(com.in10mServiceMan.R.drawable.dummy_user).fit().into(asd)*/
                            Picasso.get()
                                    .load(profile.image) // web image url
                                    .fit().centerInside()
                                    .rotate(0f)                    //if you want to rotate by 90 degrees give 90f
                                    .error(com.in10mServiceMan.R.drawable.dummy_user)
                                    .placeholder(com.in10mServiceMan.R.drawable.dummy_user)
                                    .into(asd)
                            navigationView.findViewById<TextView>(thumbsDownTVM).text = profile.totalTumbsDown.toString()
                            navigationView.findViewById<TextView>(thumbsUpTVM).setOnClickListener {
                                handler.postDelayed({
                                    val myIntent = Intent(context, ReviewsActivity::class.java)
                                    context.startActivity(myIntent)
                                }, 180L)
                            }
                        }

                    } else {
                        Toast.makeText(context, "Not Loaded Complete Profile", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<CustomerCompleteProfile>, t: Throwable) {

                }
            })
        }
    }


    private fun OpenAccount() {
        handler.postDelayed({
            val myIntent = Intent(context, MyAccountEdit::class.java)
            context.startActivity(myIntent)
        }, 180L)
    }

    fun OpenMyAccount() {
        var isLoggedIn = false //localStorage(context).isLoggedIn
        isLoggedIn = !SharedPreferencesHelper.getString(this.context, Constants.SharedPrefs.User.AUTH_TOKEN, "").isNullOrEmpty()

        if (isLoggedIn) {
            OpenAccount()
        } else {
            // present login popup
            presentSignInPopup()
        }
    }

    private fun presentSignInPopup() {
        val dialog = Dialog(context, R.style.Theme_Translucent_NoTitleBar)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(com.in10mServiceMan.R.layout.popup_sign_in)


        val window = dialog.window
        val wlp = window!!.attributes

        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
        window.attributes = wlp
        dialog.window?.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT)


        val btnClose = dialog.findViewById<ImageView>(closeBtn)


        val mobileNumber = dialog.findViewById<EditText>(mobileET)
        val btnEnter = dialog.findViewById<LinearLayout>(lvEnterBtn)
        val btnEnterr = dialog.findViewById<Button>(EnterBtn)


        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        btnEnter.setOnClickListener {
            proceedToOTP(dialog, mobileNumber)
        }
        btnEnterr.setOnClickListener {
            proceedToOTP(dialog, mobileNumber)
        }
        dialog.show()
    }

    private fun proceedToOTP(dialog: Dialog, mobileNumber: EditText) {
        val mobilenumberVal = mobileNumber.text
        if (!mobilenumberVal.isNullOrEmpty()) {

            val loginAPI = APIClient()

            loginAPI.clearToken()

            // call api to verify mobile
            var requestVerifyMobile = RequestVerifyMobile()
            requestVerifyMobile.code = "91"
            requestVerifyMobile.mobile = mobilenumberVal.toString();
            val callServicProviders = APIClient.getApiInterface().postVerifyMobile(requestVerifyMobile)
            callServicProviders.enqueue(object : Callback<ResponseVerifyMobile> {
                override fun onResponse(call: Call<ResponseVerifyMobile>, response: Response<ResponseVerifyMobile>) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data!!.data.size > 0 && data!!.data[0].customerData.size > 0) {
                            dialog.dismiss()
                            // proceed to otp dialogue
                            Toast.makeText(context, data!!.message + " \n otp is : " + data!!.data[0].customerData[0].otp, Toast.LENGTH_LONG).show()
                            presentOtp(data)
                        } else {
                            somethingWentWrong(data.message)
                        }

                    } else {
                        dialog.dismiss()
                        somethingWentWrong()
                    }
                }

                override fun onFailure(call: Call<ResponseVerifyMobile>, t: Throwable) {
                    somethingWentWrong("Something went wrong.. Please try again")
                    presentSignInPopup()
                }
            })
        } else {
            Toast.makeText(context, "Mobile number is required", Toast.LENGTH_SHORT).show()
        }
    }

    private fun somethingWentWrong(msg: String = "Something went wrong") {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    private fun presentOtp(data: ResponseVerifyMobile) {
        val dialog = Dialog(context, R.style.Theme_Translucent_NoTitleBar)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(com.in10mServiceMan.R.layout.popup_otp)


        val window = dialog.window
        val wlp = window!!.attributes

        wlp.gravity = Gravity.CENTER
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_BLUR_BEHIND.inv()
        window.attributes = wlp
        dialog.window!!.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT)


        val btnClose = dialog.findViewById<ImageView>(closeBtn)


        val otpET = dialog.findViewById<EditText>(otpET)
        val btnVerify = dialog.findViewById<LinearLayout>(lvVerifyBtn)
        val btnVerify2 = dialog.findViewById<Button>(VerifyBtn)


        btnClose.setOnClickListener {
            dialog.dismiss()
        }
        btnVerify.setOnClickListener {
            allsetPopup(dialog, otpET, data)
        }
        btnVerify2.setOnClickListener {
            allsetPopup(dialog, otpET, data)
        }
        dialog.show()
    }

    private fun allsetPopup(dialog: Dialog, otpET: EditText, data: ResponseVerifyMobile) {
        val otpText = otpET.text
        if (!otpText.isNullOrBlank()) {
            dialog.dismiss()

            val loginAPI = APIClient()

            loginAPI.publicAccessToken = data.data[0].apiToken

            // call verify otp popup
            Log.i("eee14", Gson().toJson(data))
            // call api to verify mobile
            val requestVerifyMobile = RequestVerifyOTP()
            requestVerifyMobile.code = "91"
            requestVerifyMobile.mobile = data.data[0].customerData[0].customerMobile;
            requestVerifyMobile.name = "User"
            requestVerifyMobile.otp = otpText.toString()

            val callServicProviders = APIClient.getApiInterface().postOtpMobile(requestVerifyMobile)
            callServicProviders.enqueue(object : Callback<ResponseVerifyMobile> {
                override fun onResponse(call: Call<ResponseVerifyMobile>, response: Response<ResponseVerifyMobile>) {

                    Log.i("eee11", Gson().toJson(requestVerifyMobile))
                    Log.i("eee12", Gson().toJson(call.request().url()))
                    if (response.isSuccessful) {
                        val data2 = response.body()
                        if (data2!!.data.size > 0 && data2.data[0].customerData.size > 0) {

                            customerUser = data2.data[0].customerData[0]

                            val storage = localStorage(context)
                            storage.token = data2.data[0].apiToken
                            storage.setUserLogin()
                            storage.saveLoggedInUser(data2.data[0].customerData[0])

                            loginAPI.publicAccessToken = data2.data[0].apiToken

                            // now open Account
                            OpenAccount()
                        } else {
                            somethingWentWrong(data2.message)
                        }

                        Log.i("eeee3", Gson().toJson(response.body()))

                    } else {
                        //  Log.i("eeee1",response.body().toString())
                        somethingWentWrong()
                    }
                }

                override fun onFailure(call: Call<ResponseVerifyMobile>, t: Throwable) {
                    //  Log.i("eeee2","Request Failure")
                    somethingWentWrong()
                }
            })


        } else {
            Toast.makeText(context, "OTP is required", Toast.LENGTH_SHORT).show()
        }
    }

}