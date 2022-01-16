package com.in10mServiceMan.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestUpdateUser {

    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("customer_lastname")
    @Expose
    private String customerLastname;
    @SerializedName("customer_email")
    @Expose
    private String customerEmail;
    @SerializedName("customer_country_code")
    @Expose
    private String customerCountryCode;
    @SerializedName("customer_mobile")
    @Expose
    private String customerMobile;
    @SerializedName("customer_address1")
    @Expose
    private String customerAddress1;
    @SerializedName("customer_adddress2")
    @Expose
    private String customerAdddress2;
    @SerializedName("customer_city")
    @Expose
    private String customerCity;
    @SerializedName("customer_state")
    @Expose
    private String customerState;
    @SerializedName("customer_country")
    @Expose
    private String customerCountry;
    @SerializedName("customer_latitude")
    @Expose
    private String customerLatitude;
    @SerializedName("customer_longitude")
    @Expose
    private String customerLongitude;
    @SerializedName("customer_image")
    @Expose
    private String customerImage;
    @SerializedName("customer_language")
    @Expose
    private String customerLanguage;

    @SerializedName("customer_street_name")
    @Expose
    private String customerStreetName;

    @SerializedName("customer_pincode")
    @Expose
    private String customerPinCode;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerLastname() {
        return customerLastname;
    }

    public void setCustomerLastname(String customerLastname) {
        this.customerLastname = customerLastname;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerCountryCode() {
        return customerCountryCode;
    }

    public void setCustomerCountryCode(String customerCountryCode) {
        this.customerCountryCode = customerCountryCode;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getCustomerAddress1() {
        return customerAddress1;
    }

    public void setCustomerAddress1(String customerAddress1) {
        this.customerAddress1 = customerAddress1;
    }

    public String getCustomerAdddress2() {
        return customerAdddress2;
    }

    public void setCustomerAdddress2(String customerAdddress2) {
        this.customerAdddress2 = customerAdddress2;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCustomerState() {
        return customerState;
    }

    public void setCustomerState(String customerState) {
        this.customerState = customerState;
    }

    public String getCustomerCountry() {
        return customerCountry;
    }

    public void setCustomerCountry(String customerCountry) {
        this.customerCountry = customerCountry;
    }

    public String getCustomerLatitude() {
        return customerLatitude;
    }

    public void setCustomerLatitude(String customerLatitude) {
        this.customerLatitude = customerLatitude;
    }

    public String getCustomerLongitude() {
        return customerLongitude;
    }

    public void setCustomerLongitude(String customerLongitude) {
        this.customerLongitude = customerLongitude;
    }

    public String getCustomerImage() {
        return customerImage;
    }

    public void setCustomerImage(String customerImage) {
        this.customerImage = customerImage;
    }

    public String getCustomerLanguage() {
        return customerLanguage;
    }

    public void setCustomerLanguage(String customerLanguage) {
        this.customerLanguage = customerLanguage;
    }

    public String getCustomerStreetName() {
        return customerStreetName;
    }

    public void setCustomerStreetName(String customerStreetName) {
        this.customerStreetName = customerStreetName;
    }

    public String getCustomerPinCode() {
        return customerPinCode;
    }

    public void setCustomerPinCode(String customerPinCode) {
        this.customerPinCode = customerPinCode;
    }

}