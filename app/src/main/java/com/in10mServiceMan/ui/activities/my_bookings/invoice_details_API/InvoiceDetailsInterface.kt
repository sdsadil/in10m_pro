package com.in10mServiceMan.ui.activities.my_bookings.invoice_details_API

interface IInvoiceDetailsView
{
    fun onInvoiceDetailsCompleted(mPost: InvoiceDetailsResponse)
    fun onInvoiceDetailsFailed(msg:String)
}

interface IInvoiceDetailsPresenter
{
    fun invoiceDetails(mBookingId: Int, mUserId: Int, mUserType: Int)
}

interface IInvoiceDetailsInteractor: IInvoiceDetailsPresenter

interface IInvoiceDetailsInteractorListener: IInvoiceDetailsView