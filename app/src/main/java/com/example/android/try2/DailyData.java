package com.example.android.try2;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//класс базы данных SQLite
@Entity(tableName = "daily_table")
public class DailyData {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private String time;

    //конструктор
    public DailyData(String title, String description, String time) {
        this.title = title;
        this.description = description;
        this.time = time;
    }
    //сеттер
    public void setId(int id) {
        this.id = id;
    }

    //геттеры
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }
}
