
package com.in10mServiceMan.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CountData {

    @SerializedName("dateWise")
    @Expose
    private Integer dateWise;
    @SerializedName("monthWise")
    @Expose
    private Integer monthWise;
    @SerializedName("yearWise")
    @Expose
    private Integer yearWise;

    public Integer getDateWise() {
        return dateWise;
    }

    public void setDateWise(Integer dateWise) {
        this.dateWise = dateWise;
    }

    public Integer getMonthWise() {
        return monthWise;
    }

    public void setMonthWise(Integer monthWise) {
        this.monthWise = monthWise;
    }

    public Integer getYearWise() {
        return yearWise;
    }

    public void setYearWise(Integer yearWise) {
        this.yearWise = yearWise;
    }

}
