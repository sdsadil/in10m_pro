
package com.in10mServiceMan.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavoriteData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("date")
    @Expose
    private FavDate date;
    @SerializedName("serviceman_id")
    @Expose
    private Integer servicemanId;
    @SerializedName("serviceman_name")
    @Expose
    private String servicemanName;
    @SerializedName("service_image")
    @Expose
    private String serviceImage;
    @SerializedName("service")
    @Expose
    private List<ServiceDetail> service = null;
    @SerializedName("sub_service")
    @Expose
    private List<SubserviceDetail> subService = null;
    @SerializedName("thumbs_up")
    @Expose
    private Integer thumbsUp;
    @SerializedName("thumbs_down")
    @Expose
    private Integer thumbsDown;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FavDate getDate() {
        return date;
    }

    public void setDate(FavDate date) {
        this.date = date;
    }

    public Integer getServicemanId() {
        return servicemanId;
    }

    public void setServicemanId(Integer servicemanId) {
        this.servicemanId = servicemanId;
    }

    public String getServicemanName() {
        return servicemanName;
    }

    public void setServicemanName(String servicemanName) {
        this.servicemanName = servicemanName;
    }

    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    public List<ServiceDetail> getService() {
        return service;
    }

    public void setService(List<ServiceDetail> service) {
        this.service = service;
    }

    public List<SubserviceDetail> getSubService() {
        return subService;
    }

    public void setSubService(List<SubserviceDetail> subService) {
        this.subService = subService;
    }

    public Integer getThumbsUp() {
        return thumbsUp;
    }

    public void setThumbsUp(Integer thumbsUp) {
        this.thumbsUp = thumbsUp;
    }

    public Integer getThumbsDown() {
        return thumbsDown;
    }

    public void setThumbsDown(Integer thumbsDown) {
        this.thumbsDown = thumbsDown;
    }

}
