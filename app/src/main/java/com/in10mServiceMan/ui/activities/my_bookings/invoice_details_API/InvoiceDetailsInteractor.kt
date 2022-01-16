package com.in10mServiceMan.ui.activities.my_bookings.invoice_details_API

import com.in10mServiceMan.ui.apis.LoginAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InvoiceDetailsInteractor(var listener: IInvoiceDetailsInteractorListener): IInvoiceDetailsInteractor {
    override fun invoiceDetails(mBookingId: Int, mUserId: Int, mUserType: Int) {
        LoginAPI.loginUser().getInvoiceDetails(mBookingId,mUserId,mUserType).enqueue(object : Callback<InvoiceDetailsResponse> {
            override fun onFailure(call: Call<InvoiceDetailsResponse>?, t: Throwable?) {
                listener.onInvoiceDetailsFailed("Something went wrong")
            }

            override fun onResponse(call: Call<InvoiceDetailsResponse>?, response: Response<InvoiceDetailsResponse>?) {
                try {
                    listener.onInvoiceDetailsCompleted(response?.body()!!)
                } catch (e: Exception) {
                    listener.onInvoiceDetailsFailed("")
                }
            }
        })
    }
}