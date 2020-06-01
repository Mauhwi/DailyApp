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
    public static void setReminder(Context context, int dailyId,
                                   String title, Calendar when, int alarmCode) {
        //Создание AlarmManager для регистрации оповещений
        if (alarmCode == 1) {
            AlarmManager alarmManager = (AlarmManager) context
                .getSystemService(Context.ALARM_SERVICE);
            Intent i = new Intent(context, AlarmReceiver.class);
            i.putExtra(EXTRA_TEXT, title);
            i.putExtra(EXTRA_ID, dailyId);
            Calendar timeNow = Calendar.getInstance();
            if(when.before(timeNow)){
                when.add(Calendar.DATE, 1);
            }
            PendingIntent pi = PendingIntent.getBroadcast(context, dailyId, i,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    when.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
        //или его удаление
        } else {
            AlarmManager alarmManager = (AlarmManager) context
                    .getSystemService(Context.ALARM_SERVICE);
            Intent i = new Intent(context, AlarmReceiver.class);
            i.putExtra(EXTRA_TEXT, title);
            i.putExtra(EXTRA_ID, dailyId);
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
        Intent i = new Intent(context, DailyReset.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i,
                PendingIntent.FLAG_ONE_SHOT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
    }
}
