package com.example.smartcarmqttapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
=======
import android.os.Handler;
>>>>>>> 685258c502cafc5f31f8c5501e1b5708b6963161

import org.eclipse.paho.client.mqttv3.MqttException;

public class MainActivity extends AppCompatActivity {
<<<<<<< HEAD

    private boolean isConnected = false;
    public MqttCar controller;
=======
    public static final int DELAY_MILLIS = 2500;

>>>>>>> 685258c502cafc5f31f8c5501e1b5708b6963161

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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