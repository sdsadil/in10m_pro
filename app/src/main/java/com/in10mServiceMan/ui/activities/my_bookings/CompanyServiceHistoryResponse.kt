package com.in10mServiceMan.ui.activities.my_bookings

import com.google.gson.annotations.SerializedName


data class CompanyServiceHistoryResponse(
        @SerializedName("data")
        val `data`: List<CSHData?>? = listOf(),
        @SerializedName("current_page")
        val currentPage: Int? = 0,
        @SerializedName("message")
        val message: String? = "",
        @SerializedName("status")
        val status: Int? = 0,
        @SerializedName("total")
        val total: Int? = 0
)

data class CSHData(
        @SerializedName("service")
        val service: String? = "",
        @SerializedName("service_color")
        val serviceColor: String? = "",
        @SerializedName("serviceman_image")
        val servicemanImage: String? = "",
        @SerializedName("serviceman_name")
        val servicemanName: String? = "",
        @SerializedName("services")
        val services: List<Any?>? = listOf(),
        @SerializedName("total_bookings")
        val totalBookings: Int? = 0,
        @SerializedName("total_price")
        val totalPrice: Int? = 0
)