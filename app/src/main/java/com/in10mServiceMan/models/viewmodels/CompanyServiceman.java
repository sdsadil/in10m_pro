package com.in10mServiceMan.models.viewmodels;

public class CompanyServiceman {
    private String name;
    private String mobile;
    private String experience;
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public CompanyServiceman(String fullName, String mobileNumber, String yearOfExperience, String email, Integer perentPosition) {
        this.name = fullName;
        this.mobile = mobileNumber;
        this.experience = yearOfExperience;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
