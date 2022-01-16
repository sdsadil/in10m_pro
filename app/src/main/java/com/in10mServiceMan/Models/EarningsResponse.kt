package com.in10mServiceMan.Models

import com.google.gson.annotations.SerializedName


class EarningsResponse(
        @SerializedName("data")
        val `data`: EarningData? = EarningData(),
        @SerializedName("message")
        val message: String? = "",
        @SerializedName("status")
        val status: Int? = 0
)

data class EarningData(
        @SerializedName("cash_total_earning")
        val cash_total_earning: Double? = 0.0,
        @SerializedName("online_total_earning")
        val online_total_earning: Double? = 0.0,
        @SerializedName("total_in10m_outstanding")
        val total_in10m_outstanding: Double? = 0.0,
        @SerializedName("total_earnings")
        val total_earnings: Double? = 0.0,
        @SerializedName("online_total_customer_payment")
        val online_total_customer_payment: Double? = 0.0,
        @SerializedName("online_in10m_other_charges")
        val online_in10m_other_charges: Double? = 0.0,
        @SerializedName("cash_total_customer_payment")
        val cash_total_customer_payment: Double? = 0.0,
        @SerializedName("cash_in10m_other_charges")
        val cash_in10m_other_charges: Double? = 0.0
)