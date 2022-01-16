package com.in10mServiceMan.ui.activities.signup

import com.google.gson.annotations.SerializedName


data class ProfilePicUploadResponse(
        @SerializedName("data")
        val `data`: ProfilePicData?,
        @SerializedName("message")
        val message: String?,
        @SerializedName("status")
        val status: Int?
)

data class ProfilePicData(
        @SerializedName("image_url")
        val imageUrl: String?
)