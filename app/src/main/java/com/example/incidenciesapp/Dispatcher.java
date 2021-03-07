package com.example.incidenciesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class Dispatcher extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatcher);
        Class<?> activityClass;
/*
        try {
            SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
            activityClass = Class.forName(
                    prefs.getString("lastActivity", Activity1.class.getName()));
        } catch(ClassNotFoundException ex) {
            activityClass = Activity1.class;
        }

        startActivity(new Intent(this, activityClass));
        */

    }
     }
