package com.example.to_do.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Date;

@Entity(tableName = "task_table")
public class Task {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    String name;
    String description;
    @ColumnInfo(name = "end_date")
    String endDate;
    @ColumnInfo(name = "created_date")
    String createdDate;
    int status;
    int user;

    public Task(String name, String description, String endDate, String createdDate, int status, int user) {
        this.name = name;
        this.description = description;
        this.endDate = endDate;
        this.createdDate = createdDate;
        this.status = status;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser() {
        return user;
    }


    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public void setUser(int user) {
        this.user = user;
    }
}
