package com.in10mServiceMan.ui.activities.sign_in

import com.google.gson.annotations.SerializedName


data class LinkSendResponse(
        @SerializedName("message")
        val message: String?,
        @SerializedName("status")
        val status: Int?
)