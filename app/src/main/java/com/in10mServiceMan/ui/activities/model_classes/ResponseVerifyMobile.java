
package com.in10mServiceMan.ui.activities.model_classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ResponseVerifyMobile {


    private Integer status;


    private String error;


    private String message;

    private ArrayList<CustomerWithToken> data = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<CustomerWithToken> getData() {
        return data;
    }

    public void setData(ArrayList<CustomerWithToken> data) {
        this.data = data;
    }

}
