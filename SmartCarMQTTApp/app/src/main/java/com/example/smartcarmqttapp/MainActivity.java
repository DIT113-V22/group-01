package com.example.smartcarmqttapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import javax.swing.text.html.ImageView;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import org.eclipse.paho.client.mqttv3.MqttException;

public class MainActivity extends AppCompatActivity {

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