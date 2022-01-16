package com.in10mServiceMan.ui.activities.company_pros.serviceman_details_API

import com.in10mServiceMan.ui.apis.LoginAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServicemanDetailsInteractor(var listener: IServicemanDetailsInteractorListener): IServicemanDetailsInteractor {
    override fun servicemanDetails(mServicemanId: Int) {
        LoginAPI.loginUser().getServiceManInfo(mServicemanId).enqueue(object : Callback<ServicemanDetailsResponse> {
            override fun onFailure(call: Call<ServicemanDetailsResponse>?, t: Throwable?) {
                listener.onServicemanDetailsFailed("Something went wrong")
            }

            override fun onResponse(call: Call<ServicemanDetailsResponse>?, response: Response<ServicemanDetailsResponse>?) {
                try {
                    listener.onServicemanDetailsCompleted(response?.body()!!)
                } catch (e: Exception) {
                    listener.onServicemanDetailsFailed("")
                }
            }
        })
    }
}