package com.example.to_do;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.to_do.databinding.ActivityRegisterBinding;
import com.example.to_do.models.User;
import com.example.to_do.utils.Utils;
import com.example.to_do.viewmodel.TaskViewModel;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = RegisterActivity.class.getSimpleName();

    ActivityRegisterBinding binding;
    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        binding.setLifecycleOwner(this);

        binding.txtViewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUser();
            }
        });

        binding.txtViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }
    private void saveUser(){
        String username = binding.edtTxtUsername.getText().toString();
        String password = binding.edtTxtPassword.getText().toString();
        String name = binding.edtTxtName.getText().toString();
        String lastname = binding.edtTxtLastname.getText().toString();
        String passwordConfirm = binding.edtTxtPasswordConfirm.getText().toString();

        if(!(name.trim().isEmpty()||lastname.trim().isEmpty()||username.trim().isEmpty()||password.trim().isEmpty()||passwordConfirm.trim().isEmpty())){
            if(password.length() > 7){
                if(password.equals(passwordConfirm)) {
                    try {
                        User user = new User(username, password, name, lastname);
                        taskViewModel.getUserNameExist(username).observe(this, new Observer<String>() {
                            @Override
                            public void onChanged(String mUsername) {
                                if(!username.equals(mUsername)){

                                    //TODO: Checar pq se manda la petición dos veces

                                    taskViewModel.insertUser(user);

                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Este usuario ya existe", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } catch (Exception e){
                        Log.e(TAG, "ERROR: " + e.getMessage());
                    }
                } else {
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "La contraseña debe tener 8 caracteres mínimo", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Verifica tus datos", Toast.LENGTH_SHORT).show();
        }
    }

    private void back(){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}