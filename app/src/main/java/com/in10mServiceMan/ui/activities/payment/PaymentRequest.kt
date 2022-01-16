package com.in10mServiceMan.ui.activities.payment

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class PaymentRequest(
        var customer_id: String? = "",
        var service_man_id: String? = "",
        var cash_amount: String? = "",
        var payment_type: String? = "",
        var pay_status: Boolean = false,
        var booking_Id: String? = "",
        var tax: Double? = 0.0,
        var processingFee: Double? = 0.0,
        var tax_percent: Double? = 0.0,
        var applicationFee: Double? = 0.0,
        var fee_percent: String? = "",
        var is_accepted: Boolean = true,
        var is_editing: Boolean = false,
        var service_fee: String? = "",
        var total_amount: String? = ""
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
                "customer_id" to customer_id,
                "service_man_id" to service_man_id,
                "cash_amount" to cash_amount,
                "payment_type" to payment_type,
                "pay_status" to pay_status,
                "booking_Id" to booking_Id,
                "tax" to tax,
                "processingFee" to processingFee,
                "tax_percent" to tax_percent,
                "applicationFee" to applicationFee,
                "fee_percent" to fee_percent,
                "is_accepted" to is_accepted,
                "is_editing" to is_editing,
                "service_fee" to service_fee,
                "total_amount" to total_amount
        )
    }
}
