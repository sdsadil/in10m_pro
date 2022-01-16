package com.in10mServiceMan.ui.activities.company_pros.serviceman_details_API

class ServicemanDetailsPresenter(val view: IServicemanDetailsView): IServicemanDetailsInteractor, IServicemanDetailsInteractorListener {

    var servicemanDetailsInteractor = ServicemanDetailsInteractor(this)

    override fun onServicemanDetailsCompleted(mPost: ServicemanDetailsResponse) {
        view.onServicemanDetailsCompleted(mPost)
    }

    override fun onServicemanDetailsFailed(msg: String) {
        view.onServicemanDetailsFailed(msg)
    }

    override fun servicemanDetails(mServicemanId: Int) {
        servicemanDetailsInteractor.servicemanDetails(mServicemanId)
    }
}