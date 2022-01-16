package com.in10mServiceMan.ui.activities.company_pros.active.active_API

interface IActiveView
{
    fun onActiveCompleted(mPost: ActiveResponse)
    fun onActiveFailed(msg:String)
}

interface IActivePresenter
{
    fun active(mCompanyId: Int, mCrewStatus: Int)
}

interface IActiveInteractor: IActivePresenter

interface IActiveInteractorListener: IActiveView