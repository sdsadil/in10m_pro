package com.in10mServiceMan.Models.ViewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.in10mServiceMan.Models.Booking;
import com.in10mServiceMan.Models.Service;
import com.in10mServiceMan.Models.ServiceMan;
import com.in10mServiceMan.Models.SubService;

public class ServiceSubServiceServiceManBooking {
    @SerializedName("service")
    @Expose
    private Service service;

    @SerializedName("subService")
    @Expose
    private SubService subService;

    @SerializedName("serviceMan")
    @Expose
    private ServiceMan serviceMan;

    @SerializedName("booking")
    @Expose
    private Booking booking;


    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public SubService getSubService() {
        return subService;
    }

    public void setSubService(SubService subService) {
        this.subService = subService;
    }

    public ServiceMan getServiceMan() {
        return serviceMan;
    }

    public void setServiceMan(ServiceMan serviceMan) {
        this.serviceMan = serviceMan;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
