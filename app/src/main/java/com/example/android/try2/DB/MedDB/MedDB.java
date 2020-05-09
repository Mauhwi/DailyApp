package com.example.android.try2.DB.MedDB;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = MedData.class, version = 1)
public abstract class MedDB extends RoomDatabase {
    //объявления единичного экземпляра таблицы
    private static MedDB instance;

    //пустой метод доступа к DAO, генерируемый room
    public abstract MedDAO medDao();

    public static synchronized MedDB getInstance(Context context) {
        if (instance ==null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    MedDB.class, "med_db").fallbackToDestructiveMigration()
                    .addCallback(roomCallback).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    private static  class PopulateDBAsyncTask extends AsyncTask<Void,Void,Void> {
        private MedDAO medDAO;

        private PopulateDBAsyncTask(MedDB database) {
            medDAO = database.medDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            medDAO.insert(new MedData("Танакан", "pill", "12:20", 1));
            return null;
        }
    }


}
