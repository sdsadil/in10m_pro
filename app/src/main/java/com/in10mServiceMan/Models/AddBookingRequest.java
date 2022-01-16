
package com.in10mServiceMan.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddBookingRequest {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("subservice_id")
    @Expose
    private String subserviceId;
    @SerializedName("service_provider_id")
    @Expose
    private String serviceProviderId;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("servicing_address")
    @Expose
    private String servicingAddress;
    @SerializedName("polyline")
    @Expose
    private String polyline;
    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("service_code")
    @Expose
    private String serviceCode;
    @SerializedName("service_mobile")
    @Expose
    private String serviceMobile;
    @SerializedName("service_email")
    @Expose
    private String serviceEmail;
    @SerializedName("service_date")
    @Expose
    private String serviceDate;
    @SerializedName("service_time")
    @Expose
    private String serviceTime;
    @SerializedName("status")
    @Expose
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getSubserviceId() {
        return subserviceId;
    }

    public void setSubserviceId(String subserviceId) {
        this.subserviceId = subserviceId;
    }

    public String getServiceProviderId() {
        return serviceProviderId;
    }

    public void setServiceProviderId(String serviceProviderId) {
        this.serviceProviderId = serviceProviderId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getServicingAddress() {
        return servicingAddress;
    }

    public void setServicingAddress(String servicingAddress) {
        this.servicingAddress = servicingAddress;
    }

    public String getPolyline() {
        return polyline;
    }

    public void setPolyline(String polyline) {
        this.polyline = polyline;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceMobile() {
        return serviceMobile;
    }

    public void setServiceMobile(String serviceMobile) {
        this.serviceMobile = serviceMobile;
    }

    public String getServiceEmail() {
        return serviceEmail;
    }

    public void setServiceEmail(String serviceEmail) {
        this.serviceEmail = serviceEmail;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(String serviceTime) {
        this.serviceTime = serviceTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
