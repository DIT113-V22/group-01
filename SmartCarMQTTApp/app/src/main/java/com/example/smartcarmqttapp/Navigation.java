package com.example.smartcarmqttapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smartcarmqttapp.screens.AboutUsActivity;
import com.example.smartcarmqttapp.screens.ConnectedCarActivity;
import com.example.smartcarmqttapp.screens.HomeActivity;
import com.example.smartcarmqttapp.screens.PracticeDrivingActivity;
import com.example.smartcarmqttapp.screens.quiz.PracticeTheoryActivity;
import com.example.smartcarmqttapp.state.CarState;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Navigation {

    public static void initializeNavigation(AppCompatActivity activity, int selectedItem) {
        BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(selectedItem);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return navigate(activity, item);
            }
        });
    }

    public static boolean navigate(AppCompatActivity activity, @NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.connectedCar:
                activity.startActivity(new Intent(activity, ConnectedCarActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                activity.overridePendingTransition(0, 0);
                return true;

            case R.id.practiceDriving:
                if (CarState.instance.isConnected()) {
                    activity.startActivity(new Intent(activity, PracticeDrivingActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    activity.overridePendingTransition(0, 0);
                    return true;
                }

                AlertDialog dialog = new AlertDialog.Builder(activity)
                        .setMessage("Would you like to connect to it?")
                        .setPositiveButton("Connect my car!", (theDialog, id) -> {
                            activity.startActivity(new Intent(activity, PracticeDrivingActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            activity.overridePendingTransition(0, 0);
                        })
                        .create();

                dialog.setTitle("Car Not Connected 😔");
                dialog.show();
                return false;

            case R.id.home:
                activity.startActivity(new Intent(activity, HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                activity.overridePendingTransition(0, 0);
                return true;

            case R.id.practiceTheory:
                activity.startActivity(new Intent(activity, PracticeTheoryActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                activity.overridePendingTransition(0, 0);
                return true;

            case R.id.aboutUs:
                activity.startActivity(new Intent(activity, AboutUsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                activity.overridePendingTransition(0, 0);
                return true;

            default:
                return false;
        }
    }
}
