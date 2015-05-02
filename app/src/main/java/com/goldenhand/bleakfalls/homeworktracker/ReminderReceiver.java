package com.goldenhand.bleakfalls.homeworktracker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Default on 1/5/2015.
 */

public class ReminderReceiver extends BroadcastReceiver {

    //public static String NOTIFICATION_MANAGER = "com.goldenhand.bleakfalls.homeworktracker.reminderreceiver.notificationmanager";

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setContentTitle("HOMEWORK ALERT!")
                .setContentText("HW DUE SOONS")
                .setSmallIcon(R.drawable.clock);
        Intent notifyIntent = new Intent(context,HomeworkListActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(notifyPendingIntent);
        int mNotificationId= 1;
        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
        //System.out.println("NOTIFICATION CREATED");
    }
}
