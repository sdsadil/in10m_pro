package com.in10mServiceMan.ui.complete_profile_api

class CompleteProfilePresenter(val view: ICompleteProfileView): ICompleteProfileInteractor, ICompleteProfileInteractorListener {

    var interactor = CompleteProfileInteractor(this)

    override fun onCompleteProfileCompleted(mPost: CompleteProfileResponse) {
        view.onCompleteProfileCompleted(mPost)
    }

    override fun onCompleteProfileFailed(msg: String) {
        view.onCompleteProfileFailed(msg)
    }

    override fun completeProfile(mServicemanId: Int) {
        interactor.completeProfile(mServicemanId)
    }
}