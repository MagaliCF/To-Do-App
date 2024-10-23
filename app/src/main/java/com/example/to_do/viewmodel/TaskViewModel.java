package com.example.to_do.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.to_do.models.Task;
import com.example.to_do.models.User;
import com.example.to_do.repository.TaskRepository;
import com.example.to_do.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository taskRepository;
    private LiveData<List<Task>> taskList;
    private LiveData<String> username;
    private LiveData<User> mUser;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        taskRepository = new TaskRepository(application);
    }

    public void init(User user) {
        taskList = taskRepository.getAllData(user.getId());
        username = taskRepository.getUserNameExist(user.getUsername());
        mUser = taskRepository.getUser(user.getUsername(), user.getPassword());
    }

    //User methods
    public void insertUser(User user) {
        taskRepository.insertDataUser(user);
    }

    public void deleteUser(User user) {
        taskRepository.deleteDataUser(user);
    }

    public void updateUser(User user) {
        taskRepository.updateDataUser(user);
    }
    public LiveData<String> getUserNameExist(String mUsername){
        username = taskRepository.getUserNameExist(mUsername);
        return username;
    }

    public  LiveData<User> getUser(String mUsername, String mPassword){
        mUser = taskRepository.getUser(mUsername, mPassword);
        return mUser;
    }
    //Task methods
    public void insert(Task task) {
        taskRepository.insertData(task);
    }

    public void delete(Task task) {
        taskRepository.deleteData(task);
    }

    public void update(Task task) {
        taskRepository.updateData(task);
    }

    public LiveData<List<Task>> getAllTasks(int user) {
        return taskList;
    }
}

