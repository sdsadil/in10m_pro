package com.in10mServiceMan.ui.accound_edit.UpdatePayment

import com.in10mServiceMan.ui.apis.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdatePaymentInteractor(var listener: IUpdatePaymentInteractorListener): IUpdatePaymentInteractor {
    override fun updatePayment(mServicemanId: Int, mPaymentMethod: String) {
        APIClient.getApiInterface().updatePayment(mServicemanId, mPaymentMethod).enqueue(object : Callback<UpdatePaymentResponse> {
            override fun onFailure(call: Call<UpdatePaymentResponse>?, t: Throwable?) {
                listener.onUpdatePaymentFailed("Something went wrong")
            }

            override fun onResponse(call: Call<UpdatePaymentResponse>?, response: Response<UpdatePaymentResponse>?) {
                try {
                    listener.onUpdatePaymentCompleted(response?.body()!!)
                } catch (e: Exception) {
                    listener.onUpdatePaymentFailed("")
                }
            }
        })
    }
}