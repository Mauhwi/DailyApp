package com.example.android.try2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.android.try2.DB.DailyDB.DailyDB;
import com.example.android.try2.DB.MedDB.MedDB;

//Изменяет
public class DailyReset extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                MedDB medDB = MedDB.getInstance(context.getApplicationContext());
                medDB.medDao().changestate();
                DailyDB dailyDB = DailyDB.getInstance(context.getApplicationContext());
                dailyDB.dailyDao().changestate();
            }
        });
    }
}
