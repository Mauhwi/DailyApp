package com.example.android.try2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.android.try2.DB.DailyDB;

//Изменяет
public class DailyReset extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                DailyDB dailyDB = DailyDB.getInstance(context.getApplicationContext());
                dailyDB.dailyDao().changestate();
                dailyDB.medDao().changestate();
            }
        });
    }
}
