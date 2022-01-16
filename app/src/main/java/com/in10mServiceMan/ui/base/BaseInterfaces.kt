package com.in10mServiceMan.ui.base

interface IBaseView {
    fun onSessionExpired()
    fun onSessionValid()
}

interface IBasePresenter {
    fun checkSession(header:String,userId:String)
}

interface IBaseInteractor : IBasePresenter

interface IBaseInteractorListener : IBaseView