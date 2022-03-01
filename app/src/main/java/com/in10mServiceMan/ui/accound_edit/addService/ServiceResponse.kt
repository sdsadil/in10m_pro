package com.in10mServiceMan.ui.accound_edit.addService

data class ServiceResponse(
    val `data`: List<Data>,
    val message: String,
    val status: Int
)

data class Data(
    val certificate: Int,
    val icon: String,
    val icon_color: String,
    val service_id: Int,
    val service_name: String,
    val total_experience: Int
)