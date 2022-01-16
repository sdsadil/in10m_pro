package com.in10mServiceMan.ui.activities.my_bookings.invoice_details_API

data class InvoiceDetailsResponse(
    val `data`: Data,
    val message: String,
    val status: Int
)

data class Data(
    val amount_paid: Float,
    val customer_name: String,
    val date: String,
    val in10m_fee: Float,
    val payment_method: Int,
    val service: String,
    val serviceman_charge: Float,
    val transaction_id: Any
)