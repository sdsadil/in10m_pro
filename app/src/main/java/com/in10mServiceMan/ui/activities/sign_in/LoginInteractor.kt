package com.in10mServiceMan.ui.activities.sign_in

import android.widget.Toast
import com.in10mServiceMan.Models.CustomerCompleteProfile
import com.in10mServiceMan.ui.apis.LoginAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginInteractor(var listener: ILoginInteractorListener) : ILoginInteractor {
    override fun getCompleteProfile(userId: String) {
        val callServiceProviders = LoginAPI.loginUser().getCompleteProfile(userId.toInt())
        callServiceProviders.enqueue(object : Callback<CustomerCompleteProfile> {
            override fun onResponse(call: Call<CustomerCompleteProfile>, response: Response<CustomerCompleteProfile>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data!!.data != null) {
                        listener.onCompleteProfileReceived(response.body()!!)
                    }

                } else {
                    listener.onFailed("Something went wrong..")
                }
            }

            override fun onFailure(call: Call<CustomerCompleteProfile>, t: Throwable) {
                listener.onFailed("Something went wrong..")
            }
        })
    }

    override fun sendResetLink(email: String) {
        val homeCall = LoginAPI.loginUser().sendResetPasswordLink(email)
        homeCall.enqueue(object : Callback<LinkSendResponse> {
            override fun onResponse(call: Call<LinkSendResponse>, response: Response<LinkSendResponse>) {
                if (response.isSuccessful) {
                    listener.onResetLinkSend(response.body()!!)
                } else {
                    listener.onFailed("Something went Wrong!!")
                }
            }

            override fun onFailure(call: Call<LinkSendResponse>, t: Throwable) {
                listener.onFailed(t.localizedMessage)
            }
        })
    }

    override fun userLogin(email: String, password: String) {
        val homeCall = LoginAPI.loginUser().userLogin(email, password)
        homeCall.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    listener.onLoginCompleted(response.body()!!)
                } else {
                    listener.onFailed("Something went Wrong!!")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                listener.onFailed(t.localizedMessage)
            }
        })
    }
}