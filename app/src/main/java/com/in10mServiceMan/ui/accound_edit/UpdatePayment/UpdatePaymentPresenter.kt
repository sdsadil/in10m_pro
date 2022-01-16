package com.in10mServiceMan.ui.accound_edit.UpdatePayment

class UpdatePaymentPresenter(val view: IUpdatePaymentView): IUpdatePaymentInteractor, IUpdatePaymentInteractorListener {

    var updatePaymentInteractor = UpdatePaymentInteractor(this)

    override fun onUpdatePaymentCompleted(mPost: UpdatePaymentResponse) {
        view.onUpdatePaymentCompleted(mPost)
    }

    override fun onUpdatePaymentFailed(msg: String) {
        view.onUpdatePaymentFailed(msg)
    }

    override fun updatePayment(mServicemanId: Int, mPaymentMethod: String) {
        updatePaymentInteractor.updatePayment(mServicemanId, mPaymentMethod)
    }
}