package com.in10mServiceMan.models

import com.google.gson.annotations.SerializedName


data class CompanyHomeCountResponse(
        @SerializedName("data")
        val `data`: FinishedJobsData? = FinishedJobsData(),
        @SerializedName("message")
        val message: String? = "",
        @SerializedName("status")
        val status: Int? = 0
)

data class FinishedJobsData(
        @SerializedName("jobs_day")
        val jobsDay: Int? = 0,
        @SerializedName("jobs_month")
        val jobsMonth: Int? = 0,
        @SerializedName("jobs_year")
        val jobsYear: Int? = 0
)