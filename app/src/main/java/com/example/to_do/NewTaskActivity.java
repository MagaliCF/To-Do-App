package com.example.to_do;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.to_do.databinding.ActivityNewTaskBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NewTaskActivity extends AppCompatActivity {
    ActivityNewTaskBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        binding.txtViewCreatedDate.setText("Creada el día " + date);

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
                Intent intent = new Intent();
                intent.putExtra("title", binding.editTxtName.getText().toString());
                intent.putExtra("description", binding.editTxtDescription.getText().toString());
                intent.putExtra("endDate", binding.editTxtDate.getText().toString());
                intent.putExtra("createdDate", date);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}