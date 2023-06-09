package com.in10mServiceMan.ui.accound_edit.checkStripe

interface ICheckStripeView
{
    fun onCheckStripeCompleted(mPost: CheckStripeResponse)
    fun onCheckStripeFailed(msg:String)
}

interface ICheckStripePresenter
{
    fun checkStripe(mServicemanId: String)
}

interface ICheckStripeInteractor: ICheckStripePresenter

interface ICheckStripeInteractorListener: ICheckStripeView