package com.example.smartcarmqttapp.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.media.MediaPlayer;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.smartcarmqttapp.MqttCar;
import com.example.smartcarmqttapp.Navigation;
import com.example.smartcarmqttapp.R;
import com.example.smartcarmqttapp.model.AudioPlayer;
import com.example.smartcarmqttapp.state.CarState;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifImageView;


public class PracticeDrivingActivity extends AppCompatActivity implements SensorEventListener {
    public MqttCar controller;
    private int clicks = 0;
    private boolean exit = false;

    public ImageView leftBlinkerArrow, rightBlinkerArrow;
    public CardView leftBlinkerButton, rightBlinkerButton;
    public ImageView imageView;

    private TextView ultraSoundText;
    private TextView gyroText;
    private TextView infraredText;

    private TextView ultraVal;
    private TextView gyroVal;
    private TextView infraredVal;

    //For error screen when the car is not connected
    public GifImageView screenError;

    private Button toggleDataButton;
    private Dialog sensorDialog;

    private MediaPlayer mp;

    private SwitchCompat switchCompat;
    private SensorManager sensorManager;
    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];

    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];

    private Switch tilting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_driving);
        Navigation.initializeNavigation(this, R.id.practiceDriving);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        AudioPlayer.instance.createMP();
        //play sound file and set looping to true

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("\nUse the arrow keys to maneuver the car \n \n" +
                        "Red button is an emergency stop \n \n" +
                        "Pressing the toggle data gives some extra car data ;)")
                .setPositiveButton("Time to drive!", (theDialog, id) -> {})
                .create();

        dialog.setTitle("Quick Hints on how to drive the car");
        dialog.show();

        leftBlinkerArrow = findViewById(R.id.leftBlinkerArrow);
        rightBlinkerArrow = findViewById(R.id.rightBlinkerArrow);

        leftBlinkerButton = findViewById(R.id.leftBlinker);
        rightBlinkerButton= findViewById(R.id.rightBlinker);

        ultraSoundText = findViewById(R.id.udText);
        gyroText = findViewById(R.id.gyroText);
        infraredText = findViewById(R.id.infraText);

        ultraVal = findViewById(R.id.ultrasoundValue);
        gyroVal = findViewById(R.id.gyroValue);
        infraredVal = findViewById(R.id.infraValue);

        tilting = findViewById(R.id.tilting);
        toggleDataButton = findViewById(R.id.toggleDataBtn);
        sensorDialog = new Dialog(this);

        dashboard();
        if (CarState.instance.isConnected()) {
            try {
                CarState.instance.getConnectedCar().changeSpeed(25.0);
            }
            catch(Exception ex) {
                // ignore
            }
        }

        toggleDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clicks == 0){
                    exit = false;
                    clicks++;

                    if (CarState.instance.isConnected()) {
                        CarState.instance.getConnectedCar().listeners.put("data", () -> {
                            if(!exit){
                                ultraSoundText.setText("Ultrasound Distance (cm):");
                                gyroText.setText("Gyroscope Heading (deg):");
                                infraredText.setText("Infrared Distance (cm):");
                                // Set Ultrasound reading
                                ultraVal.setText(CarState.instance.getUltraSoundDistance());

                                // Set Gyroscope heading
                                gyroVal.setText(CarState.instance.getGyroHeading());

                                // Set Infrared reading
                                infraredVal.setText(CarState.instance.getIRDistance());
                            }
                            else{
                                ultraSoundText.setText("");
                                gyroText.setText("");
                                infraredText.setText("");
                                ultraVal.setText("");
                                gyroVal.setText("");
                                infraredVal.setText("");
                            }
                        });
                    }
                }
                else {
                    exit = true;
                    clicks = 0;

                    if (CarState.instance.isConnected()) {
                        CarState.instance.getConnectedCar().listeners.remove("data");
                    }

                    ultraSoundText.setText("");
                    gyroText.setText("");
                    infraredText.setText("");
                    ultraVal.setText("");
                    gyroVal.setText("");
                    infraredVal.setText("");
                }
            }
        });

        imageView = findViewById(R.id.cameraView);
        screenError = findViewById(R.id.screenError);

        if (CarState.instance.isConnected()) {
            imageView.setVisibility(View.VISIBLE);
            screenError.setVisibility(View.GONE);

            AudioPlayer.instance.chooseSongerino(getBaseContext(), R.raw.ferrari);
            AudioPlayer.instance.playSound(true);

            controller = CarState.instance.getConnectedCar();

            controller.listeners.put("camera", () -> {
                    Log.d("ui update", "ui update");
                runOnUiThread(() -> {
                    imageView.setImageBitmap(controller.camera.get());
                });
            });

            controller.listeners.put("crashsounds", () -> {
                    Log.d("crash sound", "crash sound");
                runOnUiThread(() -> {
                    //A crash!!!! Oh no.....
                    double irDistance = Double.parseDouble(CarState.instance.getIRDistance());
                    if(irDistance < 10 && irDistance > 0){
                        if(AudioPlayer.instance.getMp().isPlaying())
                            AudioPlayer.instance.chooseSongerino(getBaseContext(), R.raw.carcrash);
                            AudioPlayer.instance.playSound(false);
                    }
                });
            });
            try {
                controller.steerCar(0);
            }
            catch (Exception ex) {
                // ignore
            }
        }
        else {
            AudioPlayer.instance.chooseSongerino(getBaseContext(), R.raw.disconnectedstatic);
            AudioPlayer.instance.playSound(true);
            imageView.setVisibility(View.INVISIBLE);
            screenError.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Get updates from the accelerometer and magnetometer at a constant rate.
        // To make batch operations more efficient and reduce power consumption,
        // provide support for delaying updates to the application.
        //
        // In this example, the sensor reporting delay is small enough such that
        // the application receives an update before the system checks the sensor
        // readings again.
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }
        Sensor magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magneticField != null) {
            sensorManager.registerListener(this, magneticField,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Don't receive any more updates from either sensor.
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onStop() {
        AudioPlayer.instance.stopSound();

        if (CarState.instance.isConnected()) {
            CarState.instance.getConnectedCar().listeners.remove("dashboard");
            CarState.instance.getConnectedCar().listeners.remove("camera");
        }

        super.onStop();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Ignore
    }

    // Get readings from accelerometer and magnetometer. To simplify calculations,
    // consider storing these readings as unit vectors.
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, accelerometerReading,
                    0, accelerometerReading.length);
        }
        else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, magnetometerReading,
                    0, magnetometerReading.length);
        }

        updateOrientationAngles();
    }


    // Compute the three orientation angles based on the most recent readings from
    // the device's accelerometer and magnetometer.
    public void updateOrientationAngles() {
        // Update rotation matrix, which is needed to update orientation angles.
        SensorManager.getRotationMatrix(rotationMatrix, null,
                accelerometerReading, magnetometerReading);

        SensorManager.getOrientation(rotationMatrix, orientationAngles);
        // "orientationAngles" now has up-to-date information.

        try {
            long angle = Math.round(-orientationAngles[2] * GYROSCOPE_OFFSET);
            if (tilting.isChecked()) CarState.instance.getConnectedCar().steerCar(angle);
        }
        catch (Exception ex) {
            // ignore
        }
    }

    private void dashboard() {
        TextView speedVal = findViewById(R.id.speedValue);
        TextView distanceVal = findViewById(R.id.distanceValue);

        if (CarState.instance.isConnected()) {
            CarState.instance.getConnectedCar().listeners.put("dashboard", () -> {
                speedVal.setText(CarState.instance.getSpeed() + " m/s");
                distanceVal.setText(CarState.instance.getDistance() + " cm");
            });
        }
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

        Animation animation = AnimationUtils.loadAnimation(
                getApplicationContext(),
                R.anim.blinker
        );

        if (clicks == 1) {
            clicks--;
            leftBlinkerArrow.clearAnimation();
        }else{
            leftBlinkerArrow.startAnimation(animation);
            rightBlinkerArrow.clearAnimation();
            clicks++;
        }
        if(FORCE_UPDATE) controller.blinkerStatus.set(MqttCar.BlinkerDirection.Right);
    }

    /**
     * Begins blinking right. Bound to button ###
     * UI Implementation left for Milestone 6: Manual Driving
     */
    public void onClickBlinkRight(View view) throws MqttException {
        controller.blinkDirection(MqttCar.BlinkerDirection.Right);

        Animation animation = AnimationUtils.loadAnimation(
                getApplicationContext(),
                R.anim.blinker
        );

        if (clicks == 1) {
            clicks--;
            rightBlinkerArrow.clearAnimation();
        }else{
            rightBlinkerArrow.startAnimation(animation);
            leftBlinkerArrow.clearAnimation();
            clicks++;
        }
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

    /**
     * Car sound can be enabled or disabled
     */
    public void onClickSoundOn(View view) {
        switchCompat = findViewById(R.id.switchButton);

        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchCompat.isChecked())
                    AudioPlayer.instance.playSound(true);
                else
                    AudioPlayer.instance.getMp().pause();
            }
        });
    }
}