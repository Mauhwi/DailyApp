package com.example.android.try2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;


public class ReminderManager {
    public static final String EXTRA_ID = "com.example.android.try2.EXTRA_ID";
    public static final String EXTRA_TEXT = "com.example.android.try2.EXTRA_TEXT";
    public static final String EXTRA_CODE = "com.example.android.try2.EXTRA_CODE";

    private ReminderManager() {}
    public static void setReminder(Context context, int taskId,
                                   String title, Calendar when, int alarmCode) {
        if (alarmCode == 1) {
            AlarmManager alarmManager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
            Intent i = new Intent(context, OnAlarmReceiver.class);
            i.putExtra(EXTRA_TEXT, title);
            i.putExtra(EXTRA_ID, taskId);
            i.putExtra(EXTRA_CODE, alarmCode);
            PendingIntent pi = PendingIntent.getBroadcast(context, 0, i,
                    PendingIntent.FLAG_ONE_SHOT);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                    when.getTimeInMillis(), pi);
        } else if (alarmCode == 2){
            AlarmManager alarmManager = (AlarmManager) context
                    .getSystemService(Context.ALARM_SERVICE);
            Intent i = new Intent(context, OnAlarmReceiver.class);
            i.putExtra(EXTRA_TEXT, title);
            i.putExtra(EXTRA_ID, taskId);
            i.putExtra(EXTRA_CODE, alarmCode);
            PendingIntent pi = PendingIntent.getBroadcast(context, 0, i,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(pi);
        } else {
            AlarmManager alarmManager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
            Intent i = new Intent(context, OnAlarmReceiver.class);
            i.putExtra(EXTRA_TEXT, title);
            i.putExtra(EXTRA_ID, taskId);
            i.putExtra(EXTRA_CODE, alarmCode);
            PendingIntent pi = PendingIntent.getBroadcast(context, 0, i,
                    PendingIntent.FLAG_ONE_SHOT);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                    when.getTimeInMillis(), pi);
        }

    }
}
