package com.in10mServiceMan.ui.complete_profile_api

data class CompleteProfileResponse(
    val `data`: Data,
    val message: String,
    val status: Int
)

data class Data(
    val adddress2: String,
    val address1: String,
    val approved: Int,
    val city: String,
    val civil_id: Any,
    val country: String,
    val country_code: String,
    val dob: String,
    val email: String,
    val estimation_fee: Int,
    val experience: String,
    val free_estimate: Int,
    val gender: Any,
    val id: Int,
    val image: String,
    val language: Any,
    val lastname: String,
    val latitude: String,
    val longitude: String,
    val mobile: String,
    val name: String,
    val payment_type: Int,
    val pincode: String,
    val privacy_policy_accept: Int,
    val rating: Any,
    val services: List<Service>,
    val state: String,
    val status: Int,
    val street_name: Any,
    val terms_condition_accept: Int,
    val total_tumbs_down: Int,
    val total_tumbs_up: Int,
    val wallet_balance: Any,
    val working_as: String
)

data class Service(
    val service_certificate: Int,
    val service_color: String,
    val service_icon: String,
    val service_id: Int,
    val service_name: String,
    val service_total_experience: Int
)