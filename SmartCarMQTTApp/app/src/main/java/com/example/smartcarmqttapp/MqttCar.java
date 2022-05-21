package com.example.smartcarmqttapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * A facade for interacting with a car over mqtt.
 */
public class MqttCar extends BaseObservable implements IMqttActionListener, MqttCallback {

    /**
     * Constants for all mqtt car topics.
     */
    public static final class Topics {
        public static final String base = "/smartcar";
        public static final String Heartbeat = Topics.base + "/heartbeat";
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
            public static final class Odometer {
                public static final String base = Status.base + "/odometer";
                public static final String Speed = Odometer.base + "/speed";
                public static final String Distance = Odometer.base + "/distance";
            }
        }
        public static final class Controls {
            public static final String base = Topics.base + "/controls";
            public static final String Steering = Controls.base + "/steering";
            public static final String Throttle = Controls.base + "/throttle";
            public static final String EmergencyStop = Controls.base + "/stop";
        }
    }

    /**
     * Constants for communicating the direction of the blinker over mqtt.
     */
    public enum BlinkerDirection {
        Left,
        Right, 
        Off
    }

    /**
     * Default heading inside the SMCE emulator, figured out by testing.
     */
    public final static double DEFAULT_HEADING = 180.0;

    /**
     * Connection timeout in seconds.
     */
    public final static double CONNECTION_TIMEOUT = 10;

    // Mqtt broker, default: emulator host on default port
    public final static String MQTT_HOSTNAME = "tcp://10.0.2.2:1883";

    // Camera config
    private final static int IMAGE_HEIGHT = 240;
    private final static int IMAGE_WIDTH = 320;

    private final MqttAndroidClient mqtt;
    private final Logger logger;
    private final Runnable onConnected;
    private final Runnable onConnectionLost;
    private boolean heartbeatLost;

    /**
     * Time the car has been running in ms.
     */
    public final ObservableField<Long> timeRunning = new ObservableField<>(0L);

    /**
     * Datetime of the last received heartbeat.
     */
    public final ObservableField<LocalDateTime> lastHeartbeat = new ObservableField<>(LocalDateTime.now());

    /**
     * Throttle
     */
    public final ObservableField<Double> throttle = new ObservableField<>(0.0);

    /**
     * Speed in m/s.
     */
    public final ObservableField<Double> speed = new ObservableField<>(0.0);

    /**
     * Distance in cm.
     */
    public final ObservableField<Double> distance = new ObservableField<>(0.0);

    /**
     * Distance in cm.
     */
    public final ObservableField<Double> ir_distance  = new ObservableField<>(0.0);

    /**
     * Distance in cm.
     */
    public final ObservableField<Double> ultrasoundDistance = new ObservableField<>(0.0);

    /**
     * Heading in degrees.
     */
    public final ObservableField<Double> gyroscopeHeading = new ObservableField<>(DEFAULT_HEADING);

    /**
     * The current angle of the wheels
     */
    public final ObservableField<Double> wheelAngle = new ObservableField<>(0.0);

    /**
     * Which blinkers are activated.
     */
    public final ObservableField<BlinkerDirection> blinkerStatus = new ObservableField<>(BlinkerDirection.Off);

    /**
     * The current camera frame.
     */
    public final ObservableField<Bitmap> camera = new ObservableField<>(null);

    public final Map<String, Runnable> listeners = new HashMap<>();

    /**
     * Connects to a car over mqtt.
     *
     * @param context unfortunately required for the MqttAndroidClient to work
     * @param onConnected since the connection is async, this callback is
     *                    fired when the connection successfully completed
     * @param onConnectionLost this callback is fired when the connection is lost
     */
    public MqttCar(Context context, Runnable onConnected, Runnable onConnectionLost) {
        this.logger = Logger.getLogger("mqtt");
        this.onConnected = onConnected;
        this.onConnectionLost = onConnectionLost;
        this.heartbeatLost = false;

        // Mqtt Config
        String clientId = "AndroidApp";
        String url = MQTT_HOSTNAME;

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
            mqtt.subscribe(Topics.Heartbeat, QoS);
            mqtt.subscribe(Topics.Status.Odometer.Speed, QoS);
            mqtt.subscribe(Topics.Status.Odometer.Distance, QoS);
            mqtt.subscribe(Topics.Sensors.Infrared, QoS);
            mqtt.subscribe(Topics.Sensors.Odometer, QoS);
            mqtt.subscribe(Topics.Sensors.Camera, QoS);
            mqtt.subscribe(Topics.Sensors.Gyroscope, QoS);
            mqtt.subscribe(Topics.Sensors.Ultrasonic, QoS);
            mqtt.subscribe(Topics.Status.Blinkers, QoS);
        } catch (MqttException ex) {
            ex.printStackTrace();
            onConnectionLost.run();
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
        onConnectionLost.run();
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
        onConnectionLost.run();
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
                case Topics.Heartbeat:
                    this.timeRunning.set(Long.parseLong(data));
                    this.lastHeartbeat.set(LocalDateTime.now());
                    break;
                case Topics.Status.Odometer.Speed:
                    this.speed.set(Double.parseDouble(data));
                    break;
                case Topics.Status.Odometer.Distance:
                    this.distance.set(Double.parseDouble(data));
                    break;
                case Topics.Sensors.Infrared:
                    this.ir_distance.set(Double.parseDouble(data));
                    break;
                case Topics.Sensors.Camera:
                    final Bitmap bm = Bitmap.createBitmap(IMAGE_WIDTH, IMAGE_HEIGHT, Bitmap.Config.ARGB_8888);
                    final byte[] payload = message.getPayload();
                    final int[] colors = new int[IMAGE_WIDTH * IMAGE_HEIGHT];

                    for (int ci = 0; ci < colors.length; ++ci) {
                        final int r = payload[3 * ci] & 0xFF;
                        final int g = payload[3 * ci + 1] & 0xFF;
                        final int b = payload[3 * ci + 2] & 0xFF;
                        colors[ci] = Color.rgb(r, g, b);
                    }

                    bm.setPixels(colors, 0, IMAGE_WIDTH, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
                    this.camera.set(bm);
                    break;
                case Topics.Sensors.Gyroscope:
                    this.gyroscopeHeading.set(Double.parseDouble(data));
                    break;
                case Topics.Status.Blinkers:
                    switch (data) {
                        case "left":
                            this.blinkerStatus.set(BlinkerDirection.Left);
                            break;
                        
                        case "right":
                            this.blinkerStatus.set(BlinkerDirection.Right);
                            break;

                        case "off":
                            this.blinkerStatus.set(BlinkerDirection.Off);
                            break;
                    }
                    break;
                case Topics.Sensors.Ultrasonic:
                    this.ultrasoundDistance.set(Double.parseDouble(data));
                    break;
            }

            // For ui updates
            for (Runnable listener : listeners.values()) {
                listener.run();
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
        final long secondsSinceLastHeartbeat = Duration.between(lastHeartbeat.get(), LocalDateTime.now()).getSeconds();

        if (secondsSinceLastHeartbeat > CONNECTION_TIMEOUT && !heartbeatLost) {
            heartbeatLost = true;
            onConnectionLost.run();
        }

        return this.mqtt.isConnected() && !heartbeatLost;
    }

    /**
     * Disconnects the mqtt connection for communicating with the car.
     */
    public void disconnect() {
        this.mqtt.close();
    }


    /**
     * Changes the car speed.
     *
     * @param speed in percentage (between 0 and 100)
     * @throws MqttException when the message cannot be sent to the car
     */
    public void changeSpeed(double speed) throws MqttException {
        mqtt.publish(Topics.Controls.Throttle, new MqttMessage(Double.toString(speed).getBytes()));
    }

    /**
     * Direct the car by publishing a message in a specified topic.
     *
     * @param angle the angle of attack
     * @throws MqttException when the message fails to be transferred successfully to the broker
     */
    public void steerCar(double angle) throws MqttException {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(Double.toString(angle).getBytes());
        mqttMessage.setQos(1);
        mqtt.publish(Topics.Controls.Steering, mqttMessage);
    }

    /**
     * Immediately stops the car.
     * @throws MqttException when the message fails to be transferred successfully to the broker
     */
    public void emergencyStop() throws MqttException {
        String message = "";
        mqtt.publish(Topics.Controls.EmergencyStop, new MqttMessage(message.getBytes()));
    }

    /**
     * Starts the blinker for the given direction.
     *
     * @param direction The direction to blink at
     * @throws MqttException when the message fails to be transferred successfully to the broker
     */
    public void blinkDirection(BlinkerDirection direction) throws MqttException {
        mqtt.publish(Topics.Status.Blinkers, new MqttMessage(direction.toString().toLowerCase().getBytes()));
    }

}
