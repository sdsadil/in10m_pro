package com.in10mServiceMan.ui.activities.services

import com.in10mServiceMan.Models.HomeService

class ServicesPresenter(val view:IServicesView): IServicesInteractor, IServicesInteractorListener {
    override fun onServiceCompleted(mData: HomeService) {
       view.onServiceCompleted(mData)
    }

    override fun onFailed(msg: String) {
       view.onFailed(msg)
    }

    override fun getServices() {
        mInteractor.getServices()
    }
    val mInteractor=ServicesInteractor(this)
}