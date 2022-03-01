package com.in10mServiceMan.ui.accound_edit.checkStripe

class CheckStripePresenter(val view: ICheckStripeView): ICheckStripeInteractor, ICheckStripeInteractorListener {

    var checkStripeInteractor = CheckStripeInteractor(this)

    override fun onCheckStripeCompleted(mPost: CheckStripeResponse) {
        view.onCheckStripeCompleted(mPost)
    }

    override fun onCheckStripeFailed(msg: String) {
        view.onCheckStripeFailed(msg)
    }

    override fun checkStripe(mServicemanId: String) {
        checkStripeInteractor.checkStripe(mServicemanId)
    }
}