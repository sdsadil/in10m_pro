package com.in10mServiceMan.ui.accound_edit.policy_and_terms_api

interface ITermsConditionsView
{
    fun onTermsConditionsCompleted(mPost: PolicyAndTermsResponse)
    fun onTermsConditionsFailed(msg:String)
}

interface ITermsConditionsPresenter
{
    fun termsConditions(mServicemanId: Int, mUserType: Int)
}

interface ITermsConditionsInteractor: ITermsConditionsPresenter

interface ITermsConditionsInteractorListener: ITermsConditionsView