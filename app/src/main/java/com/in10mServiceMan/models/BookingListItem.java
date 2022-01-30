
package com.in10mServiceMan.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingListItem {

    @SerializedName("booking_id")
    @Expose
    private Integer bookingId;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("polyline")
    @Expose
    private Object polyline;
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
    private Object serviceEmail;
    @SerializedName("service_date")
    @Expose
    private String serviceDate;
    @SerializedName("service_time")
    @Expose
    private String serviceTime;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("serviceDetails")
    @Expose
    private List<ServiceDetail> serviceDetails = null;
    @SerializedName("subserviceDetails")
    @Expose
    private List<SubserviceDetail> subserviceDetails = null;
    @SerializedName("serviceProviderDetails")
    @Expose
    private List<ServiceProviderDetail> serviceProviderDetails = null;

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

    public Object getPolyline() {
        return polyline;
    }

    public void setPolyline(Object polyline) {
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

    public Object getServiceEmail() {
        return serviceEmail;
    }

    public void setServiceEmail(Object serviceEmail) {
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

    public List<ServiceProviderDetail> getServiceProviderDetails() {
        return serviceProviderDetails;
    }

    public void setServiceProviderDetails(List<ServiceProviderDetail> serviceProviderDetails) {
        this.serviceProviderDetails = serviceProviderDetails;
    }

}
