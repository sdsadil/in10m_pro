package com.in10mServiceMan.ui.activities.rating

import com.google.gson.annotations.SerializedName


data class ReviewsResponse(
        @SerializedName("data")
        val `data`: List<ReviewsData?>? = listOf(),
        @SerializedName("current_page")
        val currentPage: Int? = 0,
        @SerializedName("message")
        val message: String? = "",
        @SerializedName("status")
        val status: Int? = 0,
        @SerializedName("total")
        val total: Int? = 0
)

data class ReviewsData(
        @SerializedName("customer_image")
        val customerImage: String? = "",
        @SerializedName("customer_name")
        val customerName: String? = "",
        @SerializedName("description")
        val description: String? = null,
        @SerializedName("knowledge_rating")
        val knowledgeRating: Int? = 0,
        @SerializedName("overall_rating")
        val overallRating: Int? = 0,
        @SerializedName("price_rating")
        val priceRating: Int? = 0,
        @SerializedName("service_address")
        val serviceAddress: String? = "",
        @SerializedName("service_color")
        val serviceColor: String? = "",
        @SerializedName("service_date")
        val serviceDate: String? = "",
        @SerializedName("service_icon")
        val serviceIcon: String? = "",
        @SerializedName("service_name")
        val serviceName: String? = ""
)