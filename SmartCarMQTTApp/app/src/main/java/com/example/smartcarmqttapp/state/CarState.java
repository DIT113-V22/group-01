package com.example.smartcarmqttapp.state;

import android.content.Context;

import com.example.smartcarmqttapp.MqttCar;

import java.time.LocalDateTime;

public class CarState {
    // singleton
    protected CarState() {}
    public static final CarState instance = new CarState();

    private MqttCar connectedCar;


    public MqttCar connectCar(Context context, Runnable onConnected, Runnable onConnectionLost) {
        connectedCar = new MqttCar(context, onConnected, onConnectionLost);
        return connectedCar;
    }

    public void disconnectCar() {
        if (connectedCar != null) {
            connectedCar.disconnect();
        }

        connectedCar = null;
    }

    public MqttCar getConnectedCar() {
        return connectedCar;
    }

    public boolean isConnected() {
        return connectedCar != null && connectedCar.isConnected();
    }

    public LocalDateTime getHeartBeat(){
        return connectedCar.lastHeartbeat.get();
    }

    public String getSpeed(){
        return connectedCar.speed.get().toString();
    }

    public String getDistance(){
        return connectedCar.distance.get().toString();
    }

    public String getIRDistance(){return connectedCar.ir_distance.get().toString();}

    public String getGyroHeading(){return connectedCar.gyroscopeHeading.get().toString();}

    public String getUltraSoundDistance(){return connectedCar.ultrasoundDistance.get().toString();}

    
}
