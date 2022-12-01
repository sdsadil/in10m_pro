package com.in10mServiceMan.ui.listener;

import com.in10mServiceMan.models.viewmodels.ServiceWithSubService;
import com.in10mServiceMan.ui.activities.services.ServiceData;

public interface EditSubServicesListener {
    void onEditClick(int position, ServiceWithSubService serviceWithSubService);
    void onDeleteClick(int position, ServiceData serviceData);
}
