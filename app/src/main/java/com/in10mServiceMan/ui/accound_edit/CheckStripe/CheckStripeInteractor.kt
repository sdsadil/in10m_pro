package com.in10mServiceMan.ui.accound_edit.CheckStripe

import com.in10mServiceMan.ui.apis.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckStripeInteractor(var listener: ICheckStripeInteractorListener): ICheckStripeInteractor {
    override fun checkStripe(mServicemanId: String) {
        APIClient.getApiInterface().checkStripe(mServicemanId).enqueue(object : Callback<CheckStripeResponse> {
            override fun onFailure(call: Call<CheckStripeResponse>?, t: Throwable?) {
                listener.onCheckStripeFailed("Something went wrong")
            }

            override fun onResponse(call: Call<CheckStripeResponse>?, response: Response<CheckStripeResponse>?) {
                try {
                    listener.onCheckStripeCompleted(response?.body()!!)
                } catch (e: Exception) {
                    listener.onCheckStripeFailed("")
                }
            }
        })
    }
}