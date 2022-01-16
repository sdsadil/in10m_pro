package com.in10mServiceMan.ui.complete_profile_api

interface ICompleteProfileView
{
    fun onCompleteProfileCompleted(mPost: CompleteProfileResponse)
    fun onCompleteProfileFailed(msg:String)
}

interface ICompleteProfilePresenter
{
    fun completeProfile(mServicemanId: Int)
}

interface ICompleteProfileInteractor: ICompleteProfilePresenter

interface ICompleteProfileInteractorListener: ICompleteProfileView