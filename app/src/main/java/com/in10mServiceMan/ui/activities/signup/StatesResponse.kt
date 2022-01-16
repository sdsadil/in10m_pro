package com.in10mServiceMan.ui.activities.signup
import com.google.gson.annotations.SerializedName


data class StatesResponse(
    @SerializedName("data")
    val `data`: Data?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?
)

data class Data(
    @SerializedName("country_code")
    val countryCode: String?,
    @SerializedName("country_id")
    val countryId: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("phone_code")
    val phoneCode: Int?,
    @SerializedName("states")
    val states: List<State?>?
)

data class State(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("short")
    val short: String?
)