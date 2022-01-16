package com.in10mServiceMan.ui.activities.model_classes.service_subServices;

import java.util.ArrayList;

public class ServicemanBodyModel {

    ServicemanDataModel BODY;

    public ServicemanBodyModel(ServicemanDataModel BODY) {
        this.BODY = BODY;
    }

    public ServicemanBodyModel() {
    }

    public ServicemanDataModel getBODY() {
        return BODY;
    }

    public void setBODY(ServicemanDataModel BODY) {
        this.BODY = BODY;
    }
}
