package com.example.to_do;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.to_do.databinding.ActivityLoginBinding;
import com.example.to_do.databinding.ActivityRegisterBinding;
import com.example.to_do.models.User;
import com.example.to_do.utils.Utils;
import com.example.to_do.viewmodel.TaskViewModel;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = LoginActivity.class.getSimpleName();
    ActivityLoginBinding binding;
    private TaskViewModel taskViewModel;
    String username;
    String password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        binding.setLifecycleOwner(this);

        binding.txtViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(LoginActivity.this, "Aún estamos trabajando en ello...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        binding.txtViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = binding.editTxtUsername.getText().toString().trim();
                password = binding.editTxtPwd.getText().toString().trim();

                if (!(username.isEmpty() || password.isEmpty())) {
                    checkUser(username);
                } else {
                    Toast.makeText(getApplicationContext(), "Hay campos vacíos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            String username = data.getStringExtra("username");
            String password = data.getStringExtra("password");
            String name = data.getStringExtra("name");
            String lastname = data.getStringExtra("lastname");

            User user = new User(username, password, name, lastname);

            taskViewModel.insertUser(user);

            Toast.makeText(this, "Ahora puedes iniciar sesión", Toast.LENGTH_SHORT).show();

        }
    }

    private void checkUser(String username) {
        try {
            taskViewModel.getUserNameExist(username).observe(this, new Observer<String>() {
                @Override
                public void onChanged(String mUsername) {
                    if (username.equals(mUsername)) {
                        Log.e(TAG, "onChanged: " + mUsername);
                        login(mUsername, password);

                    } else if (mUsername == null) {
                        Toast.makeText(getApplicationContext(), "Este usuario no existe", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "ERROR: " + e.getMessage());
        }
    }

    private void login(String username, String password) {
        taskViewModel.getUser(username, password).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                try {
                    if(user != null){
                        Log.e(TAG, "onChanged user: " + user.toString());
                        Log.e(TAG, "onChanged user id: " + user.getId());
                        Utils.setUser(
                                getApplicationContext(),
                                "authCredentials",
                                user
                        );
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Verifica la contraseña", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "ERROR getting user: " + e.getMessage());
                }
            }
        });
    }
}
