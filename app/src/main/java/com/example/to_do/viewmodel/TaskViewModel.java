package com.example.to_do.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.to_do.models.Task;
import com.example.to_do.repository.TaskRepository;
import com.example.to_do.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository taskRepository;
    private LiveData<List<Task>> taskList;
    private String user;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        //user = Utils.getUser(application.getApplicationContext(),"authCredentials").getUsername();
        taskRepository = new TaskRepository(application);
        //taskList = taskRepository.getAllData(user);
    }

    public void init(String user) {
        this.user = user;
        taskList = taskRepository.getAllData(user);
    }

    public void insert(Task task) {
        taskRepository.insertData(task);
    }

    public void delete(Task task) {
        taskRepository.deleteData(task);
    }

    public void update(Task task) {
        taskRepository.updateData(task);
    }

    public LiveData<List<Task>> getAllTasks(String user) {
        return taskList;
    }
}

