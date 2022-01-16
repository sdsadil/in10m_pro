package com.in10mServiceMan.ui.activities.sign_in

import com.in10mServiceMan.Models.CustomerCompleteProfile

class LoginPresenter(val view: ILoginView) : ILoginInteractor, ILoginInteractorListener {
    override fun onCompleteProfileReceived(metaData: CustomerCompleteProfile) {
        view.onCompleteProfileReceived(metaData)
    }

    override fun getCompleteProfile(userId: String) {
        mInteractor.getCompleteProfile(userId)
    }

    override fun onResetLinkSend(mResposne: LinkSendResponse) {
        view.onResetLinkSend(mResposne)
    }

    override fun sendResetLink(email: String) {
        mInteractor.sendResetLink(email)
    }

    override fun onLoginCompleted(mResponse: LoginResponse) {
        view.onLoginCompleted(mResponse)
    }

    override fun onFailed(msg: String) {
        view.onFailed(msg)

    }

    override fun userLogin(email: String, password: String) {
        mInteractor.userLogin(email, password)
    }

    val mInteractor = LoginInteractor(this)
}