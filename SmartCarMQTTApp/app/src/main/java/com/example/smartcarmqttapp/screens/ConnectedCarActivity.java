package com.example.smartcarmqttapp.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcarmqttapp.MqttCar;
import com.example.smartcarmqttapp.Navigation;
import com.example.smartcarmqttapp.R;
import com.example.smartcarmqttapp.state.CarState;

public class ConnectedCarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_car);
        toggleVisibleCard(CarState.instance.isConnected());
        Navigation.initializeNavigation(this, R.id.connectedCar);
    }

    @Override
    protected void onStop() {
        if (CarState.instance.isConnected()) {
            CarState.instance.getConnectedCar().listeners.remove("connection ui");
        }
        super.onStop();
    }

    public void connectCar(View view) {
        CarState.instance.connectCar(
                getBaseContext(),
                () -> { // on connected
                    runOnUiThread(() -> {
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

        if (isConnected) {
            addUiUpdateListener();
        }
        else {
            if (CarState.instance.isConnected()) CarState.instance.getConnectedCar().listeners.clear();
        }

        CardView visibleCard = isConnected ? connectedCard : disconnectedCard;
        visibleCard.setVisibility(View.VISIBLE);
    }

    /**
     * @param view
     * @throws InterruptedException
     * Meant to lead users to the manual driving app via the connection card
     * that appears upon successfully connecting a car for the first time
     */
    public void goToDriving(View view) throws InterruptedException {
        if (CarState.instance.isConnected()) {
            Toast.makeText(
                    getBaseContext(),
                    "🚗 Going to manual driving",
                    Toast.LENGTH_SHORT
            ).show();
            Thread.sleep(1000);

            startActivity(
                new Intent(ConnectedCarActivity.this, PracticeDrivingActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            );
        }
        else{
            Toast.makeText(
                    getBaseContext(),
                    "💥 Car not connected 💥",
                    Toast.LENGTH_LONG
            ).show();
        }
    }


    public final void addUiUpdateListener() {
        CarState.instance.getConnectedCar().listeners.put("connection ui", () -> {
            runOnUiThread(() -> {
                TextView timeRunningValue = findViewById(R.id.timeRunningValue);
                TextView lastUpdate = findViewById(R.id.lastUpdateValue);
                MqttCar car = CarState.instance.getConnectedCar();
                long milis = car.timeRunning.get() / 1000;
                timeRunningValue.setText(String.valueOf(milis));
                lastUpdate.setText(car.lastHeartbeat.get().toString().split("T")[1].split("\\.")[0]);
            });
        });
    }
}