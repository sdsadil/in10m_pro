package com.in10mServiceMan.ui.activities.model_classes;

public class ServicesListModel {

    private String serviceName;

    private String serviceIcon;

    public ServicesListModel(String serviceName, String serviceIcon) {
        this.serviceName = serviceName;
        this.serviceIcon = serviceIcon;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceIcon() {
        return serviceIcon;
    }

    public void setServiceIcon(String serviceIcon) {
        this.serviceIcon = serviceIcon;
    }
}
