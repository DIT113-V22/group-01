package com.example.smartcarmqttapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    public MqttCar controller;
    public CardView theoryCard, drivingCard, connectCard, supportCard;


    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        theoryCard = (CardView) findViewById(R.id.theoryCard);
        drivingCard = (CardView) findViewById(R.id.drivingCard);
        connectCard = (CardView) findViewById(R.id.connectCard);
        supportCard = (CardView) findViewById(R.id.supportCard);

        theoryCard.setOnClickListener(this);
        drivingCard.setOnClickListener(this);
        connectCard.setOnClickListener(this);
        supportCard.setOnClickListener(this);


//        controller = new MqttCar(getApplicationContext(), () -> {
//            try {
//                controller.changeSpeed(0.5);
//            } catch (MqttException ex) {
//                ex.printStackTrace();
//            }
//        });
    }

    @Override
    public void onClick(View view) {
    Intent i;



    switch (view.getId()){
        // TODO: Add activity class to intent. e.g  case R.id.theoryCard:i = new Intent(this,Driving.class);startActivity(i);
        case R.id.theoryCard:i = new Intent(this,PracticeTheoryActivity.class);startActivity(i);
        break;
        case R.id.drivingCard:i = new Intent(this,PracticeDrivingActivity.class);startActivity(i);
        break;
        case R.id.connectCard:i = new Intent(this,ConnectedCarActivity.class);startActivity(i);
        break;
        case R.id.supportCard:i = new Intent(this,AboutUsActivity.class);startActivity(i);
        break;
        default:break;
    }
    }
}