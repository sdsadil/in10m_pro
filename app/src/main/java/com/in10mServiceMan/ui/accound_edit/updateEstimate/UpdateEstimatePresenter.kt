package com.in10mServiceMan.ui.accound_edit.updateEstimate

class UpdateEstimatePresenter(val view: IUpdateEstimateView): IUpdateEstimateInteractor, IUpdateEstimateInteractorListener {

    var updateEstimateInteractor = UpdateEstimateInteractor(this)

    override fun onUpdateEstimateCompleted(mPost: UpdateEstimateResponse) {
        view.onUpdateEstimateCompleted(mPost)
    }

    override fun onUpdateEstimateFailed(msg: String) {
        view.onUpdateEstimateFailed(msg)
    }

    override fun updateEstimate(mServicemanId: Int, mFreeEstimate: String, mEstimateFee: String) {
        updateEstimateInteractor.updateEstimate(mServicemanId, mFreeEstimate, mEstimateFee)
    }
}