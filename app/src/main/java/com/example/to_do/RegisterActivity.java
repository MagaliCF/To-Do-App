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
    boolean showMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        binding.setLifecycleOwner(this);

        showMessage = true;
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
        String username = binding.edtTxtUsername.getText().toString().trim();
        String password = binding.edtTxtPassword.getText().toString().trim();
        String name = binding.edtTxtName.getText().toString().trim();
        String lastname = binding.edtTxtLastname.getText().toString().trim();
        String passwordConfirm = binding.edtTxtPasswordConfirm.getText().toString().trim();

        if(!(name.isEmpty()||lastname.isEmpty()||username.isEmpty()||password.isEmpty()||passwordConfirm.isEmpty())){
            if(name.length() > 2 && username.length() > 2 && lastname.length() > 2){
                if(password.length() > 7){
                    if(password.equals(passwordConfirm)) {
                        try {
                            taskViewModel.getUserNameExist(username).observe(this, new Observer<String>() {
                                @Override
                                public void onChanged(String mUsername) {
                                    if(!username.equals(mUsername)){
                                        Intent intent = new Intent();
                                        intent.putExtra("username",username);
                                        intent.putExtra("password",password);
                                        intent.putExtra("name",name);
                                        intent.putExtra("lastname",lastname);
                                        setResult(RESULT_OK, intent);
                                        showMessage = false;
                                        finish();
                                    } else if(showMessage){
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
                Toast.makeText(this, "Tu nombre, apellido o usuario son muy cortos", Toast.LENGTH_SHORT).show();
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