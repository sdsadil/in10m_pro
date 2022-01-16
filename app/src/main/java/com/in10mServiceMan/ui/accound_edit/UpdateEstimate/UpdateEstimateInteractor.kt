package com.in10mServiceMan.ui.accound_edit.UpdateEstimate

import com.in10mServiceMan.ui.apis.LoginAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateEstimateInteractor(var listener: IUpdateEstimateInteractorListener): IUpdateEstimateInteractor {
    override fun updateEstimate(mServicemanId: Int, mFreeEstimate: String, mEstimateFee: String) {
        LoginAPI.loginUser().updateEstimate(mServicemanId, mFreeEstimate, mEstimateFee).enqueue(object : Callback<UpdateEstimateResponse> {
            override fun onFailure(call: Call<UpdateEstimateResponse>?, t: Throwable?) {
                listener.onUpdateEstimateFailed("Something went wrong")
            }

            override fun onResponse(call: Call<UpdateEstimateResponse>?, response: Response<UpdateEstimateResponse>?) {
                try {
                    listener.onUpdateEstimateCompleted(response?.body()!!)
                } catch (e: Exception) {
                    listener.onUpdateEstimateFailed("")
                }
            }
        })
    }
}