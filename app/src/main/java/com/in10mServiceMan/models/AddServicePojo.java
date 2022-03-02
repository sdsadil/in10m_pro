package com.in10mServiceMan.models;

public class AddServicePojo {
    String certificate;
    String service_id;
    String total_experience;

    public AddServicePojo() {
    }

    public AddServicePojo(String certificate, String service_id, String total_experience) {
        this.certificate = certificate;
        this.service_id = service_id;
        this.total_experience = total_experience;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getTotal_experience() {
        return total_experience;
    }

    public void setTotal_experience(String total_experience) {
        this.total_experience = total_experience;
    }
}
