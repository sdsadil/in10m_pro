package com.in10mServiceMan.ui.accound_edit.policy_and_terms_api

class PrivacyPolicyPresenter(val view: IPrivacyPolicyView): IPrivacyPolicyInteractor, IPrivacyPolicyInteractorListener {

    var interactor = PrivacyPolicyInteractor(this)

    override fun onPrivacyPolicyCompleted(mPost: PolicyAndTermsResponse) {
        view.onPrivacyPolicyCompleted(mPost)
    }

    override fun onPrivacyPolicyFailed(msg: String) {
        view.onPrivacyPolicyFailed(msg)
    }

    override fun privacyPolicy(mServicemanId: Int, mUserType: Int) {
        interactor.privacyPolicy(mServicemanId, mUserType)
    }
}