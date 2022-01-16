package com.in10mServiceMan.ui.complete_profile_api

import com.in10mServiceMan.ui.apis.LoginAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CompleteProfileInteractor(var listener: ICompleteProfileInteractorListener): ICompleteProfileInteractor  {
    override fun completeProfile(mServicemanId: Int) {
        LoginAPI.loginUser().completeProfile(mServicemanId).enqueue(object : Callback<CompleteProfileResponse> {
            override fun onFailure(call: Call<CompleteProfileResponse>?, t: Throwable?) {
                listener.onCompleteProfileFailed("Something went wrong")
            }

            override fun onResponse(call: Call<CompleteProfileResponse>?, response: Response<CompleteProfileResponse>?) {
                try {
                    listener.onCompleteProfileCompleted(response?.body()!!)
                } catch (e: Exception) {
                    listener.onCompleteProfileFailed("")
                }
            }
        })
    }
}