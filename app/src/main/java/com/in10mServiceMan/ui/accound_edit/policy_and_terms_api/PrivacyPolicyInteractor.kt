package com.in10mServiceMan.ui.accound_edit.policy_and_terms_api

import com.in10mServiceMan.ui.apis.LoginAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrivacyPolicyInteractor(var listener: IPrivacyPolicyInteractorListener): IPrivacyPolicyInteractor {
    override fun privacyPolicy(mServicemanId: Int, mUserType: Int) {
        LoginAPI.loginUser().privacyPolicy(mServicemanId,mUserType).enqueue(object : Callback<PolicyAndTermsResponse> {
            override fun onFailure(call: Call<PolicyAndTermsResponse>?, t: Throwable?) {
                listener.onPrivacyPolicyFailed("Something went wrong")
            }

            override fun onResponse(call: Call<PolicyAndTermsResponse>?, response: Response<PolicyAndTermsResponse>?) {
                try {
                    listener.onPrivacyPolicyCompleted(response?.body()!!)
                } catch (e: Exception) {
                    listener.onPrivacyPolicyFailed("")
                }
            }
        })
    }
}