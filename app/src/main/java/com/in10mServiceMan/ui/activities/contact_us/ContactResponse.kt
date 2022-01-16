package com.in10mServiceMan.ui.activities.contact_us
import com.google.gson.annotations.SerializedName


data class ContactResponse(
    @SerializedName("message")
    val message: String? = "",
    @SerializedName("status")
    val status: Int? = 0
)