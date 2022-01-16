package com.in10mServiceMan.ui.activities.services

import com.in10mServiceMan.Models.HomeService

interface IServicesView
{
    fun onServiceCompleted(mData: HomeService)
    fun onFailed(msg:String)
}

interface IServicesPresenter
{
    fun getServices()
}

interface IServicesInteractor: IServicesPresenter


interface IServicesInteractorListener: IServicesView
