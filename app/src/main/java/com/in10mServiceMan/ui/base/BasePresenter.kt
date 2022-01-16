package com.in10mServiceMan.ui.base

class BasePresenter(val view: IBaseView) : IBasePresenter, IBaseInteractorListener {
    override fun onSessionExpired() {
        view.onSessionExpired()
    }

    override fun onSessionValid() {
        view.onSessionValid()
    }

    override fun checkSession(header: String, userId: String) {
        mInteractor?.checkSession(header, userId)
    }

    val mInteractor = BaseInteractor(this)
}