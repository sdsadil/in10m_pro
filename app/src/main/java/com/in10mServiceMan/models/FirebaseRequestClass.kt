package com.in10mServiceMan.models
import com.google.gson.annotations.SerializedName


data class FirebaseRequestClass(
    @SerializedName("address")
    val address: String? = "",
    @SerializedName("booking_id")
    val bookingId: String? = "",
    @SerializedName("customer_appt")
    val customerAppt: String? = "",
    @SerializedName("customer_floor")
    val customerFloor: String? = "",
    @SerializedName("customer_id")
    val customerId: String? = "",
    @SerializedName("customer_image")
    val customerImage: String? = "",
    @SerializedName("customer_mobile")
    val customerMobile: String? = "",
    @SerializedName("customer_name")
    val customerName: String? = "",
    @SerializedName("eta")
    val eta: String? = "",
    @SerializedName("experience")
    val experience: String? = "",
    @SerializedName("last_name")
    val lastName: String? = "",
    @SerializedName("latitude")
    val latitude: String? = "",
    @SerializedName("longitute")
    val longitute: String? = "",
    @SerializedName("payment_status")
    val paymentStatus: Boolean? = false,
    @SerializedName("service_id")
    val serviceId: String? = "",
    @SerializedName("service_men_id")
    val serviceMenId: String? = "",
    @SerializedName("service_men_rating")
    val serviceMenRating: String? = "",
    @SerializedName("service_name")
    val serviceName: String? = "",
    @SerializedName("service_Type")
    val serviceType: String? = "",
    @SerializedName("servicemen_image")
    val servicemenImage: String? = "",
    @SerializedName("servicemen_lat")
    val servicemenLat: String? = "",
    @SerializedName("servicemen_long")
    val servicemenLong: String? = "",
    @SerializedName("servicemen_Mob")
    val servicemenMob: String? = "",
    @SerializedName("servicemen_name")
    val servicemenName: String? = "",
    @SerializedName("servicemen_thumsdown")
    val servicemenThumsdown: String? = "",
    @SerializedName("servicemen_thumsup")
    val servicemenThumsup: String? = "",
    @SerializedName("status")
    val status: String? = "",
    @SerializedName("sub_service_id")
    val subServiceId: String? = "",
    @SerializedName("sub_service_name")
    val subServiceName: String? = "",
    @SerializedName("title")
    val title: String? = ""
)