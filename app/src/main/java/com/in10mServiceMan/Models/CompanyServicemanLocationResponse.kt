package com.in10mServiceMan.Models
import com.google.gson.annotations.SerializedName


data class CompanyServicemanLocationResponse(
    @SerializedName("data")
    val `data`: List<ServicemanLocationData?>? = listOf(),
    @SerializedName("message")
    val message: String? = "",
    @SerializedName("status")
    val status: Int? = 0
)

data class ServicemanLocationData(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("image")
    val image: String? = "",
    @SerializedName("lastname")
    val lastname: String? = "",
    @SerializedName("latitude")
    val latitude: String? = "",
    @SerializedName("longitude")
    val longitude: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("services")
    val services: List<CSService?>? = listOf()
)

data class CSService(
    @SerializedName("service_id")
    val serviceId: Int? = 0,
    @SerializedName("service_name")
    val serviceName: String? = ""
)