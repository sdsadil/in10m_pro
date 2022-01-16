package com.in10mServiceMan.ui.activities.signup
import com.google.gson.annotations.SerializedName


data class ApiResponse(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?
)