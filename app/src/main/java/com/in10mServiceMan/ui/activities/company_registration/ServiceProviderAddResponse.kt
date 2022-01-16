package com.in10mServiceMan.ui.activities.company_registration
import com.google.gson.annotations.SerializedName


data class ServiceProviderAddResponse(
    @SerializedName("data")
    val `data`: Data? = Data(),
    @SerializedName("message")
    val message: String? = "",
    @SerializedName("status")
    val status: Int? = 0
)

data class Data(
    @SerializedName("company_id")
    val companyId: Int? = 0,
    @SerializedName("company_name")
    val companyName: String? = "",
    @SerializedName("email")
    val email: String? = "",
    @SerializedName("image")
    val image: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("service_providers")
    val serviceProviders: List<Any?>? = listOf(),
    @SerializedName("user_id")
    val userId: Int? = 0
)