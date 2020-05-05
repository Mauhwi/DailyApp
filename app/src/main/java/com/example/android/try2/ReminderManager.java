package com.example.android.try2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;


public class ReminderManager {
    public static final String EXTRA_ID = "com.example.android.try2.EXTRA_ID";
    public static final String EXTRA_TEXT = "com.example.android.try2.EXTRA_TEXT";

    private ReminderManager() {}
    public static void setReminder(Context context, long taskId,
                                   String title, Calendar when) {
        AlarmManager alarmManager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, OnAlarmReceiver.class);
        i.putExtra(EXTRA_TEXT, title);
        i.putExtra(EXTRA_ID, taskId);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i,
                PendingIntent.FLAG_ONE_SHOT);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                when.getTimeInMillis(), pi);
    }
}
