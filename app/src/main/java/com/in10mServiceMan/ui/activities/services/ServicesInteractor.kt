package com.in10mServiceMan.ui.activities.services

import com.in10mServiceMan.models.HomeService
import com.in10mServiceMan.ui.apis.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServicesInteractor(var listener: IServicesInteractorListener) : IServicesInteractor {
    override fun getServices() {
        val homeCall = APIClient.getApiInterface().services
        homeCall.enqueue(object : Callback<HomeService> {
            override fun onResponse(call: Call<HomeService>, response: Response<HomeService>) {
                if (response.isSuccessful) {
                    listener.onServiceCompleted(response.body()!!)
                } else {

                }
            }

            override fun onFailure(call: Call<HomeService>, t: Throwable) {
                listener.onFailed(t.localizedMessage)
            }
        })
    }

}