package com.example.smartcarmqttapp;

import static org.junit.Assert.*;
import static com.example.smartcarmqttapp.IntegrationTestUtils.*;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.HashMap;
import java.util.Map;

/**
 * Tests for checking if the communication with the car works.
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MqttCarTest {

    /**
     * Constants for mapping throttle percentage to car speed in the SMCE emulator, figured out by testing.
     */
    public static final Map<Double, Double> ThrottleSpeedMap = new HashMap<>();

    static {
        ThrottleSpeedMap.put(  0.0, 0.0);
        ThrottleSpeedMap.put( 10.0, 0.138);
        ThrottleSpeedMap.put( 20.0, 0.326);
        ThrottleSpeedMap.put( 30.0, 0.510);
        ThrottleSpeedMap.put( 40.0, 0.703);
        ThrottleSpeedMap.put( 50.0, 0.889);
        ThrottleSpeedMap.put( 60.0, 1.083);
        ThrottleSpeedMap.put( 70.0, 1.270);
        ThrottleSpeedMap.put( 80.0, 1.464);
        ThrottleSpeedMap.put( 90.0, 1.651);
        ThrottleSpeedMap.put(100.0, 1.846);
        ThrottleSpeedMap.put(  -0.0, 0.0);
        ThrottleSpeedMap.put( -10.0, 0.138);
        ThrottleSpeedMap.put( -20.0, 0.326);
        ThrottleSpeedMap.put( -30.0, 0.510);
        ThrottleSpeedMap.put( -40.0, 0.703);
        ThrottleSpeedMap.put( -50.0, 0.889);
        ThrottleSpeedMap.put( -60.0, 1.083);
        ThrottleSpeedMap.put( -70.0, 1.270);
        ThrottleSpeedMap.put( -80.0, 1.464);
        ThrottleSpeedMap.put( -90.0, 1.651);
        ThrottleSpeedMap.put(-100.0, 1.846);
    }

    /**
     * Fault tolerance (absolute values)
     */
    public static final class FaultTolerance {
        public static final double Speed = 0.01;
        public static final double Distance = 0.1;
        public static final double Heading = 3.6;
    }

    private static Context appContext;
    private static MqttCar car;

    /**
     * Tries to setup the connection to a mqtt car.
     *
     * @throws Exception when the car setup fails
     */
    @BeforeClass
    public static void setupCar() throws Exception {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        car = new MqttCar(appContext, () -> {}, () -> {});

        if (!eventually(() -> car.isConnected())) {
            throw new Exception("Failed to connect to the car!");
        }
    }

    @Test
    public void _01_GivenAMovingCar_WhenSpeedingForSomeTime_ThenTheDistanceShouldMatch() throws Exception {
        // Given:
        final double movingSpeed = 100;
        car.changeSpeed(movingSpeed);
        assertEventually(
                "Given: A moving car",
                () -> acceptable(car.speed.get(), ThrottleSpeedMap.get(movingSpeed), FaultTolerance.Speed),
                Timeout.Long
        );

        // When:
        final double expectedDistance = ThrottleSpeedMap.get(movingSpeed) * 100 /* cm */;
        try {
            car.changeSpeed(0);
            assertTrue("When: Speeding for some time", true);
        }
        catch (Exception ex) {
            assertTrue("When: Speeding for some time", false);
        }

        // Then:
        assertEventually(
            "Then: the distance should match",
            () -> car.distance.get() > expectedDistance,
            Timeout.Short
        );
    }

    @Test
    public void _02_GivenAStandingCar_WhenSpeedingFully_ThenTheSpeedShouldMatch() throws Exception {
        // Given:
        final double standingSpeed = 0;
        try {
            car.changeSpeed(standingSpeed);
            assertEventually(
                    "Given: A standing car",
                    () -> acceptable(car.speed.get(), ThrottleSpeedMap.get(standingSpeed), FaultTolerance.Speed),
                    Timeout.Long
            );
        }
        catch (Exception ex) {
            assertTrue("Given: A standing car", false);
            throw ex;
        }

        // When:
        final double expectedSpeed = 100;
        try {
            car.changeSpeed(expectedSpeed);
            assertTrue("When: Speeding fully", true);
        }
        catch (Exception ex) {
            assertTrue("When: Speeding fully", false);
            throw ex;
        }

        // Then:
        assertEventually(
                "Then: The speed should match",
                () -> acceptable(car.speed.get(), ThrottleSpeedMap.get(expectedSpeed), FaultTolerance.Speed),
                Timeout.Long
        );
    }

    @Test
    public void _03_GivenAStandingCar_WhenSpeedingBackwards_ThenTheSpeedShouldMatch() throws Exception {
        // Given:
        final double standingSpeed = 0.0;
        try {
            car.changeSpeed(standingSpeed);
            assertEventually(
                    "Given: A standing car",
                    () -> acceptable(car.speed.get(), ThrottleSpeedMap.get(standingSpeed), FaultTolerance.Speed),
                    Timeout.Long
            );
        }
        catch (Exception e) {
            assertTrue("Given: A standing car", false);
            throw e;
        }

        // When:
        final double expectedSpeed = -100;
        try {
            car.changeSpeed(expectedSpeed);
            assertTrue("When: Speeding fully backwards", true);
        }
        catch (Exception e) {
            assertTrue("When: Speeding fully backwards", false);
            throw e;
        }

        // Then:
        assertEventually(
                "Then: The speed should match",
                () -> acceptable(car.speed.get(), ThrottleSpeedMap.get(expectedSpeed), FaultTolerance.Speed),
                Timeout.Long
        );
    }

    @Test
    public void _04_GivenAMovingCar_WhenSlowingDown_ThenTheSpeedShouldMatch() throws Exception {
        // Given:
        final double movingSpeed = 80;
        try {
            car.changeSpeed(movingSpeed);
            assertEventually(
                    "Given: A moving car",
                    () -> acceptable(car.speed.get(), ThrottleSpeedMap.get(movingSpeed), FaultTolerance.Speed),
                    Timeout.Long
            );
        }
        catch (Exception e) {
            assertTrue("Given: A moving car", false);
            throw e;
        }

        // When:
        final double expectedSpeed = 30;
        try {
            car.changeSpeed(expectedSpeed);
            assertTrue("When: Slowing down", true);
        }
        catch (Exception e) {
            assertTrue("When: Slowing down", false);
            throw e;
        }

        // Then:
        assertEventually(
                "Then: The speed should match",
                () -> acceptable(car.speed.get(), ThrottleSpeedMap.get(expectedSpeed), FaultTolerance.Speed),
                Timeout.Long
        );
    }

    @Test
    public void _05_GivenAMovingCar_WhenAccelerating_ThenTheSpeedShouldMatch() throws Exception {
        // Given:
        final double movingSpeed = 20;
        try {
            car.changeSpeed(movingSpeed);
            assertEventually(
                    "Given: A moving car",
                    () -> acceptable(car.speed.get(), ThrottleSpeedMap.get(movingSpeed), FaultTolerance.Speed),
                    Timeout.Long
            );
        }
        catch (Exception e) {
            assertTrue("Given: A moving car", false);
            throw e;
        }

        // When:
        final double expectedSpeed = 70;
        try {
            car.changeSpeed(expectedSpeed);
            assertTrue("When: Accelerating", true);
        }
        catch (Exception e) {
            assertTrue("When: Accelerating", false);
            throw e;
        }

        // Then:
        assertEventually(
                "Then: The speed should match",
                () -> acceptable(car.speed.get(), ThrottleSpeedMap.get(expectedSpeed), FaultTolerance.Speed),
                Timeout.Long
        );
    }

    @Test
    public void _06_GivenAMovingCar_WhenSteering_ThenTheAngleShouldMatch() throws Exception {
        // Given:
        final double movingSpeed = 20;
        car.changeSpeed(movingSpeed);
        assertEventually(
                "Given: A moving car",
                () -> acceptable(car.speed.get(), ThrottleSpeedMap.get(movingSpeed), FaultTolerance.Speed),
                Timeout.Long
        );

        // When:
        final double expectedAngle = 10;
        try {
            car.steerCar(expectedAngle);
            assertTrue("When: Steering", true);
        }
        catch (Exception ex) {
            assertTrue("When: Steering", false);
        }

        // Then:
        assertEventually(
                "Then: the angle should match",
                () -> {
                    return acceptable(car.gyroscopeHeading.get(), MqttCar.DEFAULT_HEADING + expectedAngle, FaultTolerance.Heading);
                },
                Timeout.Long
        );

        car.steerCar(0);
    }

    @Test
    public void _07_GivenAStandingCar_WhenSteering_ThenTheAngleShouldMatch() throws Exception {
        // Given:
        final double standingSpeed = 0;
        car.changeSpeed(standingSpeed);
        assertEventually(
                "Given: A standing car",
                () -> acceptable(car.speed.get(), ThrottleSpeedMap.get(standingSpeed), FaultTolerance.Speed),
                Timeout.Long
        );

        // When:
        final double angle = 10;
        try {
            car.steerCar(angle);
            assertTrue("When: Steering", true);
        } catch (Exception ex) {
            assertTrue("When: Steering", false);
        }

        // Then:
        final double initialAngle = 0;
        assertEventually(
                "Then: nothing should happen",
                () -> acceptable(car.gyroscopeHeading.get(), MqttCar.DEFAULT_HEADING + initialAngle, FaultTolerance.Heading),
                Timeout.Long
        );

        car.steerCar(0);
    }

    @Test
    public void _08_GivenAMovingCar_WhenWallIsHit_ThenIRDistanceShouldChange() throws Exception {
        //Given:
        final double movingSpeed = 100;
        car.changeSpeed(movingSpeed);
        assertEventually(
                "Given: A moving car",
                () -> acceptable(car.speed.get(), ThrottleSpeedMap.get(movingSpeed), FaultTolerance.Speed),
                Timeout.Long
        );

        //When: Wall is hit (basically just takes some time)
        
        //Then:
        assertEventually(
            "Then: the distance should change",
            () -> car.ir_distance.get() != 0.0,
            Timeout.Long
        );
    }

    @Test
    public void _09_GivenAMovingCar_WhenDirectionChanges_ThenBlinkerShouldStop() throws Exception {
        //Given:
        final double movingSpeed = 20;
        car.changeSpeed(movingSpeed);
        car.blinkDirection(MqttCar.BlinkerDirection.Right);
        assertEventually(
            "Given: A moving car",
            () -> acceptable(car.speed.get(), ThrottleSpeedMap.get(movingSpeed), FaultTolerance.Speed),
            Timeout.Long
        );

        //When:
        final double rotationAngle = 30;
        try{
            car.steerCar(rotationAngle);
            assertTrue("When: direction changes", true);
        } catch(Exception ex) {
            assertTrue("When: direction changes", false);
        }

        //Then:
        assertEventually("Then: blinker should switch off",
            () -> car.blinkerStatus.get() == MqttCar.BlinkerDirection.Off,
            Timeout.Long
        );

        car.steerCar(0);
    }

    @Test
    public void _10_GivenAMovingCar_WhenEmergencyStopEngaged_ThenSpeedIsZero() throws Exception {
        //Given:
        final double movingSpeed = 20;
        car.changeSpeed(movingSpeed);
        assertEventually(
                "Given: A Moving Car",
                () -> acceptable(car.speed.get(), ThrottleSpeedMap.get(movingSpeed), FaultTolerance.Speed),
                Timeout.Long
        );

        //When:
        try {
            car.emergencyStop();
            assertTrue(
                    "Car has stopped.", true
            );
        } catch (Exception e) {
            assertTrue(
                    "Car has stopped.", false
            );
        }

        //Then:
        final double standingSpeed = 0;
        assertEventually(
                "Then: Car has stopped.",
                () -> acceptable(car.speed.get(), ThrottleSpeedMap.get(standingSpeed), FaultTolerance.Speed),
                Timeout.Long
        );
    }
}
