package com.in10mServiceMan.models

data class UpdateServiceData(
    val certificate: Int,
    val icon: String,
    val icon_color: String,
    val service_id: Int,
    val service_name: String,
    val total_experience: String
)