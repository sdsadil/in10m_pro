package com.in10mServiceMan.models.viewmodels;

import java.util.ArrayList;
import java.util.List;

public class CompanyServices {

    private Integer service_id;
    private String serviceName;
    private String serviceColor;

    private List<CompanyServiceman> users;

    public CompanyServices(Integer serviceId, String serviceName, String serviceColor) {
        this.service_id = serviceId;
        this.serviceName = serviceName;
        this.serviceColor = serviceColor;
        this.users = new ArrayList<>();
    }

    public Integer getService_id() {
        return service_id;
    }

    public void setService_id(Integer service_id) {
        this.service_id = service_id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceColor() {
        return serviceColor;
    }

    public void setServiceColor(String serviceColor) {
        this.serviceColor = serviceColor;
    }

    public List<CompanyServiceman> getUsers() {
        return users;
    }

    public void setUsers(List<CompanyServiceman> users) {
        this.users = users;
    }
}
