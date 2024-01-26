package com.example.to_do.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.to_do.models.User;

@Dao
public interface UserDao {

    @Insert
    public void insert(User user);

    @Update
    public void update(User user);

    @Delete
    public void delete(User user);

    @Query("SELECT username FROM users_table WHERE username = :username")
    public LiveData<String> getUserNameExist(String username);

    @Query("SELECT password FROM users_table WHERE username = :username")
    public LiveData<String> getUserPassword(String username);
}
