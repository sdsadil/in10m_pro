
package com.in10mServiceMan.ui.activities.model_classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CustomerWithToken {


    private ArrayList<CustomerUser> customer_data = null;

    private String api_token;

    public ArrayList<CustomerUser> getCustomer_data() {
        return customer_data;
    }

    public void setCustomer_data(ArrayList<CustomerUser> customer_data) {
        this.customer_data = customer_data;
    }

    public String getApi_token() {
        return api_token;
    }

    public void setApi_token(String api_token) {
        this.api_token = api_token;
    }

    public ArrayList<CustomerUser> getCustomerData() {
        return customer_data;
    }

    public void setCustomerData(ArrayList<CustomerUser> customer_data) {
        this.customer_data = customer_data;
    }

    public String getApiToken() {
        return api_token;
    }

    public void setApiToken(String api_token) {
        this.api_token = api_token;
    }

}
