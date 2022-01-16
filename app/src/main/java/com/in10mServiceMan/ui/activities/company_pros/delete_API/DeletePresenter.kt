package com.in10mServiceMan.ui.activities.company_pros.delete_API

import com.in10mServiceMan.ui.activities.company_pros.IAResponse

class DeletePresenter(val view: IDeleteView): IDeleteInteractor, IDeleteInteractorListener {

    var deleteInteractor = DeleteInteractor(this)

    override fun onDeleteCompleted(mPost: IAResponse) {
        view.onDeleteCompleted(mPost)
    }

    override fun onDeleteFailed(msg: String) {
        view.onDeleteFailed(msg)
    }

    override fun delete(mCompanyId: Int, mCompanyProId: Int) {
        deleteInteractor.delete(mCompanyId, mCompanyProId)
    }
}