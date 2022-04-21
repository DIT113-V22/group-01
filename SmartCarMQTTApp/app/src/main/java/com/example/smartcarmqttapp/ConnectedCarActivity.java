package com.example.smartcarmqttapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcarmqttapp.state.CarState;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.Duration;
import java.time.LocalTime;

public class ConnectedCarActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private static boolean isConnected = false;
    private final LocalTime currentTimeStamp = LocalTime.now();
    private static Thread newThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_car);
        toggleVisibleCard(isConnected);


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.connectedCar);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.connectedCar:
                        return true;

                    case R.id.practiceDriving:
                        startActivity(new Intent(getApplicationContext(), PracticeDrivingActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.practiceTheory:
                        startActivity(new Intent(getApplicationContext(), PracticeTheoryActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.aboutUs:
                        startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
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
        if(isConnected){
            Toast.makeText(
                    getBaseContext(),
                    "🚗 Going to manual driving",
                    Toast.LENGTH_SHORT
            ).show();
            Thread.sleep(1000);
            startActivity(new Intent(ConnectedCarActivity.this, PracticeDrivingActivity.class));
        }
        else{
            Toast.makeText(
                    getBaseContext(),
                    "💥 Car not connected 💥",
                    Toast.LENGTH_LONG
            ).show();
        }
    }


    public final void timeRunning(View view) {

        TextView timeRunningValue = findViewById(R.id.timeRunningValue);
        TextView lastUpdate = findViewById(R.id.lastUpdateValue);

        newThread = new Thread() {
            @Override
            public void run(){
                while (!isInterrupted()) {
                    try {
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
    }
}