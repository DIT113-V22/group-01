package com.example.smartcarmqttapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD

import javax.swing.text.html.ImageView;

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

    //calculated data
    public final String ODOMETER_DISTANCE = MAINMQTT_TOPIC + 
                                            "status/odometer/distance";
    public final String ODOMETER_SPEED = MAINMQTT_TOPIC + "status/odometer/speed";

    //Camera Config
    private final int IMAGE_HEIGHT = 320;
    private final int IMAGE_WIDTH = 240;
    private ImageView mCameraView;

    private MQTTFacade controller;
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
    
    private void cameraRendering(){
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
        mCameraView.setImageBitmap(bm);
    }
}