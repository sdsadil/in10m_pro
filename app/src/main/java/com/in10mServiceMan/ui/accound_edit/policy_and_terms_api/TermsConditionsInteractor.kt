package com.in10mServiceMan.ui.accound_edit.policy_and_terms_api

import com.in10mServiceMan.ui.apis.LoginAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TermsConditionsInteractor(var listener: ITermsConditionsInteractorListener): ITermsConditionsInteractor {
    override fun termsConditions(mServicemanId: Int, mUserType: Int) {
        LoginAPI.loginUser().termsConditions(mServicemanId,mUserType).enqueue(object : Callback<PolicyAndTermsResponse> {
            override fun onFailure(call: Call<PolicyAndTermsResponse>?, t: Throwable?) {
                listener.onTermsConditionsFailed("Something went wrong")
            }

            override fun onResponse(call: Call<PolicyAndTermsResponse>?, response: Response<PolicyAndTermsResponse>?) {
                try {
                    listener.onTermsConditionsCompleted(response?.body()!!)
                } catch (e: Exception) {
                    listener.onTermsConditionsFailed("")
                }
            }
        })
    }
}