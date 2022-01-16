package com.in10mServiceMan.ui.activities.company_pros.enable_API

import com.in10mServiceMan.ui.activities.company_pros.IAResponse

interface IEnableView
{
    fun onEnableCompleted(mPost: IAResponse)
    fun onEnableFailed(msg:String)
}

interface IEnablePresenter
{
    fun enable(mCompanyId: Int, mCompanyProId: Int)
}

interface IEnableInteractor: IEnablePresenter

interface IEnableInteractorListener: IEnableView