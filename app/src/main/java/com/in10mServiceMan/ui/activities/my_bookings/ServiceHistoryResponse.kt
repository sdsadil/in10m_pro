package com.in10mServiceMan.ui.activities.my_bookings

import com.google.gson.annotations.SerializedName


data class ServiceHistoryResponse(
    val current_page: Int,
    val `data`: List<ServiceHistoryData>,
    val message: String,
    val status: Int,
    val total: Int
)

data class ServiceHistoryData(
    val address: String,
    val booking_id: Int,
    val customer_image: String,
    val customer_name: String,
    val paid: Any,
    val price: Double,
    val service: String,
    val service_color: String,
    val service_date: String,
    val service_status: Int,
    val services: List<String>
)