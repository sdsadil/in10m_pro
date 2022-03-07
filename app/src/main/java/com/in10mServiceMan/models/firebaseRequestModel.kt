package com.in10mServiceMan.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class firebaseRequestModel(
        var customer_id: String? = "",
        var customer_name: String? = "",
        var customer_image: String? = "",
        var customer_mobile: String? = "",
        var address: String? = "",
        var eta: String? = "",
        var experience: String? = "",
        var last_name: String? = "",
        var latitude: String? = "",
        var longitute: String? = "",
        var service_id: String? = "",
        var service_men_id: String? = "",
        var service_men_rating: String? = "",
        var servicemen_image: String? = "",
        var service_name: String? = "",
        var service_ar_name: String? = "",
        var status: String? = "",
        var sub_service_id: String? = "",
        var sub_service_name: String? = "",
        var title: String? = "",
        var customer_appt: String? = "",
        var customer_floor: String? = "",
        var servicemen_lat: String? = "",
        var servicemen_long: String? = "",
        var booking_id: String? = "",
        var payment_status: Boolean = false,
        var service_Type: String = "",
        var servicemen_Mob: String = "",
        var servicemen_name: String = "",
        var servicemen_thumsdown: String = "",
        var servicemen_thumsup: String = "",
        var company_id: Int = 0
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
                "customer_id" to customer_id,
                "customer_name" to customer_name,
                "customer_image" to customer_image,
                "customer_mobile" to customer_mobile,
                "address" to address,
                "eta" to eta,
                "experience" to experience,
                "last_name" to last_name,
                "latitude" to latitude,
                "longitute" to longitute,
                "service_id" to service_id,
                "service_men_id" to service_men_id,
                "service_men_rating" to service_men_rating,
                "servicemen_image" to servicemen_image,
                "service_name" to service_name,
                "status" to status,
                "sub_service_id" to sub_service_id,
                "sub_service_name" to sub_service_name,
                "title" to title,
                "customer_appt" to customer_appt,
                "customer_floor" to customer_floor,
                "servicemen_lat" to servicemen_lat,
                "servicemen_long" to servicemen_long,
                "booking_id" to booking_id,
                "payment_status" to payment_status,
                "service_Type" to service_Type,
                "servicemen_Mob" to servicemen_Mob,
                "servicemen_name" to servicemen_name,
                "servicemen_thumsdown" to servicemen_thumsdown,
                "servicemen_thumsup" to servicemen_thumsup,
                "company_id" to company_id
        )
    }
}