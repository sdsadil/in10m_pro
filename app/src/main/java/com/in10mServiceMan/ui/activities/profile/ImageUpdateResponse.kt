package com.in10mServiceMan.ui.activities.profile

import com.google.gson.annotations.SerializedName


data class ImageUpdateResponse(
        @SerializedName("message")
        val message: String?,
        @SerializedName("status")
        val status: Int?
)