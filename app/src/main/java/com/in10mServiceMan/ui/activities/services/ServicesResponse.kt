package com.in10mServiceMan.ui.activities.services

import com.google.gson.annotations.SerializedName


data class ServicesResponse(
        @SerializedName("data")
        val `data`: List<ServiceData?>?,
        @SerializedName("message")
        val message: String?,
        @SerializedName("status")
        val status: Int?
)

data class ServiceData(
        @SerializedName("certification")
        val certification: Int?=0,
        @SerializedName("experience")
        var experience: Int?=0,
        @SerializedName("service_ar_name")
        val serviceArName: String?="",
        @SerializedName("ar_name")
        val arName: String?="",
        @SerializedName("service_color")
        val serviceColor: String?="",
        @SerializedName("service_icon")
        val serviceIcon: String?="",
        @SerializedName("service_id")
        val serviceId: Int?=0,
        @SerializedName("service_name")
        val serviceName: String?="",
        @SerializedName("service_status")
        val serviceStatus: Int?=0,
        @SerializedName("sub_service")
        val subService: List<Any?>?
)