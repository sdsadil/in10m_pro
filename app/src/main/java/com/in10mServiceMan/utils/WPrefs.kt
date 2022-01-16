package com.in10mServiceMan.utils

object WPrefs {

    fun isLogin(): Boolean? = SharedPreferencesHelper.getBoolean(in10mApplication.instance,WKey.IS_LOGIN,false)

    fun setLogin(status: Boolean?) = SharedPreferencesHelper.putBoolean(in10mApplication.instance,WKey.IS_LOGIN,status)
}