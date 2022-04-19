package com.example.smartcarmqttapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.smartcarmqttapp.state.CarState;

public class ConnectCarActivity extends AppCompatActivity {

    private boolean isConnected = false;

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
}