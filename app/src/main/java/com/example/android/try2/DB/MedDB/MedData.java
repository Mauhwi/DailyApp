package com.example.android.try2.DB.MedDB;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//класс базы данных SQLite
@Entity(tableName = "med_table")
public class MedData {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String image;
    private String time;
    @ColumnInfo(defaultValue = "1")
    private int state;

    //конструктор
    public MedData(String title, String image, String time, int state) {
        this.title = title;
        this.image = image;
        this.time = time;
        this.state = state;
    }
    //сеттер
    public void setId(int id) {
        this.id = id;
    }

    public void setState(int state) {
        this.state = state;
    }

    //геттеры
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getTime() {
        return time;
    }

    public int getState() {
        return state;
    }
}
