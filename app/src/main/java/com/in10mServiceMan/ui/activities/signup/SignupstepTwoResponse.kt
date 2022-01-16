package com.in10mServiceMan.ui.activities.signup

import com.google.gson.annotations.SerializedName


data class SignupstepTwoResponse(
        @SerializedName("data")
        val `data`: StepTwoData?,
        @SerializedName("message")
        val message: String?,
        @SerializedName("status")
        val status: Int?
)

data class StepTwoData(
        @SerializedName("address1")
        val address1: String?,
        @SerializedName("address2")
        val address2: String?,
        @SerializedName("city")
        val city: String?,
        @SerializedName("image_url")
        val imageUrl: String?,
        @SerializedName("state")
        val state: String?,
        @SerializedName("street_name")
        val streetName: String?,
        @SerializedName("zipcode")
        val zipcode: String?
)