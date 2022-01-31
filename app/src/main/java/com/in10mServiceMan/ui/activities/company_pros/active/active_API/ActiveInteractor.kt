package com.in10mServiceMan.ui.activities.company_pros.active.active_API

import com.in10mServiceMan.ui.apis.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ActiveInteractor(var listener: IActiveInteractorListener): IActiveInteractor {
    override fun active(mCompanyId: Int, mCrewStatus: Int) {
        APIClient.getApiInterface().getActiveCompanyDetails(mCompanyId, mCrewStatus).enqueue(object : Callback<ActiveResponse> {
            override fun onFailure(call: Call<ActiveResponse>?, t: Throwable?) {
                listener.onActiveFailed("Something went wrong")
            }

            override fun onResponse(call: Call<ActiveResponse>?, response: Response<ActiveResponse>?) {
                try {
                    listener.onActiveCompleted(response?.body()!!)
                } catch (e: Exception) {
                    listener.onActiveFailed("")
                }
            }
        })
    }
}