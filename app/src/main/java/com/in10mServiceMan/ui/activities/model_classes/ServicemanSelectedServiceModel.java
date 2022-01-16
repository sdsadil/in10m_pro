package com.in10mServiceMan.ui.activities.model_classes;

public class ServicemanSelectedServiceModel {

    private String service;
    private String subServices;
    private int totalExperience;

    public ServicemanSelectedServiceModel(String service, String subServices, int totalExperience) {
        this.service = service;
        this.subServices = subServices;
        this.totalExperience = totalExperience;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getSubServices() {
        return subServices;
    }

    public void setSubServices(String subServices) {
        this.subServices = subServices;
    }

    public int getTotalExperience() {
        return totalExperience;
    }

    public void setTotalExperience(int totalExperience) {
        this.totalExperience = totalExperience;
    }
}
