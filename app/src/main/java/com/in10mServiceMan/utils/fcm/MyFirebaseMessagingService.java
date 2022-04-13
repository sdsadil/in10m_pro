package com.in10mServiceMan.utils.fcm;

import static androidx.core.app.NotificationCompat.PRIORITY_MAX;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.in10mServiceMan.R;
import com.in10mServiceMan.ui.activities.dashboard.DashboardActivity;
import com.in10mServiceMan.utils.Constants;
import com.in10mServiceMan.utils.SharedPreferencesHelper;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Random;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = MyFirebaseMessagingService.class.getName();
    private NotificationManager notificationManager;


    @Override
    public void onNewToken(@NotNull String deviceToken) {
        super.onNewToken(deviceToken);
        @SuppressLint("HardwareIds")
        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        SharedPreferencesHelper.INSTANCE.setfcmDeviceToken(this, deviceToken);
        Log.d(TAG, "deviceToken " + deviceToken);
        Log.d(TAG, "deviceId " + deviceId);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived: " + remoteMessage);
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            sendNotification(remoteMessage.getData().get("body"));
        } else {
            Log.d(TAG, "Message Notification payload: " + remoteMessage.getNotification());
            sendNotification(Objects.requireNonNull(remoteMessage.getNotification()).getBody());
        }
    }

    private void sendNotification(String aMessage) {
        final int NOTIFY_ID = 1002;
        final int _random = generateRandom();
        String name = "com.in10m";
        String id = "" + _random; // The user-visible name of the channel.

        NotificationCompat.Builder builder;

        if (!isAppIsInBackground(getApplicationContext())) {
            Log.d(TAG, "foreground");
        } else {
            Log.d(TAG, "background");
        }

        PendingIntent pendingIntent = null;
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (notificationManager == null) {
            notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(id, name, importance);
            channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(this, id);

            builder.setContentTitle(this.getString(R.string.app_name))  // required
                    .setSmallIcon(getNotificationIcon(builder)) // required
                    .setContentText(aMessage)  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(PRIORITY_MAX)
                    .setContentIntent(pendingIntent)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(aMessage))
                    .setTicker(aMessage);

        } else {
            builder = new NotificationCompat.Builder(this);
            builder.setContentTitle(this.getString(R.string.app_name)) // required
                    .setSmallIcon(getNotificationIcon(builder)) // required
                    .setContentText(aMessage)  // required
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(pendingIntent)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(aMessage))
                    .setTicker(aMessage).setVibrate(new long[0])
                    .setPriority(Notification.PRIORITY_HIGH);
        }

        builder.setVibrate(new long[0]);
        notificationManager.notify(NOTIFY_ID, builder.build());
    }

    private int getNotificationIcon(NotificationCompat.Builder notificationBuilder) {
        notificationBuilder.setColor(ContextCompat.getColor(getApplicationContext(), R.color.darkText));
        return R.mipmap.ic_launcher;
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (String activeProcess : processInfo.pkgList) {
                    if (activeProcess.equals(context.getPackageName())) {
                        isInBackground = false;
                    }
                }
            }
        }
        return isInBackground;
    }

    public static int generateRandom() {
        Random random = new Random();
        return random.nextInt(9999 - 1000) + 1000;
    }
}
