
package com.in10mServiceMan.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateServemanWorkingStatus {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("working_status")
    @Expose
    private String workingStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWorkingStatus() {
        return workingStatus;
    }

    public void setWorkingStatus(String workingStatus) {
        this.workingStatus = workingStatus;
    }

}
