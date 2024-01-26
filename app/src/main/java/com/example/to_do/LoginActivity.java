package com.example.to_do;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.to_do.databinding.ActivityLoginBinding;
import com.example.to_do.databinding.ActivityRegisterBinding;
import com.example.to_do.models.User;
import com.example.to_do.utils.Utils;
import com.example.to_do.viewmodel.TaskViewModel;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    private TaskViewModel taskViewModel;
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
            String username = binding.editTxtUsername.getText().toString();
            String password = binding.editTxtPwd.getText().toString();
            @Override
            public void onClick(View view) {
                if(!username.equals("") && !password.equals("")){
                    Utils.setUser(
                            getApplicationContext(),
                            "authCredentials",
                            new User(username, password)
                    );
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Verifica tus datos", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            String username = data.getStringExtra("username");
            String password = data.getStringExtra("password");
            String name = data.getStringExtra("name");
            String lastname = data.getStringExtra("lastname");

            User user = new User(username,password,name, lastname);

            taskViewModel.insertUser(user);

            Toast.makeText(this, "Ahora puedes iniciar sesión", Toast.LENGTH_SHORT).show();

        }
    }
}
