package com.example.to_do.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.to_do.models.Task;
import com.example.to_do.models.User;

@Database(entities = {Task.class, User.class}, version = 4)
    public abstract class TaskDatabase extends RoomDatabase {
    private static TaskDatabase instance;
    public abstract TaskDao taskDao();
    public abstract UserDao userDao();
    public static synchronized TaskDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext()
                            ,TaskDatabase.class, "task_database").fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
