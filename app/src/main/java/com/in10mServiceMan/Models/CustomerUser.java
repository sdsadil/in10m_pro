
package com.in10mServiceMan.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomerUser {

    @SerializedName("serviceProvider_id")
    @Expose
    private Integer customerId;
    @SerializedName("serviceProvider_mobile")
    @Expose
    private String customerMobile;
    @SerializedName("serviceProvider_mobile_code")
    @Expose
    private String customerMobileCode;
    @SerializedName("serviceProvider_name")
    @Expose
    private String customerName;
    @SerializedName("is_registered")
    @Expose
    private Integer isRegistered;
    @SerializedName("otp")
    @Expose
    private Integer otp;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getCustomerMobileCode() {
        return customerMobileCode;
    }

    public void setCustomerMobileCode(String customerMobileCode) {
        this.customerMobileCode = customerMobileCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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
