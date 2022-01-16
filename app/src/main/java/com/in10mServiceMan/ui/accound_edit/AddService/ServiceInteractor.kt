package com.in10mServiceMan.ui.accound_edit.AddService

import com.in10mServiceMan.ui.apis.LoginAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceInteractor(var listener: IServiceInteractorListener): IServiceInteractor {
    override fun services(mServicemanId: Int, mService: String) {
        LoginAPI.loginUser().addNewServiceman(mServicemanId, mService).enqueue(object : Callback<ServiceResponse> {
            override fun onFailure(call: Call<ServiceResponse>?, t: Throwable?) {
                listener.onServiceFailed("Something went wrong")
            }

            override fun onResponse(call: Call<ServiceResponse>?, response: Response<ServiceResponse>?) {
                try {
                    listener.onServiceCompleted(response?.body()!!)
                } catch (e: Exception) {
                    listener.onServiceFailed("")
                }
            }

        })
    }
}