package com.in10mServiceMan.ui.activities.model_classes.service_subServices;

public class ServicemanExperienceDetails {

    private String user_id;
    private String service_id;
    private String subService_id;
    private String total_experience;
    private String status;

    public ServicemanExperienceDetails(String user_id, String service_id, String subService_id, String total_experience, String status) {
        this.user_id = user_id;
        this.service_id = service_id;
        this.subService_id = subService_id;
        this.total_experience = total_experience;
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getSubService_id() {
        return subService_id;
    }

    public void setSubService_id(String subService_id) {
        this.subService_id = subService_id;
    }

    public String getTotal_experience() {
        return total_experience;
    }

    public void setTotal_experience(String total_experience) {
        this.total_experience = total_experience;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
