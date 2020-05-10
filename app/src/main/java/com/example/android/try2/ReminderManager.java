package com.example.android.try2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.android.try2.ui.med.MedFragment;

import java.util.Calendar;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class ReminderManager {
    public static final String EXTRA_ID = "com.example.android.try2.EXTRA_ID";
    public static final String EXTRA_TEXT = "com.example.android.try2.EXTRA_TEXT";
    public static final String EXTRA_CODE = "com.example.android.try2.EXTRA_CODE";

    private ReminderManager() {}
    public static void setReminder(Context context, int dailyId,
                                   String title, Calendar when, int alarmCode) {
        if (alarmCode == 1) {
            AlarmManager alarmManager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
            Intent i = new Intent(context, OnAlarmReceiver.class);
            i.putExtra(EXTRA_TEXT, title);
            i.putExtra(EXTRA_ID, dailyId);
            i.putExtra(EXTRA_CODE, alarmCode);
            //Flag indicating that if the described PendingIntent already exists, the current one should be canceled before generating a new one
            PendingIntent pi = PendingIntent.getBroadcast(context, dailyId, i,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,
                    when.getTimeInMillis(), pi);
            Log.i(TAG, "Pending intent: " + "context: " + context + " id: " + dailyId + " i: " + i);
        } else if (alarmCode == 2){
            AlarmManager alarmManager = (AlarmManager) context
                    .getSystemService(Context.ALARM_SERVICE);
            Intent i = new Intent(context, OnAlarmReceiver.class);
            i.putExtra(EXTRA_TEXT, title);
            i.putExtra(EXTRA_ID, dailyId);
            i.putExtra(EXTRA_CODE, alarmCode);
            PendingIntent pi = PendingIntent.getBroadcast(context, dailyId, i,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            pi.cancel();
            alarmManager.cancel(pi);
            Log.i(TAG, "Pending intent: " + "context: " + context + " id: " + dailyId + " i: " + i);
        } else {
            AlarmManager alarmManager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
            Intent i = new Intent(context, OnAlarmReceiver.class);
            i.putExtra(EXTRA_TEXT, title);
            i.putExtra(EXTRA_ID, dailyId);
            i.putExtra(EXTRA_CODE, alarmCode);
            PendingIntent pi = PendingIntent.getBroadcast(context, 0, i,
                    PendingIntent.FLAG_ONE_SHOT);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    when.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
        }

    }
}
