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

    public double getSpeed(){
        return connectedCar.speed.get();
    }

    public double getDistance(){
        return connectedCar.distance.get();
    }

    public double getIRDistance(){return connectedCar.ir_distance.get();}

    public double getGyroHeading(){return connectedCar.gyroscopeHeading.get();}

    public double getUltraSoundDistance(){return connectedCar.ultrasoundDistance.get();}

    
}
