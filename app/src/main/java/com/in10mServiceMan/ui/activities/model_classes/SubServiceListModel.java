package com.in10mServiceMan.ui.activities.model_classes;

public class SubServiceListModel {

    private String subSerivecesName;
    private boolean subServicesSelected;

    public SubServiceListModel(String subSerivecesName, boolean subServicesSelected) {
        this.subSerivecesName = subSerivecesName;
        this.subServicesSelected = subServicesSelected;
    }

    public String getSubSerivecesName() {
        return subSerivecesName;
    }

    public void setSubSerivecesName(String subSerivecesName) {
        this.subSerivecesName = subSerivecesName;
    }

    public boolean isSubServicesSelected() {
        return subServicesSelected;
    }

    public void setSubServicesSelected(boolean subServicesSelected) {
        this.subServicesSelected = subServicesSelected;
    }
}
