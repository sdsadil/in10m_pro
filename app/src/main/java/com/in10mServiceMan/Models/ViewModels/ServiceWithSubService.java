package com.in10mServiceMan.Models.ViewModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.in10mServiceMan.Models.Service;
import com.in10mServiceMan.Models.SubService;

import java.util.List;

public class ServiceWithSubService {

    @SerializedName("service")
    @Expose
    private Service service;

    @SerializedName("subService")
    @Expose
    private List<SubService> subServices;

    @SerializedName("experience")
    @Expose
    private int experience;


    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public List<SubService> getSubServices() {
        return subServices;
    }

    public void setSubServices(List<SubService> subService) {
        this.subServices = subService;
    }

    public int getExperience(){
        return experience;
    }
    public void setExperience(int experience)
    {
        this.experience=experience;
    }


}
