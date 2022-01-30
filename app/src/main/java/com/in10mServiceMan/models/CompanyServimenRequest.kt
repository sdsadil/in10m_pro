package com.in10mServiceMan.models

import com.google.gson.annotations.SerializedName


data class CompanyServimenRequest(
        @SerializedName("service_id")
        val serviceId: Int?,
        @SerializedName("users")
        val users: List<User?>?
)

data class User(
        @SerializedName("email")
        var email: String?,
        @SerializedName("experience")
        var experience: Int?,
        @SerializedName("mobile")
        var mobile: String?,
        @SerializedName("name")
        var name: String?
) {
    constructor() : this("", 0, "", "")
}