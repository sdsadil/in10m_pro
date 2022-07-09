
package com.in10mServiceMan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CountryPojo {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private ArrayList<CountryPojoArray> countryPojoArray;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<CountryPojoArray> getCountryPojoArray() {
        return countryPojoArray;
    }

    public void setCountryPojoArray(ArrayList<CountryPojoArray> countryPojoArray) {
        this.countryPojoArray = countryPojoArray;
    }
}
