package com.in10mServiceMan.ui.activities.my_bookings.invoice_details_API

class InvoiceDetailsPresenter(val view: IInvoiceDetailsView): IInvoiceDetailsInteractor, IInvoiceDetailsInteractorListener {

    var invoiceDetailsInteractor = InvoiceDetailsInteractor(this)

    override fun onInvoiceDetailsCompleted(mPost: InvoiceDetailsResponse) {
        view.onInvoiceDetailsCompleted(mPost)
    }

    override fun onInvoiceDetailsFailed(msg: String) {
        view.onInvoiceDetailsFailed(msg)
    }

    override fun invoiceDetails(mBookingId: Int, mUserId: Int, mUserType: Int) {
        invoiceDetailsInteractor.invoiceDetails(mBookingId, mUserId, mUserType)
    }
}