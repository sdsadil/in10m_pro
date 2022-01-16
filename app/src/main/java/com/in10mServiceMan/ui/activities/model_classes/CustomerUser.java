
package com.in10mServiceMan.ui.activities.model_classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerUser {


    private Integer customer_id;

    private String customer_mobile;

    private String customer_mobile_code;

    private String customer_name;

    private Integer customer_type;

    private Integer isRegistered;

    private Integer otp;

    public CustomerUser(Integer customer_id, String customer_mobile, String customer_mobile_code, String customer_name, Integer customer_type, Integer isRegistered, Integer otp) {
        this.customer_id = customer_id;
        this.customer_mobile = customer_mobile;
        this.customer_mobile_code = customer_mobile_code;
        this.customer_name = customer_name;
        this.customer_type = customer_type;
        this.isRegistered = isRegistered;
        this.otp = otp;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_mobile() {
        return customer_mobile;
    }

    public void setCustomer_mobile(String customer_mobile) {
        this.customer_mobile = customer_mobile;
    }

    public String getCustomer_mobile_code() {
        return customer_mobile_code;
    }

    public void setCustomer_mobile_code(String customer_mobile_code) {
        this.customer_mobile_code = customer_mobile_code;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public Integer getCustomer_type() {
        return customer_type;
    }

    public void setCustomer_type(Integer customer_type) {
        this.customer_type = customer_type;
    }

    public Integer getIsRegistered() {
        return isRegistered;
    }

    public void setIsRegistered(Integer isRegistered) {
        this.isRegistered = isRegistered;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }
}
