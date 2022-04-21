package com.example.smartcarmqttapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.widget.ImageView;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    public MqttCar controller;
    public CardView theoryCard, drivingCard, connectCard, supportCard;
    //Camera Config
    private final int IMAGE_HEIGHT = 320;
    private final int IMAGE_WIDTH = 240;
    public ImageView imageView;


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


        controller = new MqttCar(getApplicationContext(), () -> {
            try {
                controller.changeSpeed(0.5);
            } catch (MqttException ex) {
                ex.printStackTrace();
            }
        });
    }

    @Override
    public void onClick(View view) {
    Intent i;


    switch (view.getId()){
        // TODO: Add activity class to intent. e.g  case R.id.theoryCard:i = new Intent(this,Driving.class);startActivity(i);
/*
        case R.id.theoryCard:i = new Intent();startActivity(i);
        break;
        case R.id.drivingCard:i = new Intent();startActivity(i);
        break;
        case R.id.connectCard:i = new Intent();startActivity(i);
        break;
        case R.id.supportCard:i = new Intent();startActivity(i);
        break;
        default:break;

 */
    }
    }

    /**
     *
     * @param message of frames to be rendered
     * This should be called upon received a message on the Camera Topic
     * and should then update the ImageView displayed on the current screen
     */
    public void cameraRendering(MqttMessage message){
        final Bitmap bm = Bitmap.createBitmap(IMAGE_WIDTH, IMAGE_HEIGHT, Bitmap.Config.ARGB_8888);

        final byte[] payload = message.getPayload();
        final int[] colors = new int[IMAGE_WIDTH * IMAGE_HEIGHT];
        for (int ci = 0; ci < colors.length; ++ci) {
            final byte r = payload[3 * ci];
            final byte g = payload[3 * ci + 1];
            final byte b = payload[3 * ci + 2];
            colors[ci] = Color.rgb(r, g, b);
        }
        bm.setPixels(colors, 0, IMAGE_WIDTH, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
        imageView.setImageBitmap(bm);
    }
}