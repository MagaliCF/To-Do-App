package com.example.to_do;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.to_do.models.User;
import com.example.to_do.utils.Utils;

public class SplashScreen extends AppCompatActivity {
    public static final String TAG = SplashScreen.class.getSimpleName();

    Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = null;

                try {
                    User user = Utils.getUser(getApplicationContext(),"authCredentials");
                    Log.i(TAG, "run user information: " + user.getUsername());
                    intent = new Intent(SplashScreen.this, MainActivity.class);
                } catch (Exception e){
                    intent = new Intent(SplashScreen.this, LoginActivity.class);
                    Log.e(TAG, "run error: " + e.getMessage());
                }
                startActivity(intent);
                finish();
            }
        },2000);
    }
}