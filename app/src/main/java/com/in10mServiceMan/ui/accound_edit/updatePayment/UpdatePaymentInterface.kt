package com.in10mServiceMan.ui.accound_edit.updatePayment

interface IUpdatePaymentView
{
    fun onUpdatePaymentCompleted(mPost: UpdatePaymentResponse)
    fun onUpdatePaymentFailed(msg:String)
}

interface IUpdatePaymentPresenter
{
    fun updatePayment(mServicemanId: Int, mPaymentMethod: String)
}

interface IUpdatePaymentInteractor: IUpdatePaymentPresenter

interface IUpdatePaymentInteractorListener: IUpdatePaymentView