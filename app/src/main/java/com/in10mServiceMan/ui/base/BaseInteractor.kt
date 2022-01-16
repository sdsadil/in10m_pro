package com.in10mServiceMan.ui.base

import com.in10mServiceMan.ui.activities.rating.ReviewsResponse
import com.in10mServiceMan.ui.apis.LoginAPI
import com.in10mServiceMan.utils.SharedPreferencesHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BaseInteractor(val listener: IBaseInteractorListener) : IBaseInteractor {
    override fun checkSession(header:String,userId:String) {

        val callServiceProviders = LoginAPI.loginUser().getOwnReviews("Bearer $header", userId)
        callServiceProviders.enqueue(object : Callback<ReviewsResponse> {
            override fun onResponse(call: Call<ReviewsResponse>, response: Response<ReviewsResponse>) {
                if (response?.code() == 401 && response?.message()=="Unauthorized") {
                    listener.onSessionExpired()
                } else {
                    listener.onSessionValid()
                }
            }

            override fun onFailure(call: Call<ReviewsResponse>, t: Throwable) {
                listener.onSessionValid()
            }
        })
    }
}