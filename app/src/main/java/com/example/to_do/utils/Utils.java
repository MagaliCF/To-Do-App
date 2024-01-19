package com.example.to_do.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.to_do.models.Login;
import com.google.gson.Gson;

public class Utils {
    public static void setUser(Context context, String key, Login usuario){
        SharedPreferences sp = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(usuario);

        editor.putString(key,json);
        editor.apply();
    }

    public static Login getUser(Context context, String key){
        SharedPreferences sp = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString(key, "");

        Login user = gson.fromJson(json, Login.class);

        return user;
    }
}
