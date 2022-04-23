package com.example.smartcarmqttapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.Observable;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.eclipse.paho.client.mqttv3.MqttException;

public class PracticeDrivingActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private MqttCar controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_driving);

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

        controller = new MqttCar(getApplicationContext(), () -> {
            try {
                controller.changeSpeed(ControlConstant.INITIAL_SPEED);
                controller.steerCar(ControlConstant.INITIAL_ANGLE);
            } catch (MqttException ex) {
                ex.printStackTrace();
            }
        }, null);

    }

    /**
     * Constants for determining Car behavior
     */
    public static final class ControlConstant {

        enum ChangeMode {
            ADDITION, MULTIPLICATION;
        }

        public static final double STARTING_SPEED = 10; // new speed of car when accelerating with no speed (percentage integer)
        public static final double INITIAL_SPEED = 0; // speed of car upon initialization
        public static final double INITIAL_ANGLE = 0; // angle of car upon initialization

        public static final double ACCELERATION_FACTOR = 1.1; // multiplication factor for accelerating
        public static final double DECELERATION_FACTOR = 0.9; // multiplication factor for decelerating

        public static final double TURN_LEFT_ANGLE = 10; // addition angle for turning left
        public static final double TURN_RIGHT_ANGLE = -10; // addition angle for turning right

        public static final double MIN_SPEED = 0.05; // threshold for stopping car when decelerating

        public static final ChangeMode ANGLE_CHANGE = ChangeMode.ADDITION;
        public static final ChangeMode SPEED_CHANGE = ChangeMode.MULTIPLICATION;

    }

    /**
     * Prefixes for displaying Sensor readings
     */
    public static final class SensorString {
        public static final String SPEED = "Car Speed: ";
        public static final String DISTANCE = "Total Distance: ";
        public static final String GYROSCOPE = "Gyroscope Heading: ";
        public static final String BLINKER = "Blinker Status: ";
        public static final String INFRARED = "Infrared Distance: ";
        public static final String ULTRASONIC = "Ultrasonic Distance: ";
    }

    public static final boolean FORCE_UPDATE = false; // upon theoretical data change, immediately updates visible fields
    public static final double GYROSCOPE_OFFSET = 180;

    /* ToDo:
     * Bind buttons to methods
     * Add method bodies
     * Update text views
     * Create UI
     * If doesn't work, move debugging statements before controller access
     * Test functionality
     * VERY IMPORTANT: #changeSpeed takes throttle percentage integer (20), but speed.get() returns m/s.
     * When calculating accelerated speed, use map for (speed in m/s) -> (speed in %) to throttle properly.
     */

    /**
     * Increases (multiplication) speed of moving car OR begin movement of standing car. Bound to button R.id.upButton
     */
    public void onClickAccelerate(View view) throws MqttException {
        double initialSpeed = controller.speed.get(); // returns 0.138
        initialSpeed = getThrottleFromAbsoluteSpeed(initialSpeed); // returns 10
        double acceleratedSpeed = initialSpeed == 0 ? ControlConstant.STARTING_SPEED : initialSpeed * ControlConstant.ACCELERATION_FACTOR;
        controller.changeSpeed(acceleratedSpeed);

        if(FORCE_UPDATE) controller.speed.set(acceleratedSpeed);

        // Debugging
        System.out.println("Accelerating from " + initialSpeed + " % to " + acceleratedSpeed + " %");

        /* unopinionated approach to changing speed and angle by allowing user to select change mode.
        double acceleratedSpeed =
                ControlConstant.SPEED_CHANGE == ControlConstant.ChangeMode.MULTIPLICATION ?
                        initialSpeed * ControlConstant.ACCELERATION_FACTOR :
                        initialSpeed + ControlConstant.ACCELERATION_FACTOR;
         */
    }

    /**
     * Decreases (multiplication) speed of moving car OR stops movement given speed is below threshold. Bound to button R.id.downButton
     */
    public void onClickDecelerate(View view) throws MqttException {
        double initialSpeed = controller.speed.get();
        initialSpeed = getThrottleFromAbsoluteSpeed(initialSpeed);
        double deceleratedSpeed = initialSpeed > ControlConstant.MIN_SPEED ? initialSpeed * ControlConstant.DECELERATION_FACTOR : 0;
        controller.changeSpeed(deceleratedSpeed);

        if(FORCE_UPDATE) controller.speed.set(deceleratedSpeed);

        // Debugging
        System.out.println("Accelerating from " + initialSpeed + " % to " + deceleratedSpeed + " %");
    }

    /**
     * Increases (addition) wheel angle of car. Bound to button R.id.leftButton
     */
    public void onClickRotateLeft(View view) throws MqttException {
//        double initialAngle = controller.gyroscopeHeading.get();
        double initialAngle = controller.wheelAngle.get();
        double rotatedAngle = initialAngle + ControlConstant.TURN_LEFT_ANGLE;
        controller.steerCar(rotatedAngle);
        controller.wheelAngle.set(rotatedAngle);

        if(FORCE_UPDATE) controller.gyroscopeHeading.set(rotatedAngle - GYROSCOPE_OFFSET);

        // Debugging
        System.out.println("Rotating Right from " + initialAngle + " deg to " + rotatedAngle + " deg");
    }

    /**
     * Decreases (addition) wheel angle of car. Bound to button R.id.rightButton
     */
    public void onClickRotateRight(View view) throws MqttException {
//        double initialAngle = controller.gyroscopeHeading.get();
        double initialAngle = controller.wheelAngle.get();
        double rotatedAngle = initialAngle + ControlConstant.TURN_RIGHT_ANGLE;
        controller.steerCar(rotatedAngle);
        controller.wheelAngle.set(rotatedAngle);

        if(FORCE_UPDATE) controller.gyroscopeHeading.set(rotatedAngle - GYROSCOPE_OFFSET);

        // Debugging
        System.out.println("Rotating Left from " + initialAngle + " deg to " + rotatedAngle + " deg");
    }

    /**
     * Begins blinking left. Bound to button ###
     * UI Implementation left for Milestone 6: Manual Driving
     */
    public void onClickBlinkLeft(View view) throws MqttException {
        controller.blinkDirection(MqttCar.BlinkerDirection.Left);

        if(FORCE_UPDATE) controller.blinkerStatus.set(MqttCar.BlinkerDirection.Left);
    }

    /**
     * Begins blinking right. Bound to button ###
     * UI Implementation left for Milestone 6: Manual Driving
     */
    public void onClickBlinkRight(View view) throws MqttException {
        controller.blinkDirection(MqttCar.BlinkerDirection.Right);

        if(FORCE_UPDATE) controller.blinkerStatus.set(MqttCar.BlinkerDirection.Right);
    }

    /**
     * Stops blinking. Bound to button ###
     * UI Implementation left for Milestone 6: Manual Driving
     */
    public void onClickBlinkOff(View view) throws MqttException {
        controller.blinkDirection(MqttCar.BlinkerDirection.Off);

        if(FORCE_UPDATE) controller.blinkerStatus.set(MqttCar.BlinkerDirection.Off);
    }

    /**
     * Engages Emergency Stop and stops car. Bound to button ###
     * UI Implementation left for Milestone 6: Manual Driving
     */
    public void onClickEmergencyStop(View view) throws MqttException {
        controller.emergencyStop();
    }

    // Utility methods

    public double getThrottleFromAbsoluteSpeed(double absoluteSpeed) {
        // Throttle and absolute speed are approximately linearly correlated with k=1.8
        // To obtain percentage integer from ratio, multiply by 100
        return absoluteSpeed / 1.8 * 100;
    }


}