package com.in10mServiceMan.ui.activities.company_pros.active.active_API

data class ActiveResponse(
    val `data`: List<ActiveData>,
    val message: String,
    val status: Int
)

data class ActiveData(
    val address: String,
    val earnings: Float,
    val id: Int,
    val name: String,
    val pro_image: String,
    val thumbs_down: Int,
    val thumbs_up: Int,
    val work_status: Int
)