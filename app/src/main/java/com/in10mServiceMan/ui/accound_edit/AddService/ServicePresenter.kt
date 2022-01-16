package com.in10mServiceMan.ui.accound_edit.AddService

class ServicePresenter(val view: IServiceView): IServiceInteractor, IServiceInteractorListener {

    var serviceInteractor = ServiceInteractor(this)

    override fun onServiceCompleted(mPost: ServiceResponse) {
        view.onServiceCompleted(mPost)
    }

    override fun onServiceFailed(msg: String) {
        view.onServiceFailed(msg)
    }

    override fun services(mServicemanId: Int, mService: String) {
        serviceInteractor.services(mServicemanId, mService)
    }
}