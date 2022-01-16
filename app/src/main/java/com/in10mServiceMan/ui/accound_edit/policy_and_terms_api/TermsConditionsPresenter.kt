package com.in10mServiceMan.ui.accound_edit.policy_and_terms_api

class TermsConditionsPresenter(val view: ITermsConditionsView): ITermsConditionsInteractor, ITermsConditionsInteractorListener {

    var interactor = TermsConditionsInteractor(this)

    override fun onTermsConditionsCompleted(mPost: PolicyAndTermsResponse) {
        view.onTermsConditionsCompleted(mPost)
    }

    override fun onTermsConditionsFailed(msg: String) {
        view.onTermsConditionsFailed(msg)
    }

    override fun termsConditions(mServicemanId: Int, mUserType: Int) {
        interactor.termsConditions(mServicemanId, mUserType)
    }
}