package com.example.smartcarmqttapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import javax.swing.text.html.ImageView;

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
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller = new MQTTFacade(getApplicationContext(), URL, CLIENT_TAG);
        connectToMQTT();

    }

    /**
     * Connects the app to the MQTT Broker via the tag that connects to where 
     * the broker is hosted (localhost)
     */
    private void connectToMQTT(){
        if(!isConnected){
            controller.connect(CLIENT_TAG, "", new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    isConnected = true;

                    // For if you want to listen to the car's ir sensor
                    //example of a subscription = controller.subscribe(IR_TOPIC, 
                    //QOS, null);

                    controller.subscribe(IR_TOPIC, QOS, null);
                    controller.subscribe(CAMERA_TOPIC, QOS, null);
                    controller.subscribe(ODOMETER_DISTANCE, QOS, null);
                    controller.subscribe(ODOMETER_SPEED, QOS, null);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, 
                                      Throwable exception) {
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
                    //Here subscribed topics are handled
                    //Upon an asynchronous message arrival, depending on the
                    //topic, the message is handled accordingly
                    if(topic.equals(IR_TOPIC)){
                        System.out.println("IR Sensor: " + message.toString());
                    }
                    else if(topic.equals(CAMERA_TOPIC)){
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