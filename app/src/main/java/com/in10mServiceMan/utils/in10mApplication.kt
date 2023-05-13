package com.in10mServiceMan.utils

import android.annotation.SuppressLint
import android.app.Application
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.FirebaseDatabase
//import com.onesignal.OSNotificationOpenedResult
//import com.onesignal.OneSignal

/**
 * Created by Rohit on 25/03/18.
 */
class in10mApplication : Application() {

    companion object {
        var instance: in10mApplication? = in10mApplication()
    }

    @SuppressLint("MissingPermission")
    override fun onCreate() {
        super.onCreate()
        instance = this
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseAnalytics.getInstance(this)
        initAmazonLogging()
        initOneSignal()
    }

    private fun initAmazonLogging() {
        java.util.logging.Logger.getLogger("com.amazonaws").level = java.util.logging.Level.FINEST;
    }

    private fun initOneSignal() {
      /*  OneSignal.startInit(this)
            .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
            .unsubscribeWhenNotificationsAreDisabled(true)
            .setNotificationOpenedHandler(CustomNotificationOpenHandler())
            .init()*/

//        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
//        OneSignal.initWithContext(this);
//        OneSignal.setAppId("6dce5497-7a9e-4414-9824-a21aa65c304f");
    }

//    class CustomNotificationOpenHandler : OneSignal.NotificationOpenedHandler {
//        override fun notificationOpened(result: OSNotificationOpenResult?) {

//    class CustomNotificationOpenHandler : OneSignal.OSNotificationOpenedHandler {
//        override fun notificationOpened(result: OSNotificationOpenedResult?) {
//            if (result != null) {
//                /*val data = result.notification.payload.additionalData
//                if (data != null) {
//                    var mType = data.optString("type", "1")
//                    var mCustomerID = data.optString("customer_id", "")
//                    var mDriverID = data.optString("driver_id", "")
//                    var mShipmentID = data.optString("shipment_id", "")
//                    RedirectionController(mShipmentID, mCustomerID, mDriverID, mType).initRedirection()
//                }*/
//            }
//        }
//
//    }
}