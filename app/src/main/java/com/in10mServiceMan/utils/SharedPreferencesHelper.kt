package com.in10mServiceMan.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by Rohit on 05/10/17.
 */

object SharedPreferencesHelper {
    private const val userData = "userdata"
    private const val fcmData = "fcmData"
    private const val fcmDeviceToken = "fcmDeviceToken"

    fun putInt(mContext: Context?, key: String, value: Int) {
        val preferences = PreferenceManager
            .getDefaultSharedPreferences(mContext)
        val edit = preferences.edit()
        edit.putInt(key, value)
        //edit.clear();
        edit.apply()
        /*val editor: SharedPreferences.Editor =
            mContext?.getSharedPreferences(userData, Context.MODE_PRIVATE)!!.edit()
        editor.putInt(key, value)
        editor.apply()*/
    }

    fun getInt(mContext: Context?, key: String, _default: Int): Int {
        val preferences = PreferenceManager
            .getDefaultSharedPreferences(mContext)
        return preferences.getInt(key, _default)

        /*val prefs: SharedPreferences =
            mContext!!.getSharedPreferences(userData, Context.MODE_PRIVATE)
        return prefs.getInt(key, _default)*/
    }

    fun putBoolean(mContext: Context?, key: String, value: Boolean?) {
        val preferences = PreferenceManager
            .getDefaultSharedPreferences(mContext)
        val edit = preferences.edit()
        edit.putBoolean(key, value!!)
        //edit.clear();
        edit.apply()
    }

    fun putString(mContext: Context?, key: String, value: String) {
        val preferences = PreferenceManager
            .getDefaultSharedPreferences(mContext)
        val edit = preferences.edit()
        edit.putString(key, value)
        //edit.clear();
        edit.apply()

    }

    fun putFloat(mContext: Context?, key: String, value: Float) {
        val preferences = PreferenceManager
            .getDefaultSharedPreferences(mContext)
        val edit = preferences.edit()
        edit.putFloat(key, value)
        //edit.clear();
        edit.apply()
    }

    fun putLong(mContext: Context?, key: String, value: Long) {
        val preferences = PreferenceManager
            .getDefaultSharedPreferences(mContext)
        val edit = preferences.edit()
        edit.putLong(key, value)
        //edit.clear();
        edit.apply()
    }

    fun getLong(mContext: Context?, key: String, _default: Long): Long {
        val preferences = PreferenceManager
            .getDefaultSharedPreferences(mContext)
        return preferences.getLong(key, _default)
    }

    fun getFloat(mContext: Context?, key: String, _default: Float): Float {
        val preferences = PreferenceManager
            .getDefaultSharedPreferences(mContext)
        return preferences.getFloat(key, _default)
    }

    fun getString(mContext: Context?, key: String, _default: String): String? {
        val preferences = PreferenceManager
            .getDefaultSharedPreferences(mContext)
        return preferences.getString(key, _default)
    }


    fun getBoolean(mContext: Context?, key: String, _default: Boolean?): Boolean? {
        val preferences = PreferenceManager
            .getDefaultSharedPreferences(mContext)
        return preferences.getBoolean(key, _default!!)
    }

    fun setfcmDeviceToken(context: Context?, deviceToken: String?) {
        val editor: SharedPreferences.Editor =
            context!!.getSharedPreferences(fcmData, Context.MODE_PRIVATE).edit()
        editor.putString(fcmDeviceToken, deviceToken)
        editor.apply()
    }

    fun getfcmDeviceToken(context: Context?): String? {
        val prefs: SharedPreferences = context!!.getSharedPreferences(fcmData, Context.MODE_PRIVATE)
        return prefs.getString(fcmDeviceToken, "")
    }


    fun clearPreferences(mContext: Context?) {
        val preferences = PreferenceManager
            .getDefaultSharedPreferences(mContext)

        preferences.edit().clear().apply()
    }
}