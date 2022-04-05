package com.example.smartcarmqttapp;

import android.content.Context;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.*;

public class MQTTFacade {
    private final MqttAndroidClient mMqttAndroidClient;

    /**
     * @param context (application context)
     * @param serverUrl (consists of protocol, host and port)
     * @param clientId (the name the identifies the MQTT server connection)
     */
    public MQTTFacade(Context context, String serverUrl, String clientId) {
        mMqttAndroidClient = new MqttAndroidClient(context, serverUrl, clientId);
    }

    /**
     * @param username of server
     * @param password of server (default = "")
     * @param connectionCallback
     * @param clientCallback
     *
     * Connects the app to the MQTT Broker by setting up a connection on the
     * existing Server URL
     */
    public void connect(String username, String password,
                      IMqttActionListener connectionCallback, 
                      MqttCallback clientCallback) {
        mMqttAndroidClient.setCallback(clientCallback);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(username);
        options.setPassword(password.toCharArray());
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);

        try {
            mMqttAndroidClient.connect(options, null, connectionCallback);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param disconnectionCallback
     *
     * Disconnects application from MQTT Broker
     */
    public void disconnect(IMqttActionListener disconnectionCallback) {
        try {
            mMqttAndroidClient.disconnect(null, disconnectionCallback);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param topic from which information is received
     * @param qos (default = 1)
     * @param subscriptionCallback
     */
    public void subscribe(String topic, int qos, 
                          IMqttActionListener subscriptionCallback) {
        try {
            mMqttAndroidClient.subscribe(topic, qos, null, subscriptionCallback);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param topic that you want to remove from subscription list
     * @param unsubscriptionCallback
     */
    public void unsubscribe(String topic, 
                            IMqttActionListener unsubscriptionCallback) {
        try {
            mMqttAndroidClient.unsubscribe(topic, null, unsubscriptionCallback);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param topic onto which information is being sent
     * @param message containing data relevant to topic
     * @param qos (default = 1)
     * @param publishCallback
     */
    public void publish(String topic, String message, int qos, 
                        IMqttActionListener publishCallback) {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(message.getBytes());
        mqttMessage.setQos(qos);

        try {
            mMqttAndroidClient.publish(topic, mqttMessage, null, publishCallback);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}