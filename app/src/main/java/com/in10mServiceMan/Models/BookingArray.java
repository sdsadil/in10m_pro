
package com.in10mServiceMan.Models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingArray {

    @SerializedName("booking_id")
    @Expose
    private Integer bookingId = 0;
    @SerializedName("latitude")
    @Expose
    private String latitude = null;
    @SerializedName("longitude")
    @Expose
    private String longitude = null;
    @SerializedName("polyline")
    @Expose
    private String polyline = null;
    @SerializedName("service_name")
    @Expose
    private String serviceName = null;
    @SerializedName("service_code")
    @Expose
    private String serviceCode = null;
    @SerializedName("service_mobile")
    @Expose
    private String serviceMobile = null;
    @SerializedName("service_email")
    @Expose
    private String serviceEmail = null;
    @SerializedName("service_date")
    @Expose
    private String serviceDate = null;
    @SerializedName("service_time")
    @Expose
    private String serviceTime = null;
    @SerializedName("status")
    @Expose
    private Integer status = 0;
    @SerializedName("customerDetails")
    @Expose
    private List<CustomerDetail> customerDetails = null;
    @SerializedName("serviceDetails")
    @Expose
    private List<ServiceDetail> serviceDetails = null;
    @SerializedName("subserviceDetails")
    @Expose
    private List<SubserviceDetail> subserviceDetails = null;

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<CustomerDetail> getCustomerDetails() {
        return customerDetails;
    }

    public void setCustomerDetails(List<CustomerDetail> customerDetails) {
        this.customerDetails = customerDetails;
    }

    public List<ServiceDetail> getServiceDetails() {
        return serviceDetails;
    }

    public void setServiceDetails(List<ServiceDetail> serviceDetails) {
        this.serviceDetails = serviceDetails;
    }

    public List<SubserviceDetail> getSubserviceDetails() {
        return subserviceDetails;
    }

    public void setSubserviceDetails(List<SubserviceDetail> subserviceDetails) {
        this.subserviceDetails = subserviceDetails;
    }

}
