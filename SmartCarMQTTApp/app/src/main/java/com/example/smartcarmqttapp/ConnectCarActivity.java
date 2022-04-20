package com.example.smartcarmqttapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.databinding.ObservableField;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcarmqttapp.state.CarState;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class ConnectCarActivity extends AppCompatActivity {

    private boolean isConnected = false;
    private final LocalTime currentTimeStamp = LocalTime.now();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_car);
        toggleVisibleCard(CarState.instance.isConnected());


    }

    public void connectCar(View view) {
        CarState.instance.connectCar(
            getBaseContext(),
            () -> { // on connected
                runOnUiThread(() -> {
                    timeRunning(view);
                    toggleVisibleCard(true);
                    Toast.makeText(
                        getBaseContext(),
                        "🎉 Car successfully connected!",
                        Toast.LENGTH_SHORT
                    ).show();
                });
            },
            () -> { // on connection lost
                runOnUiThread(() -> {
                    toggleVisibleCard(false);
                    Toast.makeText(
                            getBaseContext(),
                            "💥 Car connection lost!",
                            Toast.LENGTH_LONG
                    ).show();
                });
            }
        );
    }

    public void disconnectCar(View view) {
        CarState.instance.disconnectCar();
        toggleVisibleCard(false);
        Toast.makeText(
            getBaseContext(),
            "🎉 Car successfully disconnected!",
            Toast.LENGTH_SHORT
        ).show();
    }

    private void toggleVisibleCard(boolean isConnected) {
        CardView connectedCard = findViewById(R.id.connectedCard);
        CardView disconnectedCard = findViewById(R.id.disconnectedCard);
        connectedCard.setVisibility(View.GONE);
        disconnectedCard.setVisibility(View.GONE);

        this.isConnected = isConnected;
        CardView visibleCard = this.isConnected ? connectedCard : disconnectedCard;
        visibleCard.setVisibility(View.VISIBLE);
    }

    /**
     * @param view
     * @throws InterruptedException
     * Meant to lead users to the manual driving app via the connection card
     * that appears upon successfully connecting a car for the first time
     */
    public void goToDriving(View view) throws InterruptedException {
        if(CarState.instance.isConnected()){
            Toast.makeText(
                    getBaseContext(),
                    "🚗 Going to manual driving",
                    Toast.LENGTH_SHORT
            ).show();
            Thread.sleep(1200);
            //TODO Edit to switch to manual driving screen once we have merged the Code:
            //startActivity(new Intent(ConnectCarActivity.this, TestManualDriving.class));
        }
        else{
            Toast.makeText(
                getBaseContext(),
                "💥 Car not connected 💥",
                Toast.LENGTH_LONG
            ).show();
        }
    }


    public long timeRunning(View view) {

        TextView timeRunningValue = findViewById(R.id.timeRunningValue);
        TextView lastUpdate = findViewById(R.id.lastUpdateValue);

        //timestamp of when it you gain a connection


        /*
        if (CarState.instance.isConnected()) {
            //time between current time (doesn't change) and LocalDateTime (changes like a heartbeat)
            timeRunningValue.setText(Duration.between(currentTime, car.lastHeartbeat.get()));
            return 1;
        } else {
            return 0;
        }
        */
        Thread newThread = new Thread(){
            @Override
            public void run(){
                while(!isInterrupted()){
                    try{
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                timeRunningValue.setText(Duration.between(currentTimeStamp, LocalTime.now()).toString());
                                lastUpdate.setText(LocalTime.now().toString());
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        };

        newThread.start();
        //while (CarState.instance.isConnected())

            //Duration d = Duration.between(currentTimeStamp, LocalTime.now());
            //comparing timestamp with current time -> time between == how long connection has been active for
            //timeRunningValue.setText(Duration.between(currentTimeStamp, LocalTime.now()).toString());
            //lastUpdate.setText(LocalTime.now().toString());
        return 0;
    }
}