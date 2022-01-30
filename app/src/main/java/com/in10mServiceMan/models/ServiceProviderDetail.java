
package com.in10mServiceMan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceProviderDetail {

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
    private Object streetName;
    @SerializedName("pincode")
    @Expose
    private Object pincode;
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
    private Integer rating;
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
    private Object walletBalance;
    @SerializedName("civil_id")
    @Expose
    private String civilId;

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

    public Object getStreetName() {
        return streetName;
    }

    public void setStreetName(Object streetName) {
        this.streetName = streetName;
    }

    public Object getPincode() {
        return pincode;
    }

    public void setPincode(Object pincode) {
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
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

    public Object getWalletBalance() {
        return walletBalance;
    }

    public void setWalletBalance(Object walletBalance) {
        this.walletBalance = walletBalance;
    }

    public String getCivilId() {
        return civilId;
    }

    public void setCivilId(String civilId) {
        this.civilId = civilId;
    }

}
