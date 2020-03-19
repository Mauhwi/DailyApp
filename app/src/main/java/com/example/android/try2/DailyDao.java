package com.example.android.try2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//data access object предоставляет методы для доступа к БД
@Dao
public interface DailyDao {
    //методы без тела, т.к. room автоматически генерирует метод при использовании аннотации @Insert
    @Insert
    void insert(DailyData dailyData);

    @Update
    void update(DailyData dailyData);

    @Delete
    void delete(DailyData dailyData);

    //метод наполнения списка
    @Query("SELECT * FROM DAILY_TABLE")
    LiveData<List<DailyData>> getAllNotes();
}