
package com.in10mServiceMan.ui.activities.model_classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestVerifyOTP {


    private String mobile;

    private String code;

    private String otp;

    private String name;


    public RequestVerifyOTP(String mobile, String code, String otp, String name) {
        this.mobile = mobile;
        this.code = code;
        this.otp = otp;
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
