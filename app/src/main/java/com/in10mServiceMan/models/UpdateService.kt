package com.in10mServiceMan.models

data class UpdateService(
    val `data`: List<UpdateServiceData>,
    val message: String,
    val status: Int
)