package com.in10mServiceMan.ui.activities.company_pros.disable_API

import com.in10mServiceMan.ui.activities.company_pros.IAResponse
import com.in10mServiceMan.ui.apis.LoginAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DisableInteractor(var listener: IDisableInteractorListener): IDisableInteractor {
    override fun disable(mCompanyId: Int, mCompanyProId: Int) {
        LoginAPI.loginUser().deactivate(mCompanyId, mCompanyProId).enqueue(object : Callback<IAResponse> {
            override fun onFailure(call: Call<IAResponse>?, t: Throwable?) {
                listener.onDisableFailed("Something went wrong")
            }

            override fun onResponse(call: Call<IAResponse>?, response: Response<IAResponse>?) {
                try {
                    listener.onDisableCompleted(response?.body()!!)
                } catch (e: Exception) {
                    listener.onDisableFailed("")
                }
            }
        })
    }
}