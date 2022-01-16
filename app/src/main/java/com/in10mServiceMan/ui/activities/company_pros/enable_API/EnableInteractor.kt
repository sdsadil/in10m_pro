package com.in10mServiceMan.ui.activities.company_pros.enable_API

import com.in10mServiceMan.ui.activities.company_pros.IAResponse
import com.in10mServiceMan.ui.apis.LoginAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EnableInteractor(var listener: IEnableInteractorListener): IEnableInteractor {
    override fun enable(mCompanyId: Int, mCompanyProId: Int) {
        LoginAPI.loginUser().activate(mCompanyId, mCompanyProId).enqueue(object : Callback<IAResponse> {
            override fun onFailure(call: Call<IAResponse>?, t: Throwable?) {
                listener.onEnableFailed("Something went wrong")
            }

            override fun onResponse(call: Call<IAResponse>?, response: Response<IAResponse>?) {
                try {
                    listener.onEnableCompleted(response?.body()!!)
                } catch (e: Exception) {
                    listener.onEnableFailed("")
                }
            }
        })
    }
}