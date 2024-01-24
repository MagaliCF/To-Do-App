package com.example.to_do;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.to_do.models.User;
import com.example.to_do.utils.Utils;

public class LoginActivity extends AppCompatActivity {

    private EditText editTxtUser;
    private EditText editTxtPassword;
    private TextView txtViewRegiterHere;
    private TextView txtViewLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTxtUser = findViewById(R.id.editTxtUsername);
        editTxtPassword = findViewById(R.id.editTxtPwd);
        txtViewRegiterHere = findViewById(R.id.txtViewRegister);
        txtViewLogin = findViewById(R.id.txtViewLogin);

        txtViewRegiterHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(LoginActivity.this, "Aún estamos trabajando en ello...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        txtViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editTxtUser.getText().toString().equals("") && !editTxtPassword.getText().toString().equals("")){
                    Utils.setUser(
                            getApplicationContext(),
                            "authCredentials",
                            new User(editTxtUser.getText().toString(), editTxtPassword.getText().toString())
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
}
