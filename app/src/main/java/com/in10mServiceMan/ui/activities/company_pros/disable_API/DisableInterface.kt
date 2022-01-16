package com.in10mServiceMan.ui.activities.company_pros.disable_API

import com.in10mServiceMan.ui.activities.company_pros.IAResponse

interface IDisableView
{
    fun onDisableCompleted(mPost: IAResponse)
    fun onDisableFailed(msg:String)
}

interface IDisablePresenter
{
    fun disable(mCompanyId: Int, mCompanyProId: Int)
}

interface IDisableInteractor: IDisablePresenter

interface IDisableInteractorListener: IDisableView