package com.in10mServiceMan.ui.activities.company_pros.delete_API

import com.in10mServiceMan.ui.activities.company_pros.IAResponse
import com.in10mServiceMan.ui.apis.LoginAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteInteractor(var listener: IDeleteInteractorListener): IDeleteInteractor {
    override fun delete(mCompanyId: Int, mCompanyProId: Int) {
        LoginAPI.loginUser().delete(mCompanyId, mCompanyProId).enqueue(object : Callback<IAResponse> {
            override fun onFailure(call: Call<IAResponse>?, t: Throwable?) {
                listener.onDeleteFailed("Something went wrong")
            }

            override fun onResponse(call: Call<IAResponse>?, response: Response<IAResponse>?) {
                try {
                    listener.onDeleteCompleted(response?.body()!!)
                } catch (e: Exception) {
                    listener.onDeleteFailed("")
                }
            }
        })
    }
}