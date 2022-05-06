package com.example.smartcarmqttapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;

import com.example.smartcarmqttapp.screens.HomeActivity;

public class MainActivity extends AppCompatActivity {

    public MqttCar controller;
    public static final int DELAY_MILLIS = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("Welcome");

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(in);
                finish();
            }
        }, DELAY_MILLIS);

    }
}