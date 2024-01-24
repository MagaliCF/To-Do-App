package com.example.to_do;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.to_do.models.User;
import com.example.to_do.utils.Utils;
import com.example.to_do.viewmodel.TaskViewModel;

public class RegisterActivity extends AppCompatActivity {
    public static final String TAG = RegisterActivity.class.getSimpleName();
    private EditText editTxtName;
    private EditText editTxtLastname;
    private EditText editTxtUsername;
    private EditText editTxtPassword;
    private EditText editTxtPasswordConfirm;
    private TextView txtViewCancel;
    private TextView txtViewSave;

    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTxtName = findViewById(R.id.editTxtName);
        editTxtLastname = findViewById(R.id.editTxtLastname);
        editTxtUsername = findViewById(R.id.editTxtUsername);
        editTxtPassword = findViewById(R.id.editTxtPassword);
        editTxtPasswordConfirm = findViewById(R.id.editTxtPasswordConfirm);
        txtViewCancel = findViewById(R.id.txtViewCancel);
        txtViewSave = findViewById(R.id.txtViewSave);

        taskViewModel = (TaskViewModel) new ViewModelProvider(this).get(TaskViewModel.class);

        txtViewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUser();
            }
        });

        txtViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back();
            }
        });
    }
    private void saveUser(){
        String username = editTxtUsername.getText().toString();
        String password = editTxtPassword.getText().toString();
        String name = editTxtName.getText().toString();
        String lastname = editTxtLastname.getText().toString();
        String passwordConfirm = editTxtPasswordConfirm.getText().toString();

        if(!(name.equals("")||lastname.equals("")||username.equals("")||password.equals("")||passwordConfirm.equals(""))){
            if(password.equals(passwordConfirm) && password.length() > 7){
                User user = new User(username, password, name, lastname);

                //TODO: Hacer la validación de exitencia de usuario, pero que se preocupe la Magali del futuro
                /*taskViewModel.insertUser(user);

                Utils.setUser(
                        getApplicationContext(),
                        "authCredentials",
                        user
                );*/

                Log.e(TAG, "User seved: " + user.toString() + " with id: " + user.getId());
            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
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