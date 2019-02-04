package com.example.firstnotificationtest;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.annotation.RequiresApi;
import android.widget.TextView;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NotificationService extends NotificationListenerService {

        @Override
        public void onCreate() {
            super.onCreate();
            //android.os.Debug.waitForDebugger();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
        @Override
        public void onNotificationPosted(StatusBarNotification sbn) {
            Intent intent = new  Intent("com.example.firstnotificationtest");
            intent.putExtra("Notification Type", sbn.getPackageName());
            intent.putExtra("Notification Title", sbn.getNotification().extras.getString(Notification.EXTRA_TITLE));
            intent.putExtra("Notification Text", sbn.getNotification().extras.getCharSequence(Notification.EXTRA_TEXT));
            sendBroadcast(intent);
        }

        @Override
        public void onNotificationRemoved(StatusBarNotification sbn) {

        }
}
