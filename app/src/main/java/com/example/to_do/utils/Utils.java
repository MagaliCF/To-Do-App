package com.example.to_do.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.to_do.models.User;
import com.google.gson.Gson;

public class Utils {
    public static void setUser(Context context, String key, User user){
        SharedPreferences sp = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);

        editor.putString(key,json);
        editor.apply();
    }

    public static User getUser(Context context, String key){
        SharedPreferences sp = context.getSharedPreferences("preferences", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString(key, "");

        User user = gson.fromJson(json, User.class);

        return user;
    }

    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
