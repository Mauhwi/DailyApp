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
    public static void setReminder(Context context, int dailyId,
                                   String title, Calendar when, int alarmCode) {
        //Создание AlarmManager для регистрации оповещений
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
        //или его удаление
        } else {
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
        }
    }

    //Метод вызова AlarmManager каждый день приблизительно в полночь
    public static void midnight(Context context) {
        Calendar timeNow = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        if(calendar.before(timeNow)){
            calendar.add(Calendar.DATE, 1);
        }
        AlarmManager alarmManager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, DailyResetReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i,
                PendingIntent.FLAG_ONE_SHOT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
    }
}
