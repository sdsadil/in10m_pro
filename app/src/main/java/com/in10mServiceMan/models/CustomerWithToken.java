
package com.in10mServiceMan.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerWithToken {

    @SerializedName("serviceProvider_data")
    @Expose
    private List<CustomerUser> customerData = null;
    @SerializedName("api_token")
    @Expose
    private String apiToken;

    public List<CustomerUser> getCustomerData() {
        return customerData;
    }

    public void setCustomerData(List<CustomerUser> customerData) {
        this.customerData = customerData;
    }

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

}
