
package com.in10mServiceMan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IncomingBookingRequestFirebaseModal {

    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("eta")
    @Expose
    private String eta;
    @SerializedName("experience")
    @Expose
    private String experience;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitute")
    @Expose
    private String longitute;
    @SerializedName("service_id")
    @Expose
    private String serviceId;
    @SerializedName("service_men_id")
    @Expose
    private String serviceMenId;
    @SerializedName("service_men_rating")
    @Expose
    private String serviceMenRating;
    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("sub_service_id")
    @Expose
    private String subServiceId;
    @SerializedName("sub_service_name")
    @Expose
    private String subServiceName;
    @SerializedName("title")
    @Expose
    private String title;

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

    public String getEta() {
        return eta;
    }

    public void setEta(String eta) {
        this.eta = eta;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitute() {
        return longitute;
    }

    public void setLongitute(String longitute) {
        this.longitute = longitute;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceMenId() {
        return serviceMenId;
    }

    public void setServiceMenId(String serviceMenId) {
        this.serviceMenId = serviceMenId;
    }

    public String getServiceMenRating() {
        return serviceMenRating;
    }

    public void setServiceMenRating(String serviceMenRating) {
        this.serviceMenRating = serviceMenRating;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubServiceId() {
        return subServiceId;
    }

    public void setSubServiceId(String subServiceId) {
        this.subServiceId = subServiceId;
    }

    public String getSubServiceName() {
        return subServiceName;
    }

    public void setSubServiceName(String subServiceName) {
        this.subServiceName = subServiceName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
