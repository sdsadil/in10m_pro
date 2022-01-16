package com.in10mServiceMan.ui.activities.company_pros.serviceman_details_API

interface IServicemanDetailsView
{
    fun onServicemanDetailsCompleted(mPost: ServicemanDetailsResponse)
    fun onServicemanDetailsFailed(msg:String)
}

interface IServicemanDetailsPresenter
{
    fun servicemanDetails(mServicemanId: Int)
}

interface IServicemanDetailsInteractor: IServicemanDetailsPresenter

interface IServicemanDetailsInteractorListener: IServicemanDetailsView