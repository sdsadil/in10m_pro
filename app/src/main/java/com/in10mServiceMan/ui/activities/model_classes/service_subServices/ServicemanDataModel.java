package com.in10mServiceMan.ui.activities.model_classes.service_subServices;

import java.util.ArrayList;

public class ServicemanDataModel {

    private ArrayList<ServicemanExperienceDetails> data;

    public ServicemanDataModel() {
    }

    public ServicemanDataModel(ArrayList<ServicemanExperienceDetails> data) {
        this.data = data;
    }

    public ArrayList<ServicemanExperienceDetails> getData() {
        return data;
    }

    public void setData(ArrayList<ServicemanExperienceDetails> data) {
        this.data = data;
    }
}
