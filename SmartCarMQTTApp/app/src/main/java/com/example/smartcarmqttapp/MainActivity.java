package com.example.smartcarmqttapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class MainActivity extends AppCompatActivity {
    //MQTT Detials
    private final String CLIENT_TAG = "MQTTSmartCar";
    private final String LOCALHOST = "127.0.0.1";
    private final String URL = "tcp://" + LOCALHOST + ":1883";
    private final int QOS = 1;

    //main topic to derive others from
    public final String MAINMQTT_TOPIC = "/smartcar/";

    //control topics
    public final String STEERING_TOPIC = MAINMQTT_TOPIC + "controls/steering";
    public final String THROTTLE_TOPIC = MAINMQTT_TOPIC + "controls/throttle";
    public final String ESTOP_TOPIC = MAINMQTT_TOPIC + "controls/stop";

    //sensor topics
    public final String IR_TOPIC = MAINMQTT_TOPIC + "sensor/ir";
    public final String ULTRASONIC_TOPIC = MAINMQTT_TOPIC + "sensor/ultrasonic";
    public final String GYROSCOPE_TOPIC = MAINMQTT_TOPIC + "sensor/gyroscope";
    public final String ODOMETER_TOPIC = MAINMQTT_TOPIC + "sensor/odometer";
    public final String CAMERA_TOPIC = MAINMQTT_TOPIC + "sensor/camera";
    //status topics
    public final String BLINKERS_TOPIC = MAINMQTT_TOPIC + "status/blinkers";


    private MQTTFacade controller;
    private boolean isConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller = new MQTTFacade(getApplicationContext(), URL, CLIENT_TAG);
        connectToMQTT();

    }

    /**
     * Connects the app to the MQTT Broker via the tag that connects to where the broker
     * is hosted (localhost)
     */
    private void connectToMQTT(){
        if(!isConnected){
            controller.connect(CLIENT_TAG, "", new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    isConnected = true;

                    // For if you want to listen to the car's ir sensor
                    //example of a subscription = controller.subscribe(IR_TOPIC, QOS, null);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    final String failedConnection = "Failure to Connect to MQTT Broker";
                    System.out.println(failedConnection);
                }
            }, new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    isConnected = false;
                    System.out.println("Connection to MQTT broker lost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    System.out.println("MQTT Topic: " + topic + " | Message: " + message);
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("Message Delivered");
                }
            });
        }
    }
}