package com.in10mServiceMan.ui.activities.company_pros.disable_API

import com.in10mServiceMan.ui.activities.company_pros.IAResponse

class DisablePresenter(val view: IDisableView): IDisableInteractor, IDisableInteractorListener {

    var disableInteractor = DisableInteractor(this)

    override fun onDisableCompleted(mPost: IAResponse) {
        view.onDisableCompleted(mPost)
    }

    override fun onDisableFailed(msg: String) {
        view.onDisableFailed(msg)
    }

    override fun disable(mCompanyId: Int, mCompanyProId: Int) {
        disableInteractor.disable(mCompanyId, mCompanyProId)
    }
}