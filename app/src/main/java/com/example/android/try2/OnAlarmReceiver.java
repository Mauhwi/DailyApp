package com.example.android.try2;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class OnAlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager mgr = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        int taskId = intent.getIntExtra(ReminderManager.EXTRA_ID, -1);
        String title = intent.getStringExtra(ReminderManager.EXTRA_TEXT);
// Build the Notification object using a Notification.Builder
        Notification note = new Notification.Builder(context)
                .setContentTitle(context.getString(R.string.notify_new_task_title))
                .setContentText(title)
                .setSmallIcon(android.R.drawable.stat_sys_warning)
                .setAutoCancel(true)
                .build();
        // Send the notification.
        mgr.notify((int) taskId, note);
    }
}
