package com.in10mServiceMan.ui.accound_edit.updateEstimate

interface IUpdateEstimateView
{
    fun onUpdateEstimateCompleted(mPost: UpdateEstimateResponse)
    fun onUpdateEstimateFailed(msg:String)
}

interface IUpdateEstimatePresenter
{
    fun updateEstimate(mServicemanId: Int, mFreeEstimate: String, mEstimateFee: String)
}

interface IUpdateEstimateInteractor: IUpdateEstimatePresenter

interface IUpdateEstimateInteractorListener: IUpdateEstimateView