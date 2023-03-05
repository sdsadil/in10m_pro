package com.in10mServiceMan.ui.activities.payment

import com.google.firebase.database.PropertyName
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PaymentRequestClass {

    @SerializedName("customer_id")
    @Expose
    var customer_id: String? = null

    @SerializedName("service_man_id")
    @Expose
    var service_man_id: Int? = null

    @SerializedName("cash_amount")
    @Expose
    var cash_amount: String? = null

    @SerializedName("payment_type")
    @Expose
    var payment_type: Int? = null

    @SerializedName("pay_status")
    @Expose
    var pay_status: Boolean? = null

    @SerializedName("booking_Id")
    @Expose
    var booking_Id: String? = null

    @SerializedName("tax")
    @Expose
    var tax: Double? = null

    @SerializedName("processingFee")
    @Expose
    var processingFee: Double? = null

    @SerializedName("tax_percent")
    @Expose
    var tax_percent: Double? = null

    @SerializedName("applicationFee")
    @Expose
    var applicationFee: Double? = null

    @SerializedName("fee_percent")
    @Expose
    var fee_percent: String? = null

    @SerializedName("accepted_status")
    @Expose
    var accepted_status: Boolean = false

    @SerializedName("editing_status")
    @Expose
    var editing_status: Boolean = false

    @SerializedName("service_amount")
    @Expose
    var service_amount: Double? = null

    @SerializedName("total_amount")
    @Expose
    var total_amount: String? = null

    @SerializedName("work_scope")
    @Expose
    var work_scope: String? = null

}