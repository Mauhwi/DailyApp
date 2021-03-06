package com.example.android.try2.DB;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.android.try2.DB.DailyData.DailyDao;
import com.example.android.try2.DB.DailyData.DailyData;
import com.example.android.try2.DB.MedData.MedDAO;
import com.example.android.try2.DB.MedData.MedData;


//абстрактный класс, использующий builder из состава room для создания таблицы
@Database(entities = {DailyData.class, MedData.class}, version = 3)
public abstract class DailyDB extends RoomDatabase {

    private static DailyDB instance;
    //пустой метод доступа к DAO, генерируемый room
    public abstract DailyDao dailyDao();
    public abstract MedDAO medDao();
    //создание таблицы, доступ к которому одновременно может иметь только один тред (synchronized)
    public static synchronized DailyDB getInstance(Context context) {
        //создание таблицы, только если ее экземпляра еще нет
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DailyDB.class, "daily_db").fallbackToDestructiveMigration()
                    .addCallback(roomCallback).build();
        }
        return instance;
    }
    //добавляем пример заполнения задания при создании таблицы
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void,Void,Void> {
        private DailyDao dailyDao;
        private MedDAO medDao;
        private PopulateDBAsyncTask(DailyDB database) {
            dailyDao = database.dailyDao();
            medDao = database.medDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dailyDao.insert(new DailyData("Покормить кота", "Помыть тарелку", "12:20", 1, 2));
            dailyDao.insert(new DailyData("Сделать упражнения", "Приседания", "14:20", 1, 2));
            dailyDao.insert(new DailyData("Сделать упражнения", "Приседания", "14:20", 2,2));
            medDao.insert(new MedData("Танакан", "pill", "12:20", 1));
            return null;
        }
    }
}
