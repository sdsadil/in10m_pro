
package com.in10mServiceMan.ui.activities.model_classes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestVerifyMobile {


    private String mobile;

    private String code;

    public RequestVerifyMobile(String mobile, String code) {
        this.mobile = mobile;
        this.code = code;
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

}
