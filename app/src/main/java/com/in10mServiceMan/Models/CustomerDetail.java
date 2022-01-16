
package com.in10mServiceMan.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerDetail {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lastname")
    @Expose
    private Object lastname;
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
    private Object address1;
    @SerializedName("adddress2")
    @Expose
    private Object adddress2;
    @SerializedName("street_name")
    @Expose
    private Object streetName;
    @SerializedName("pincode")
    @Expose
    private Object pincode;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("state")
    @Expose
    private Object state;
    @SerializedName("country")
    @Expose
    private Object country;
    @SerializedName("latitude")
    @Expose
    private Object latitude;
    @SerializedName("longitude")
    @Expose
    private Object longitude;
    @SerializedName("image")
    @Expose
    private Object image;
    @SerializedName("rating")
    @Expose
    private Double rating;
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
    private Object language;
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

    public Object getLastname() {
        return lastname;
    }

    public void setLastname(Object lastname) {
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

    public Object getAddress1() {
        return address1;
    }

    public void setAddress1(Object address1) {
        this.address1 = address1;
    }

    public Object getAdddress2() {
        return adddress2;
    }

    public void setAdddress2(Object adddress2) {
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

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }

    public Object getLatitude() {
        return latitude;
    }

    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    public Object getLongitude() {
        return longitude;
    }

    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
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

    public Object getLanguage() {
        return language;
    }

    public void setLanguage(Object language) {
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

    @Override
    public String toString() {
        return "CustomerDetail{" +
                "address1=" + address1 +
                ", adddress2=" + adddress2 +
                '}';
    }
}
