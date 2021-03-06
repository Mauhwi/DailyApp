package com.example.android.try2.DB.MedData;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MedDAO {
    @Insert
    void insert(MedData medData);

    @Update
    void update(MedData medData);

    @Delete
    void delete(MedData medData);

    //метод наполнения списка активных заданий
    @Query("SELECT * FROM MED_TABLE ORDER BY state ASC, time(time) ASC")
    LiveData<List<MedData>> getAllMeds();

    //метод наполнения списка неактивных заданий
    @Query("SELECT * FROM MED_TABLE WHERE state=2")
    LiveData<List<MedData>> getInactiveMeds();

    @Query("UPDATE med_table SET state = 1")
    void changestate();

    @Query("SELECT COUNT(*) FROM MED_TABLE WHERE state=2")
    int getInactiveCount();
}
