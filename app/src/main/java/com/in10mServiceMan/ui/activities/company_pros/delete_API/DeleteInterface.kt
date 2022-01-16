package com.in10mServiceMan.ui.activities.company_pros.delete_API

import com.in10mServiceMan.ui.activities.company_pros.IAResponse

interface IDeleteView
{
    fun onDeleteCompleted(mPost: IAResponse)
    fun onDeleteFailed(msg:String)
}

interface IDeletePresenter
{
    fun delete(mCompanyId: Int, mCompanyProId: Int)
}

interface IDeleteInteractor: IDeletePresenter

interface IDeleteInteractorListener: IDeleteView