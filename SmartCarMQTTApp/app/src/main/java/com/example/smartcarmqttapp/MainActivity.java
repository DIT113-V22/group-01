package com.example.smartcarmqttapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MainActivity extends AppCompatActivity {
    public MqttCar controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new MqttCar(getApplicationContext(), () -> {
            try {
                controller.changeSpeed(0.5);
            } catch (MqttException ex) {
                ex.printStackTrace();
            }
        });
    }
}