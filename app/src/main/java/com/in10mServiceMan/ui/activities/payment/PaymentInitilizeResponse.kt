package com.in10mServiceMan.ui.activities.payment
import com.google.gson.annotations.SerializedName


data class PaymentInitilizeResponse(
    @SerializedName("data")
    val `data`: Data? = Data(),
    @SerializedName("message")
    val message: String? = "",
    @SerializedName("status")
    val status: Int? = 0
)

data class Data(
    @SerializedName("applicationFee")
    val applicationFee: Double? = 0.0,
    @SerializedName("fee_percent")
    val feePercent: String? = "",
    @SerializedName("processingFee")
    val processingFee: Double? = 0.0,
    @SerializedName("service_fee")
    val serviceFee: Double? = 0.0,
    @SerializedName("service_fee_percent")
    val serviceFeePercent: Double? = 0.0,
    @SerializedName("serviceman_service_amount")
    val servicemanServiceAmount: Double? = 0.0,
    @SerializedName("tax")
    val tax: Double? = 0.0,
    @SerializedName("tax_percent")
    val taxPercent: Double? = 0.0,
    @SerializedName("total_amount")
    val totalAmount: Double? = 0.0,
    @SerializedName("user_app_fee")
    val userAppFee: Double? = 0.0,
    @SerializedName("user_app_fee_percent")
    val userAppFeePercent: Double? = 0.0
)