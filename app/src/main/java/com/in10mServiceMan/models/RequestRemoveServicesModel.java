
package com.in10mServiceMan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestRemoveServicesModel {

    @SerializedName("serviceman_id")
    @Expose
    private Integer servicemanId;
    @SerializedName("service_id")
    @Expose
    private Integer service_id;

    public Integer getServicemanId() {
        return servicemanId;
    }

    public void setServicemanId(Integer servicemanId) {
        this.servicemanId = servicemanId;
    }

    public Integer getService_id() {
        return service_id;
    }

    public void setService_id(Integer service_id) {
        this.service_id = service_id;
    }
}
