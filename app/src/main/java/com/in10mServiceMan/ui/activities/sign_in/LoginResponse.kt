package com.in10mServiceMan.ui.activities.sign_in

import com.google.gson.annotations.SerializedName


data class LoginResponse(
    @SerializedName("data")
    val `data`: List<Data?>? = listOf(),
    @SerializedName("message")
    val message: String? = "",
    @SerializedName("status")
    val status: Int? = 0
)

data class Data(
    @SerializedName("api_token")
    val apiToken: String? = "",
    @SerializedName("customer_data")
    val customerData: List<CustomerData?>? = listOf()
)

data class CustomerData(
    @SerializedName("phone_verified")
    val phoneVerified: Any? = Any(),
    @SerializedName("registration_step")
    val registrationStep: Int? = 0,
    @SerializedName("service_person_address1")
    val servicePersonAddress1: String? = "",
    @SerializedName("service_person_address2")
    val servicePersonAddress2: String? = "",
    @SerializedName("service_person_city")
    val servicePersonCity: String? = "",
    @SerializedName("service_person_company")
    val servicePersonCompany: Int? = null,
    @SerializedName("service_person_email")
    val servicePersonEmail: String? = "",
    @SerializedName("service_person_id")
    val servicePersonId: Int? = 0,
    @SerializedName("service_person_mobile")
    val servicePersonMobile: String? = "",
    @SerializedName("service_person_mobile_code")
    val servicePersonMobileCode: String? = "",
    @SerializedName("service_person_name")
    val servicePersonName: String? = "",
    @SerializedName("service_person_rating")
    val servicePersonRating: Any? = Any(),
    @SerializedName("service_person_state")
    val servicePersonState: String? = "",
    @SerializedName("service_person_street_name")
    val servicePersonStreetName: String? = "",
    @SerializedName("service_person_total_tumbs_down")
    val servicePersonTotalTumbsDown: Int? = 0,
    @SerializedName("service_person_total_tumbs_up")
    val servicePersonTotalTumbsUp: Int? = 0,
    @SerializedName("service_person_type")
    val servicePersonType: Int? = 0,
    @SerializedName("service_person_zipcode")
    val servicePersonZipcode: String? = "",
    @SerializedName("service_person_payment_type")
    val servicePersonPaymntType: Int? = 0
)