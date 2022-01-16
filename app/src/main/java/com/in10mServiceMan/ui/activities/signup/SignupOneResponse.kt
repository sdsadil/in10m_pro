package com.in10mServiceMan.ui.activities.signup

import com.google.gson.annotations.SerializedName


data class SignupOneResponse(
        @SerializedName("data")
        val `data`: List<SignUpData?>?,
        @SerializedName("message")
        val message: String?,
        @SerializedName("status")
        val status: Int?
)

data class SignUpData(
        @SerializedName("api_token")
        val apiToken: String?,
        @SerializedName("customer_data")
        val customerData: List<CustomerData?>?
)

data class CustomerData(
        @SerializedName("customer_company")
        val customerCompany: Any?,
        @SerializedName("customer_email")
        val customerEmail: String?,
        @SerializedName("customer_id")
        val customerId: Int?,
        @SerializedName("customer_mobile")
        val customerMobile: String?,
        @SerializedName("customer_mobile_code")
        val customerMobileCode: String?,
        @SerializedName("customer_name")
        val customerName: String?,
        @SerializedName("otp")
        val otp: Int?
)