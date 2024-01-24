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
    private int user;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        taskRepository = new TaskRepository(application);
    }

    public void init(int user) {
        this.user = user;
        taskList = taskRepository.getAllData(user);
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

