package com.example.android.try2;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//класс абстрактный, так как вместо new используется builder из состава room
@Database(entities = DailyData.class, version = 1)
public abstract class DailyDB extends RoomDatabase {

    //объявления единичного экземпляра таблицы
    private static DailyDB instance;

    //пустой метод доступа к DAO, генерируемый room
    public abstract DailyDao dailyDao();

    //создание таблицы, доступ к которому одновременно может иметь только один тред (synchronized)
    public static synchronized DailyDB getInstance(Context context) {
        //создание таблицы, только если ее экземпляра еще нет
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DailyDB.class, "daily_db").fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
