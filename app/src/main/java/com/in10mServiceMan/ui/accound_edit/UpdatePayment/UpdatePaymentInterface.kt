package com.in10mServiceMan.ui.accound_edit.UpdatePayment

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