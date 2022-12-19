package com.in10mServiceMan.models

data class ServiceProviderLocationUpdate(
    val `data`: List<ServiceProviderLocationUpdateData>,
    val message: String,
    val status: Int
)