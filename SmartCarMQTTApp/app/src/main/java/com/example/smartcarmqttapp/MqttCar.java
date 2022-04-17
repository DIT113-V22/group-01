package com.example.smartcarmqttapp;

import android.content.Context;

import androidx.databinding.ObservableField;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.logging.Logger;

/**
 * A facade for interacting with a car over mqtt.
 */
public class MqttCar implements IMqttActionListener, MqttCallback {

    /**
     * Constants for all mqtt car topics.
     */
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
    public final ObservableField<Double> distance = new ObservableField<>(-1.0);
    public final ObservableField<Double> ir_distance  = new ObservableField<>(-1.0);
    
    public final ObservableField<Double> ultrasoundDistance = new ObservableField<>(-1.0);
    public final ObservableField<Double> gyroscopeHeading = new ObservableField<>(-1.0);
    public final ObservableField<Double> blinkerStatus = new ObservableField<>(-1.0);

    /**
     * Connects to a car over mqtt.
     *
     * @param context unfortunately required for the MqttAndroidClient to work
     * @param onConnected since the connection is async, this callback is
     *                    fired when the connection successfully completed
     */
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

    /**
     * Subscribes to the necessary topics to update the cars' observable fields.
     * Fires when the MqttAndroidClient connected successfully.
     *
     * @param asyncActionToken not relevant, MqttAndroidClient stuff
     */
    @Override
    public void onSuccess(IMqttToken asyncActionToken) {
        int QoS = 1;

        try {
            // ToDo: Subscribe your topic here
            this.logger.info("Subscribing...");
            mqtt.subscribe(Topics.DerivedData.Speed, QoS);
            mqtt.subscribe(Topics.DerivedData.Distance, QoS);
            mqtt.subscribe(Topics.Sensors.Infrared, QoS);
            mqtt.subscribe(Topics.Sensors.Odometer, QoS);
            mqtt.subscribe(Topics.Sensors.Camera, QoS);

            mqtt.subscribe(Topics.Sensors.Gyropscope, QoS);
            mqtt.subscribe(Topics.Sensors.Ultrasonic, QoS);
            mqtt.subscribe(Topics.Status.Blinkers, QoS);
        } catch (MqttException ex) {
            ex.printStackTrace();
        }

        this.onConnected.run();
    }

    /**
     * Fires when the MqttAndroidClient failed to connect.
     *
     * @param asyncActionToken not relevant, MqttAndroidClient stuff
     * @param exception the reason for the failed connection.
     */
    @Override
    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
        // ignore for now
        exception.printStackTrace();
    }

    /**
     * Fires when the MqttAndroidClient loses the connection.
     * Probably not too relevant since we set automatic reconnect to true.
     *
     * @param cause the reason for the lost connection
     */
    @Override
    public void connectionLost(Throwable cause) {
        // ignore for now
        cause.printStackTrace();
    }

    /**
     * Updates the cars' observable fields.
     * Fires when the MqttAndroidClient receives a message.
     *
     * @param topic the topic at which the message is received
     * @param message the received message
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) {
        final String data = new String(message.getPayload());

        try {
            switch (topic) {
                // Todo: Listen for your sensor topic here and set your field accordingly

                case Topics.DerivedData.Speed:
                    //speed in m/s
                    this.speed.set(Double.parseDouble(data));
                    break;
                case Topics.DerivedData.Distance:
                    //distance in cm
                    this.distance.set(Double.parseDouble(data));
                    break;
                case Topics.Sensors.Infrared:
                    this.ir_distance.set(Double.parseDouble(data));
                    break;
                case Topics.Sensors.Camera:
                    //Camera topic
                    //Display camera view on home screen
                    break;
                case Topics.Sensors.Gyroscope:
                    this.gyroscopeHeading.set(Double.parseDouble(data));
                    break;
                case Topics.Status.Blinkers:
                    this.blinkerStatus.set(data);
                    break;
                case Topics.Sensors.Ultrasonic:
                    this.ultrasoundDistance.set(Double.parseDouble(data));
                    break;
            }
        }
        catch (NumberFormatException ex) {
            logger.info("Unable to parse incoming data from " + topic + ", data: " + data);
        }
    }

    /**
     * Fires when a message is sent successfully.
     *
     * @param token not relevant, MqttAndroidClient stuff
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // ignore
    }

    /**
     * Checks the MqttAndroidClient connection status.
     *
     * @return the MqttAndroidClient connection status
     */
    public boolean isConnected() {
        return this.mqtt.isConnected();
    }

    // ToDo: Add publish methods here

    /**
     * Changes the car speed.
     *
     * @param speed in percentage (between 0 and 1)
     * @throws MqttException when the message cannot be sent to the car
     */
    public void changeSpeed(double speed) throws MqttException {
        mqtt.publish(Topics.Controls.Throttle, new MqttMessage(Double.toString(speed).getBytes()));
    }

    public void emergencyStop() throws MqttException {
        String message = "";
        mqtt.publish(Topics.Controls.EmergencyStop, new MqttMessage(message.getBytes()));
    }

    public void changeAngle(double angle) throws MqttException {
        mqtt.publish(Topics.Controls.Steering, new MqttMessage(Double.toString(angle).getBytes()));
    }

    public void blinkDirection(String direction) throws MqttException {
        mqtt.publish(Topics.Status.Blinkers, new MqttMessage(direction.getBytes()));
    }
}
