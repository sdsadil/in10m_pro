package com.in10mServiceMan.ui.activities.signup

import com.google.gson.annotations.SerializedName


data class SignupThreeResponse(
        @SerializedName("data")
        val `data`: SignUpThreeData?,
        @SerializedName("message")
        val message: String?,
        @SerializedName("status")
        val status: Int?
)

data class SignUpThreeData(
        @SerializedName("company_id")
        val companyId: Any?,
        @SerializedName("country_code")
        val countryCode: String?,
        @SerializedName("email")
        val email: String?,
        @SerializedName("firstname")
        val firstname: String?,
        @SerializedName("image")
        val image: String?,
        @SerializedName("lastname")
        val lastname: String?,
        @SerializedName("mobile")
        val mobile: String?,
        @SerializedName("user_id")
        val userId: Int?
)