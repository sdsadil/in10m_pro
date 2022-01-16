package com.in10mServiceMan.ui.activities.company_pros.active.active_API

class ActivePresenter(val view: IActiveView): IActiveInteractor, IActiveInteractorListener {

    var activeInteractor = ActiveInteractor(this)

    override fun onActiveCompleted(mPost: ActiveResponse) {
        view.onActiveCompleted(mPost)
    }

    override fun onActiveFailed(msg: String) {
        view.onActiveFailed(msg)
    }

    override fun active(mCompanyId: Int, mCrewStatus: Int) {
        activeInteractor.active(mCompanyId, mCrewStatus)
    }
}