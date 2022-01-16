package com.in10mServiceMan.ui.activities.profile
import com.google.gson.annotations.SerializedName


data class ProfileUpdateRequest(
    @SerializedName("serviceprovider_adddress2")
    val serviceproviderAdddress2: String? = "",
    @SerializedName("serviceprovider_address1")
    val serviceproviderAddress1: String? = "",
    @SerializedName("serviceprovider_age")
    val serviceproviderAge: String? = "",
    @SerializedName("serviceprovider_city")
    val serviceproviderCity: String? = "",
    @SerializedName("serviceprovider_civil_id")
    val serviceproviderCivilId: String? = "",
    @SerializedName("serviceprovider_country")
    val serviceproviderCountry: String? = "",
    @SerializedName("serviceprovider_country_code")
    val serviceproviderCountryCode: String? = "",
    @SerializedName("serviceprovider_dob")
    val serviceproviderDob: String? = "",
    @SerializedName("serviceprovider_email")
    val serviceproviderEmail: String? = "",
    @SerializedName("serviceprovider_experience")
    val serviceproviderExperience: String? = "",
    @SerializedName("serviceprovider_gender")
    val serviceproviderGender: String? = "",
    @SerializedName("serviceprovider_id")
    val serviceproviderId: Int? = 0,
    @SerializedName("serviceprovider_image")
    val serviceproviderImage: String? = "",
    @SerializedName("serviceprovider_language")
    val serviceproviderLanguage: String? = "",
    @SerializedName("serviceprovider_lastname")
    val serviceproviderLastname: String? = "",
    @SerializedName("serviceprovider_latitude")
    val serviceproviderLatitude: String? = "",
    @SerializedName("serviceprovider_longitude")
    val serviceproviderLongitude: String? = "",
    @SerializedName("serviceprovider_mobile")
    val serviceproviderMobile: String? = "",
    @SerializedName("serviceprovider_name")
    val serviceproviderName: String? = "",
    @SerializedName("serviceprovider_pincode")
    val serviceproviderPincode: String? = "",
    @SerializedName("serviceprovider_rating")
    val serviceproviderRating: String? = "",
    @SerializedName("serviceprovider_state")
    val serviceproviderState: String? = "",
    @SerializedName("serviceprovider_street_name")
    val serviceproviderStreetName: String? = "",
    @SerializedName("serviceprovider_working_as")
    val serviceproviderWorkingAs: String? = ""
)