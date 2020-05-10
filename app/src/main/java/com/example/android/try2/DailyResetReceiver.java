package com.example.android.try2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.try2.DB.MedDB.MedDB;
import com.example.android.try2.DB.MedDB.MedData;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DailyResetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                MedDB medDB = MedDB.getInstance(context.getApplicationContext());
                medDB.medDao().changestate();
                Log.i(TAG, "миднайт рисивд");
            }
        });
    }
}
