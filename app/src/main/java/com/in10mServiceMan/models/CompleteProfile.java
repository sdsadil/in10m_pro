
package com.in10mServiceMan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CompleteProfile {


    @SerializedName("free_estimate")
    @Expose
    private Integer free_estimate;
    @SerializedName("estimation_fee")
    @Expose
    private Double estimation_fee;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("adddress2")
    @Expose
    private String adddress2;
    @SerializedName("street_name")
    @Expose
    private String streetName;
    @SerializedName("pincode")
    @Expose
    private String pincode;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("total_tumbs_up")
    @Expose
    private Integer totalTumbsUp;
    @SerializedName("total_tumbs_down")
    @Expose
    private Integer totalTumbsDown;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("approved")
    @Expose
    private Integer approved;
    @SerializedName("wallet_balance")
    @Expose
    private String walletBalance;
    @SerializedName("civil_id")
    @Expose
    private String civilId;

    @SerializedName("working_as")
    @Expose
    private String workingAs;


    @SerializedName("experience")
    @Expose
    private String experience;

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getWorkingAs() {
        return workingAs;
    }

    public void setWorkingAs(String workingAs) {
        this.workingAs = workingAs;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAdddress2() {
        return adddress2;
    }

    public void setAdddress2(String adddress2) {
        this.adddress2 = adddress2;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Integer getTotalTumbsUp() {
        return totalTumbsUp;
    }

    public void setTotalTumbsUp(Integer totalTumbsUp) {
        this.totalTumbsUp = totalTumbsUp;
    }

    public Integer getTotalTumbsDown() {
        return totalTumbsDown;
    }

    public void setTotalTumbsDown(Integer totalTumbsDown) {
        this.totalTumbsDown = totalTumbsDown;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Integer getApproved() {
        return approved;
    }

    public void setApproved(Integer approved) {
        this.approved = approved;
    }

    public String getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(String walletBalance) {
        this.walletBalance = walletBalance;
    }

    public String getCivilId() {
        return civilId;
    }

    public void setCivilId(String civilId) {
        this.civilId = civilId;
    }
    public Integer getFree_estimate() {
        return free_estimate;
    }

    public void setFree_estimate(Integer free_estimate) {
        this.free_estimate = free_estimate;
    }

    public Double getEstimation_fee() {
        return estimation_fee;
    }

    public void setEstimation_fee(Double estimation_fee) {
        this.estimation_fee = estimation_fee;
    }

}
