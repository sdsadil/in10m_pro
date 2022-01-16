package com.in10mServiceMan.ui.activities.payment

import com.google.firebase.database.PropertyName
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PaymentRequestClass {

    @SerializedName("customer_id")
    @Expose
    var customer_id: String? = null

    @SerializedName("service_man_id")
    @Expose
    var service_man_id: String? = null

    @SerializedName("cash_amount")
    @Expose
    var cash_amount: String? = null

    @SerializedName("payment_type")
    @Expose
    var payment_type: Int? = null

    @SerializedName("pay_status")
    @Expose
    var pay_status: Boolean? = null

    @SerializedName("booking_Id")
    @Expose
    var booking_Id: String? = null

    @SerializedName("tax")
    @Expose
    var tax: Double? = null

    @SerializedName("processingFee")
    @Expose
    var processingFee: Double? = null

    @SerializedName("tax_percent")
    @Expose
    var tax_percent: Double? = null

    @SerializedName("applicationFee")
    @Expose
    var applicationFee: Double? = null

    @SerializedName("fee_percent")
    @Expose
    var fee_percent: String? = null

    @SerializedName("accepted_status")
    @Expose
    var accepted_status: Boolean = false

    @SerializedName("editing_status")
    @Expose
    var editing_status: Boolean = false

    @SerializedName("service_amount")
    @Expose
    var service_amount: Double? = null

    @SerializedName("total_amount")
    @Expose
    var total_amount: String? = null

    /*fun getCustomer_Id(): String? {
        return customer_id
    }

    fun setCustomer_Id(customer_id: String?) {
        this.customer_id = customer_id
    }

    fun getService_man_id(): String? {
        return service_man_id
    }

    fun setService_man_id(service_man_id: String) {
        this.service_man_id = service_man_id
    }

    fun getCash_amount(): String? {
        return cash_amount
    }

    fun setCash_amount(cash_amount: String) {
        this.cash_amount = cash_amount
    }

    fun getPayment_type(): Int? {
        return payment_type
    }

    fun setPayment_type(payment_type: Int) {
        this.payment_type = payment_type
    }

    fun getPay_status(): Boolean? {
        return pay_status
    }

    fun setPay_status(pay_status: Boolean) {
        this.pay_status = pay_status
    }

    fun getBooking_Id(): String? {
        return booking_Id
    }

    fun setBooking_Id(booking_Id: String) {
        this.booking_Id = booking_Id
    }

    fun getTax(): Double? {
        return tax
    }

    fun setTax(tax: Double) {
        this.tax = tax
    }

    fun getProcessingFee(): Double? {
        return processingFee
    }

    fun setProcessingFee(processingFee: Double) {
        this.processingFee = processingFee
    }

    fun getTax_percent(): Double? {
        return tax_percent
    }

    fun setTax_percent(tax_percent: Double) {
        this.tax_percent = tax_percent
    }

    fun getApplicationFee(): Double? {
        return applicationFee
    }

    fun setApplicationFee(applicationFee: Double) {
        this.applicationFee = applicationFee
    }

    fun getFee_percent(): String? {
        return fee_percent
    }

    fun setFee_percent(fee_percent: String) {
        this.fee_percent = fee_percent
    }

    fun setIs_accpted(is_accepted: Boolean) {
        this.is_accepted = is_accepted
    }

    fun getIs_accepted(): Boolean? {
        return is_accepted
    }

    fun setIs_editing(is_editing: Boolean) {
        this.is_editing = is_editing
    }

    fun getIs_editing(): Boolean? {
        return is_editing
    }

    fun setService_fee(service_fee: String) {
        this.service_fee = service_fee
    }

    fun getService_fee(): String? {
        return service_fee
    }

    fun setTotal_amount(total_amount: String) {
        this.total_amount = total_amount
    }

    fun getTotal_amount(): String? {
        return total_amount
    }*/
}