package com.in10mServiceMan.ui.accound_edit.policy_and_terms_api

interface IPrivacyPolicyView
{
    fun onPrivacyPolicyCompleted(mPost: PolicyAndTermsResponse)
    fun onPrivacyPolicyFailed(msg:String)
}

interface IPrivacyPolicyPresenter
{
    fun privacyPolicy(mServicemanId: Int, mUserType: Int)
}

interface IPrivacyPolicyInteractor: IPrivacyPolicyPresenter

interface IPrivacyPolicyInteractorListener: IPrivacyPolicyView