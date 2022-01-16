
package com.in10mServiceMan.Models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestRemoveSubServicesModel {

    @SerializedName("serviceman_id")
    @Expose
    private Integer servicemanId;
    @SerializedName("sub_services")
    @Expose
    private List<Integer> subServices = null;

    public Integer getServicemanId() {
        return servicemanId;
    }

    public void setServicemanId(Integer servicemanId) {
        this.servicemanId = servicemanId;
    }

    public List<Integer> getSubServices() {
        return subServices;
    }

    public void setSubServices(List<Integer> subServices) {
        this.subServices = subServices;
    }

}
