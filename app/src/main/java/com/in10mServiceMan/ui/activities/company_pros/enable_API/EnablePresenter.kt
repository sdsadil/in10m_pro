package com.in10mServiceMan.ui.activities.company_pros.enable_API

import com.in10mServiceMan.ui.activities.company_pros.IAResponse

class EnablePresenter(val view: IEnableView): IEnableInteractor, IEnableInteractorListener {

    var enableInteractor = EnableInteractor(this)

    override fun onEnableCompleted(mPost: IAResponse) {
        view.onEnableCompleted(mPost)
    }

    override fun onEnableFailed(msg: String) {
        view.onEnableFailed(msg)
    }

    override fun enable(mCompanyId: Int, mCompanyProId: Int) {
        enableInteractor.enable(mCompanyId, mCompanyProId)
    }
}