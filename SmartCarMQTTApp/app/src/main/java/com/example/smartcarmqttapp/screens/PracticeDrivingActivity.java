package com.example.smartcarmqttapp.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.media.MediaPlayer;
import android.content.Intent;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.example.smartcarmqttapp.MqttCar;
import com.example.smartcarmqttapp.Navigation;
import com.example.smartcarmqttapp.R;
import com.example.smartcarmqttapp.model.AudioPlayer;
import com.example.smartcarmqttapp.model.JoystickView;
import com.example.smartcarmqttapp.state.CarState;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.eclipse.paho.client.mqttv3.MqttException;

import pl.droidsonroids.gif.GifImageView;


public class PracticeDrivingActivity extends AppCompatActivity implements SensorEventListener, JoystickView.JoystickListener {
    public MqttCar controller;
    private int clicks = 0;
    private boolean exit = false;

    public JoystickView joystickView;

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

    private Button toggleDataButton, goButton, brakeButton;
    private Dialog sensorDialog;
    PracticeDrivingActivity zis;

    private SwitchCompat switchCompat;
    private SensorManager sensorManager;
    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];

    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];

    private Switch tilting;
    private boolean crashed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice_driving);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        zis = this;

        // bottomNavigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.practiceDriving);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                alertQuitDriving(() -> Navigation.navigate(zis, item));
                return false;
            }
        });

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

        joystickView = findViewById(R.id.joystickView);

        leftBlinkerArrow = findViewById(R.id.leftBlinkerArrow);
        rightBlinkerArrow = findViewById(R.id.rightBlinkerArrow);

        leftBlinkerButton = findViewById(R.id.leftBlinker);
        rightBlinkerButton= findViewById(R.id.rightBlinker);

        goButton = findViewById(R.id.goButton);
        brakeButton = findViewById(R.id.brakeButton);

        ultraSoundText = findViewById(R.id.udText);
        gyroText = findViewById(R.id.gyroText);
        infraredText = findViewById(R.id.infraText);

        ultraVal = findViewById(R.id.ultrasoundValue);
        gyroVal = findViewById(R.id.gyroValue);
        infraredVal = findViewById(R.id.infraValue);

        tilting = findViewById(R.id.tilting);
        switchCompat = findViewById(R.id.switchButton);
        toggleDataButton = findViewById(R.id.toggleDataBtn);
        sensorDialog = new Dialog(this);

        goButton.setVisibility(View.INVISIBLE);
        brakeButton.setVisibility(View.INVISIBLE);

        dashboard();

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

            if (switchCompat.isChecked()) {
                AudioPlayer.instance.chooseSongerino(getBaseContext(), R.raw.ferrari);
                AudioPlayer.instance.playSound(false);
            }

            controller = CarState.instance.getConnectedCar();

            controller.listeners.put("camera", () -> {
                runOnUiThread(() -> {
                    imageView.setImageBitmap(controller.camera.get());
                });
            });

            controller.listeners.put("crashsounds", () -> {
                    Log.d("crash sound", "crash sound");
                runOnUiThread(() -> {
                    //A crash!!!! Oh no.....
                    double irDistance = CarState.instance.getConnectedCar().ir_distance.get();
                    if (!crashed && irDistance < 10 && irDistance > 0){
                        if (switchCompat.isChecked()) {
                            AudioPlayer.instance.chooseSongerino(getBaseContext(), R.raw.carcrash);
                            AudioPlayer.instance.playSound(false);
                            crashed = true;
                        }
                    }
                    else {
                        crashed = false;
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
            if (switchCompat.isChecked()) {
                AudioPlayer.instance.chooseSongerino(getBaseContext(), R.raw.disconnectedstatic);
                AudioPlayer.instance.playSound(true);
            }

            imageView.setVisibility(View.INVISIBLE);
            screenError.setVisibility(View.VISIBLE);
        }

    }

    public void onClickTilting(View view){
        if(tilting.isChecked()){
            joystickView.setVisibility(View.INVISIBLE);
            goButton.setVisibility(View.VISIBLE);
            brakeButton.setVisibility(View.VISIBLE);
        } else {
            joystickView.setVisibility(View.VISIBLE);
            goButton.setVisibility(View.INVISIBLE);
            brakeButton.setVisibility(View.INVISIBLE);
        }

    }


    public void onClickGo(View view) throws MqttException {
        double initialThrottle = controller.throttle.get();
        double acceleratedThrottle;

        if (switchCompat.isChecked() && !AudioPlayer.instance.getMp().isPlaying()) {
            AudioPlayer.instance.chooseSongerino(getBaseContext(), R.raw.motorhummin);
            AudioPlayer.instance.playSound(true);
        }

        if(initialThrottle == 0) { // car standing still: start driving
            acceleratedThrottle = ControlConstant.STARTING_THROTTLE;
        }else if(Math.abs(initialThrottle) < ControlConstant.MIN_THROTTLE) { // car accelerates to min speed
            acceleratedThrottle = 0;
            AudioPlayer.instance.getMp().pause();
        }else if(initialThrottle > 0) { // car driving: increase speed
            acceleratedThrottle = initialThrottle * ControlConstant.ACCELERATION_FACTOR;
        }else { // car driving backwards: increase speed (decrease speed modulus)
            acceleratedThrottle = initialThrottle / ControlConstant.ACCELERATION_FACTOR;
        }
        acceleratedThrottle = Math.min(acceleratedThrottle, ControlConstant.MAX_THROTTLE); // speed cant be over MAX
        controller.changeSpeed(acceleratedThrottle); // publishes to MQTT
        controller.throttle.set(acceleratedThrottle); // stores current throttle
    }


    public void onClickBrake(View view) throws MqttException {
    controller.changeSpeed(0);
    controller.throttle.set(0.0);
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
            CarState.instance.getConnectedCar().listeners.remove("crashsounds");
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

    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int source) throws MqttException {
        if(controller != null) {
            controller.changeSpeed(100 * yPercent);
            double rotationAngleRadians = Math.atan(- xPercent / yPercent); // x direction flipped since negative x goes CCW (pos angle)
            double rotationAngleDegrees = rotationAngleRadians * 180 / Math.PI;
            controller.steerCar(rotationAngleDegrees);
        }


    }

    /**
     * Constants for determining Car behavior
     */
    public static final class ControlConstant {

        public static final double ACCELERATION_FACTOR = 1.5; // multiplication factor for accelerating
        public static final double DECELERATION_FACTOR = 0.8; // multiplication factor for decelerating

        public static final double TURN_LEFT_ANGLE = 10; // addition angle for turning left
        public static final double TURN_RIGHT_ANGLE = -10; // addition angle for turning right

        public static final double STARTING_THROTTLE = 10;
        public static final double MIN_THROTTLE = 5; // threshold for stopping car when decelerating
        public static final double MAX_THROTTLE = 100; // threshold for accelerating car
    }

    public static final boolean FORCE_UPDATE = false; // upon theoretical data change, immediately updates visible fields
    public static final double GYROSCOPE_OFFSET = 180;

    /**
     * Increases (multiplication) speed of moving car OR begin movement of standing car. Bound to button R.id.upButton
     */
    public void onClickAccelerate(View view) throws MqttException {
        double initialThrottle = controller.throttle.get();
        double acceleratedThrottle;

        if (switchCompat.isChecked() && !AudioPlayer.instance.getMp().isPlaying()) {
            AudioPlayer.instance.chooseSongerino(getBaseContext(), R.raw.motorhummin);
            AudioPlayer.instance.playSound(true);
        }

        if(initialThrottle == 0) { // car standing still: start driving
            acceleratedThrottle = ControlConstant.STARTING_THROTTLE;
        }else if(Math.abs(initialThrottle) < ControlConstant.MIN_THROTTLE) { // car accelerates to min speed
            acceleratedThrottle = 0;
            AudioPlayer.instance.getMp().pause();
        }else if(initialThrottle > 0) { // car driving: increase speed
            acceleratedThrottle = initialThrottle * ControlConstant.ACCELERATION_FACTOR;
        }else { // car driving backwards: increase speed (decrease speed modulus)
            acceleratedThrottle = initialThrottle / ControlConstant.ACCELERATION_FACTOR;
        }
        acceleratedThrottle = Math.min(acceleratedThrottle, ControlConstant.MAX_THROTTLE); // speed cant be over MAX
        controller.changeSpeed(acceleratedThrottle); // publishes to MQTT
        controller.throttle.set(acceleratedThrottle); // stores current throttle
    }

    /**
     * Decreases (multiplication) speed of moving car OR stops movement given speed is below threshold. Bound to button R.id.downButton
     */
    public void onClickDecelerate(View view) throws MqttException {
        double initialThrottle = controller.throttle.get();
        double deceleratedThrottle;

        if (switchCompat.isChecked() && !AudioPlayer.instance.getMp().isPlaying()) {
            AudioPlayer.instance.chooseSongerino(getBaseContext(), R.raw.motorhummin);
            AudioPlayer.instance.playSound(true);
        }

        if(initialThrottle == 0) { // car standing still: start driving backwards
            deceleratedThrottle = -ControlConstant.STARTING_THROTTLE;
        }else if(Math.abs(initialThrottle) < ControlConstant.MIN_THROTTLE){
            deceleratedThrottle = 0;
            AudioPlayer.instance.getMp().pause();
        }else if(initialThrottle > 0) { // car driving: slow down
            deceleratedThrottle = initialThrottle * ControlConstant.DECELERATION_FACTOR;
        }else{ // car driving backwards: speed up backwards
            deceleratedThrottle = initialThrottle / ControlConstant.DECELERATION_FACTOR;
        }
        // speed modulus cant be greater than MAX
        deceleratedThrottle = Math.max(deceleratedThrottle, -ControlConstant.MAX_THROTTLE);
        controller.changeSpeed(deceleratedThrottle); // publish to MQTT
        controller.throttle.set(deceleratedThrottle); // store current throttle
    }

    /**
     * Increases (addition) wheel angle of car. Bound to button R.id.leftButton
     */
    public void onClickRotateLeft(View view) throws MqttException {
        double initialAngle = controller.wheelAngle.get();
        double rotatedAngle = initialAngle + ControlConstant.TURN_LEFT_ANGLE;
        controller.steerCar(rotatedAngle);
        controller.wheelAngle.set(rotatedAngle);

        if(FORCE_UPDATE) controller.gyroscopeHeading.set(rotatedAngle - GYROSCOPE_OFFSET);

    }

    /**
     * Decreases (addition) wheel angle of car. Bound to button R.id.rightButton
     */
    public void onClickRotateRight(View view) throws MqttException {
        double initialAngle = controller.wheelAngle.get();
        double rotatedAngle = initialAngle + ControlConstant.TURN_RIGHT_ANGLE;
        controller.steerCar(rotatedAngle);
        controller.wheelAngle.set(rotatedAngle);

        if(FORCE_UPDATE) controller.gyroscopeHeading.set(rotatedAngle - GYROSCOPE_OFFSET);

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
     * Engages Emergency Stop and stops car. Bound to button ###
     * UI Implementation left for Milestone 6: Manual Driving
     */
    public void onClickEmergencyStop(View view) throws MqttException {
        controller.emergencyStop();
        AudioPlayer.instance.getMp().pause();
    }

    /**
     * Car sound can be enabled or disabled
     */
    public void onClickSoundOn(View view) {
        if (switchCompat.isChecked()) {
            AudioPlayer.instance.getMp().start();
        }
        else {
            AudioPlayer.instance.getMp().pause();
        }
    }

    protected void alertQuitDriving(Runnable onQuit) {
        if (CarState.instance.isConnected()) {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setMessage("Are you really gonna wuss out on me?")
                    .setNegativeButton("Just Kidding!", (theDialog, id) -> {})
                    .setPositiveButton("\uD83D\uDE1E yeahhh..", (theDialog, id) -> {
                        onQuit.run();
                    })
                    .create();

            dialog.setTitle("Leaving Driver's Seat");
            dialog.setIcon(R.drawable.ic_baseline_follow_the_signs_24);
            dialog.show();
        }
        else {
            onQuit.run();
        }
    }

    @Override
    public void onBackPressed() {
        alertQuitDriving(() -> {
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            overridePendingTransition(0, 0);
        });
    }
}
