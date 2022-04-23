package com.example.smartcarmqttapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartcarmqttapp.state.CarState;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PracticeDrivingActivity extends AppCompatActivity {


    private Button sensorDisplayButton;
    private BottomNavigationView bottomNavigationView;
    private Dialog sensorDialog;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_driving);

        sensorDisplayButton = findViewById(R.id.sensorDataButton);
        sensorDialog = new Dialog(this);




        sensorDisplayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                openSensorDialog();
            }

        });


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.practiceDriving);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.connectedCar:
                        startActivity(new Intent(getApplicationContext(), ConnectedCarActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.practiceDriving:
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

    private void openSensorDialog() {
        sensorDialog.setContentView(R.layout.sensor_dialog);
        sensorDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sensorDialog.show();
        ImageView closeDialog = sensorDialog.findViewById(R.id.closeDialog);

        TextView speedValue = sensorDialog.findViewById(R.id.speedField);
        TextView distanceValue = sensorDialog.findViewById(R.id.distanceField);
        TextView USValue = sensorDialog.findViewById(R.id.ultrasoundField);
        TextView gyroHeading = sensorDialog.findViewById(R.id.gyroHeadingField);
        TextView infraredValue = sensorDialog.findViewById(R.id.infraredDistance);

        if (CarState.instance.isConnected()){
            // Set speed value
            speedValue.setText(CarState.instance.getSpeed());

            // Set distance
            distanceValue.setText(CarState.instance.getDistance());

            // Set Ultrasound reading
            USValue.setText(CarState.instance.getUltraSoundDistance());

            // Set Gyroscope heading
            gyroHeading.setText(CarState.instance.getGyroHeading());

            // Set Infrared reading
            infraredValue.setText(CarState.instance.getIRDistance());
        } else {
            // Set speed value
            speedValue.setText("NA");

            // Set distance
            distanceValue.setText("NA");

            // Set Ultrasound reading
            USValue.setText("NA");

            // Set Gyroscope heading
            gyroHeading.setText("NA");

            // Set Infrared reading
            infraredValue.setText("NA");
        }



        closeDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sensorDialog.dismiss();
            }
        });

    }
}