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

    public static String HW_NAME = "NAME_OF_HOMEWORK_TO_DISPLAY_IN_NOTIFICATION";
    public static String HW_SUBJ_NAME = "SUBJECT_NAME_OF_HOMEWORK_TO_DISPLAY_IN_NOTIFICATION";
    public static String HW_ID = "HOMEWORK ID TO LOAD HW WHEN U CLICK ON THE NOTIFICATION";

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setContentTitle("REMINDER TO DO HOMEWORK!")
                .setContentText(intent.getStringExtra(HW_NAME) + " - " + intent.getStringExtra(HW_SUBJ_NAME))
                .setSmallIcon(R.drawable.clock);
        Intent notifyIntent = new Intent(context,HomeworkListActivity.class);
        notifyIntent.putExtra(HW_ID, intent.getStringExtra(HW_ID));
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(notifyPendingIntent);
        int mNotificationId= 1;
        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
        //System.out.println("NOTIFICATION CREATED");
    }
}
