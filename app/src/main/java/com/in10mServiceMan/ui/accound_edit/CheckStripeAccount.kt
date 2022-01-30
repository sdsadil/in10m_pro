package com.in10mServiceMan.ui.accound_edit

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CheckStripeAccount {
    @SerializedName("status")
    @Expose
    private var status: Int? = null
    @SerializedName("message")
    @Expose
    private var message: String? = null
    @SerializedName("stripe_account")
    @Expose
    private var stripe_account: String? = null

    fun stripeAccount(): String {
        return stripe_account!!
    }
}