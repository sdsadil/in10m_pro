package com.in10mServiceMan.ui.activities.sign_in

import com.in10mServiceMan.Models.CustomerCompleteProfile
import java.sql.DatabaseMetaData

interface ILoginView {
    fun onLoginCompleted(mResponse:LoginResponse)
    fun onFailed(msg:String)
    fun onResetLinkSend(mResposne:LinkSendResponse)
    fun onCompleteProfileReceived(metaData: CustomerCompleteProfile)
}

interface ILoginPresenter {

    fun userLogin(email:String,password:String)
    fun sendResetLink(email:String)
    fun getCompleteProfile(userId:String)
}

interface ILoginInteractor : ILoginPresenter

interface ILoginInteractorListener : ILoginView