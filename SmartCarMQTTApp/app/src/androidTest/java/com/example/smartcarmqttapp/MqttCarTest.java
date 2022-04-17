package com.example.smartcarmqttapp;

import static org.junit.Assert.*;
import static com.example.smartcarmqttapp.IntegrationTestUtils.*;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Tests for checking if the communication with the car works.
 */
@RunWith(AndroidJUnit4.class)
public class MqttCarTest {
    /**
     * Fault tolerance (absolute values)
     */
    public static final class FaultTolerance {
        public static final double Speed = 0.01;
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
        car = new MqttCar(appContext, () -> {});

        if (!eventually(() -> car.isConnected())) {
            throw new Exception("Failed to connect to the car!");
        }
    }

    // ToDo: Add tests for the mqtt car here

    @Test
    public void GivenAStandingCar_WhenSpeedingFully_ThenTheSpeedShouldMatch() throws Exception {
        // Given:
        final double standingSpeed = 0;
        try {
            car.changeSpeed(standingSpeed);
            assertEventually(
                    "Given: A standing car",
                    () -> acceptable(car.speed.get(), standingSpeed, FaultTolerance.Speed),
                    Timeout.Long
            );
        }
        catch (Exception ex) {
            assertTrue("Given: A standing car", false);
            throw ex;
        }

        // When:
        final double expectedSpeed = 1.0;
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
                () -> acceptable(car.speed.get(), expectedSpeed, FaultTolerance.Speed),
                Timeout.Long
        );
    }

    @Test
    public void GivenAStandingCar_WhenSpeedingBackwards_ThenTheSpeedShouldMatch() throws Exception {
        // Given:
        final double standingSpeed = 0.0;
        try {
            car.changeSpeed(standingSpeed);
            assertEventually(
                    "Given: A standing car",
                    () -> acceptable(car.speed.get(), standingSpeed, FaultTolerance.Speed),
                    Timeout.Long
            );
        }
        catch (Exception e) {
            assertTrue("Given: A standing car", false);
            throw e;
        }

        // When:
        final double expectedSpeed = -1.0;
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
                () -> acceptable(car.speed.get(), expectedSpeed, FaultTolerance.Speed),
                Timeout.Long
        );
    }

    @Test
    public void GivenAMovingCar_WhenSlowingDown_ThenTheSpeedShouldMatch() throws Exception {
        // Given:
        final double movingSpeed = 0.8;
        try {
            car.changeSpeed(movingSpeed);
            assertEventually(
                    "Given: A moving car",
                    () -> acceptable(car.speed.get(), movingSpeed, FaultTolerance.Speed),
                    Timeout.Long
            );
        }
        catch (Exception e) {
            assertTrue("Given: A moving car", false);
            throw e;
        }

        // When:
        final double expectedSpeed = 0.3;
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
                () -> acceptable(car.speed.get(), expectedSpeed, FaultTolerance.Speed),
                Timeout.Long
        );
    }

    @Test
    public void GivenAMovingCar_WhenAccelerating_ThenTheSpeedShouldMatch() throws Exception {
        // Given:
        final double movingSpeed = 0.2;
        try {
            car.changeSpeed(movingSpeed);
            assertEventually(
                    "Given: A moving car",
                    () -> acceptable(car.speed.get(), movingSpeed, FaultTolerance.Speed),
                    Timeout.Long
            );
        }
        catch (Exception e) {
            assertTrue("Given: A moving car", false);
            throw e;
        }

        // When:
        final double expectedSpeed = 0.7;
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
                () -> acceptable(car.speed.get(), expectedSpeed, FaultTolerance.Speed),
                Timeout.Long
        );
    }
}
