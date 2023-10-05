package com.example.roomdbproject;

import android.graphics.Color;
import android.graphics.ColorSpace;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "history")
public class History implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_history")
    private int id;
    @ColumnInfo(name = "sum")
    private int sum;
    @ColumnInfo(name = "red")
    private int red;
    @ColumnInfo(name = "green")
    private int green;
    @ColumnInfo(name = "blue")
    private int blue;
    @ColumnInfo(name = "category")
    private String category;
    @ColumnInfo(name = "operation")
    private String operation;
    @ColumnInfo(name = "userId")
    private int userId;

    public History(int sum, String category, String operation, int userId, int red, int green, int blue){
        this.sum = sum;
        this.category = category;
        this.operation = operation;
        this.userId = userId;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }
}
