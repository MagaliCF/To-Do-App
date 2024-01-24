package com.example.to_do;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.to_do.adapters.AdapterToDoList;
import com.example.to_do.databinding.ActivityMainBinding;
import com.example.to_do.models.Task;
import com.example.to_do.utils.Utils;
import com.example.to_do.viewmodel.TaskViewModel;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    ActivityMainBinding binding;
    private TaskViewModel taskViewModel;
    private int user;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        user = Utils.getUser(getApplicationContext(),"authCredentials").getId();

        taskViewModel = (TaskViewModel) new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.init(user);

        /*taskViewModel = new ViewModelProvider(this, (Factory) AndroidViewModelFactory.getInstance(this.getApplication()))
                .get(TaskViewModel.class);*/

        binding.txtViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewTaskActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        binding.txtViewName.setText("Hola, " + Utils.getUser(getApplicationContext(),"authCredentials").getUsername());

        binding.txtViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.setUser(
                        getApplicationContext(),
                        "authCredentials",
                        null
                );
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setHasFixedSize(true);

        AdapterToDoList adapter = new AdapterToDoList();
        binding.recyclerView.setAdapter(adapter);

        taskViewModel.getAllTasks(user).observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                adapter.setTasks(tasks);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                taskViewModel.delete(adapter.getTaskAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Has eliminado una nota", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(binding.recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            String title = data.getStringExtra("title");
            String description = data.getStringExtra("description");
            String createdDate = data.getStringExtra("createdDate");
            String endDate = data.getStringExtra("endDate");

            Task task = new Task(title,description,endDate,createdDate,1, user);

            taskViewModel.insert(task);

            Toast.makeText(this, "¡Has agregado una nota correctamente!", Toast.LENGTH_SHORT).show();
        }
    }
}