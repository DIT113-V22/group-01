package com.example.smartcarmqttapp;

import android.content.Context;

import androidx.databinding.ObservableField;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.logging.Logger;

public class MqttCar implements IMqttActionListener, MqttCallback {

    public static final class Topics {
        public static final String base = "/smartcar";
        public static final class Sensors {
            public static final String base = Topics.base + "/sensor";
            public static final String Infrared = Sensors.base + "/ir";
            public static final String Ultrasonic = Sensors.base + "/ultrasonic";
            public static final String Odometer = Sensors.base + "/odometer";
            public static final String Gyroscope = Sensors.base + "/gyroscope";
            public static final String Camera = Sensors.base + "/camera";
        }
        public static final class Status {
            public static final String base = Topics.base + "/status";
            public static final String Blinkers = Status.base + "/blinkers";
        }
        public static final class DerivedData {
            public static final String base = Topics.base + "/derivedData";
            public static final String Speed = DerivedData.base + "/speed";
            public static final String Distance = DerivedData.base + "/distance";
        }
        public static final class Controls {
            public static final String base = Topics.base + "/controls";
            public static final String Steering = Controls.base + "/steering";
            public static final String Throttle = Controls.base + "/throttle";
            public static final String EmergencyStop = Controls.base + "/stop";
        }
    }

    private final MqttAndroidClient mqtt;
    private final Logger logger;
    private final Runnable onConnected;

    // ToDo: Add a field for your sensor data here as an observable field
    public final ObservableField<Double> speed = new ObservableField<>(-1.0);

    public MqttCar(Context context, Runnable onConnected) {
        this.logger = Logger.getLogger("mqtt");
        this.onConnected = onConnected;

        // Mqtt Config
        String clientId = "AndroidApp";
        String url = "tcp://10.0.2.2:1883"; // localhost on default port

        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(clientId);
        options.setPassword("".toCharArray());
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);

        this.mqtt = new MqttAndroidClient(context, url, clientId);

        try {
            mqtt.setCallback(this);
            mqtt.connect(options, null, this);
        }
        catch (MqttException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        int QoS = 1;

        try {
            // ToDo: Subscribe your topic here
            this.logger.info("Subscribing...");
            mqtt.subscribe(Topics.DerivedData.Speed, QoS);
        } catch (MqttException ex) {
            ex.printStackTrace();
        }

        this.onConnected.run();
    }

    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        // ignore for now
        exception.printStackTrace();
    }

    @Override
    public void connectionLost(Throwable cause) {
        // ignore for now
        cause.printStackTrace();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        final String data = new String(message.getPayload());


        try {
            switch (topic) {
                // Todo: Listen for your sensor topic here and set your field accordingly

                case Topics.DerivedData.Speed:
                    this.speed.set(Double.parseDouble(data));
                    break;
            }
        }
        catch (NumberFormatException ex) {
            logger.info("Unable to parse incoming data from " + topic + ", data: " + data);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // ignore
    }

    public boolean isConnected() {
        return this.mqtt.isConnected();
    }

    // ToDo: Add publish methods here
    public void changeSpeed(double speed) throws MqttException {
        mqtt.publish(Topics.Controls.Throttle, new MqttMessage(Double.toString(speed).getBytes()));
    }
}
