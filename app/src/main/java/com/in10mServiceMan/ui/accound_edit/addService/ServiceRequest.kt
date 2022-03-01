package com.in10mServiceMan.ui.accound_edit.addService

data class ServiceRequest(
    val serviceman_id: Int,
    val services: List<TotalService>
)

data class TotalService(
    val certificate: Int,
    val service_id: Int,
    val total_experience: Int
)