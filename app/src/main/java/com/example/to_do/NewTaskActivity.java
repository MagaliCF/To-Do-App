package com.example.to_do;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.to_do.databinding.ActivityNewTaskBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewTaskActivity extends AppCompatActivity {
    ActivityNewTaskBinding binding;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent i = getIntent();

        if(i.hasExtra("id")){
            binding.txtViewCreateNote.setText("Editar nota");
            binding.editTxtName.setText(i.getStringExtra("title"));
            binding.editTxtDescription.setText(i.getStringExtra("description"));
            binding.editTxtDate.setText(i.getStringExtra("endDate"));
            binding.txtViewCreatedDate.setText("Creada el día " + i.getStringExtra("createdDate"));
        } else {
            date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
            binding.txtViewCreatedDate.setText("Creada el día " + date);
        }

        binding.txtViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewTaskActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        binding.txtViewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = binding.editTxtName.getText().toString().trim();
                String description =  binding.editTxtDescription.getText().toString().trim();
                String endDate = binding.editTxtDate.getText().toString().trim();
                Intent intent = new Intent();

                if(!title.isEmpty() && !description.isEmpty() && !endDate.isEmpty()) {
                    intent.putExtra("title", title);
                    intent.putExtra("description", description);
                    intent.putExtra("endDate", endDate);
                    intent.putExtra("createdDate", date);
                } else {
                    Toast.makeText(NewTaskActivity.this, "Inserta un nombre, descripción y fecha", Toast.LENGTH_SHORT).show();
                    return;
                }

                int id = getIntent().getIntExtra("id", -1);
                if(id != -1) {
                    intent.putExtra("id", id);
                }

                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}