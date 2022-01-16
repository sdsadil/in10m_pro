package com.in10mServiceMan.ui.activities.company_pros.serviceman_details_API

data class ServicemanDetailsResponse(
    val `data`: List<ServicemanDetailsData>,
    val message: String,
    val status: Int
)

data class ServicemanDetailsData(
    val adddress2: String,
    val address1: String,
    val certificate: Int,
    val city: String,
    val country: Any,
    val country_code: String,
    val description: String,
    val dob: String,
    val experience: String,
    val gender: Any,
    val id: Int,
    val image: String,
    val lastname: String,
    val latitude: String,
    val longitude: String,
    val mobile: String,
    val name: String,
    val payment_type: Int,
    val pincode: String,
    val reviews: List<ServicemanDetailsReview>,
    val service_type: String,
    val service_type_icon: String,
    val skill: String,
    val state: Int,
    val street_name: Any,
    val title: String,
    val title_description: String,
    val total_tumbs_down: Int,
    val total_tumbs_up: Int
)

data class ServicemanDetailsReview(
    val description: String,
    val image: String,
    val knowledge_rating: Int,
    val lastname: Any,
    val name: String,
    val overall_rating: Int,
    val price_rating: Int,
    val thumbs_down: Int,
    val thumbs_up: Int
)