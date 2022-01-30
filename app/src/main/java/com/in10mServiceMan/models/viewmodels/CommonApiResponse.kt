package com.in10mServiceMan.models.viewmodels

import com.google.gson.annotations.SerializedName


data class CommonApiResponse(
        @SerializedName("data")
        val `data`: List<Any?>? = listOf(),
        @SerializedName("message")
        val message: String? = "",
        @SerializedName("status")
        val status: Int? = 0
)