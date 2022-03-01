package com.in10mServiceMan.ui.accound_edit.addService

interface IServiceView {
    fun onServiceCompleted(mPost: ServiceResponse)
    fun onServiceFailed(msg: String)
}

interface IServicePresenter {
    fun services(mServicemanId: Int, mService: String)
}

interface IServiceInteractor : IServicePresenter

interface IServiceInteractorListener : IServiceView