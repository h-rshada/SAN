package com.example.android.san;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class cashOnDelivery extends AppCompatActivity {

    SharedPreferences sp;
    boolean login;
    String auth_id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_on_delivery);
        sp = getSharedPreferences("YourSharedPreference", Activity.MODE_PRIVATE);
        login = sp.getBoolean("LOGIN", false);
        auth_id = sp.getString("AUTH_ID", "");

        Log.d("Login^^^^^^^6666", login + "");
        Log.d("Auth_id", auth_id);
        Log.d("I m in cashon delivery", "Cash");
    }
}
