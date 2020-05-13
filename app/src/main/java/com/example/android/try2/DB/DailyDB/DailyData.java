package com.example.android.try2.DB.DailyDB;

import androidx.room.ColumnInfo;
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
    @ColumnInfo(defaultValue = "1")
    private int state;
    @ColumnInfo(defaultValue = "2")
    private int notificationState;

    //конструктор
    public DailyData(String title, String description, String time, int state, int notificationState) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.state = state;
        this.notificationState = notificationState;
    }
    //сеттер
    public void setId(int id) {
        this.id = id;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setNotificationState(int notificationState) {
        this.notificationState=notificationState;
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

    public int getState() {
        return state;
    }

    public int getNotificationState() {
        return  notificationState;
    }
}
