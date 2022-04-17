package com.example.smartcarmqttapp;

import static org.junit.Assert.*;
import static com.example.smartcarmqttapp.IntegrationTestUtils.*;

import android.content.Context;

import androidx.databinding.ObservableField;
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

    @Test
    public void GivenAMovingCar_WhenSteering_ThenTheAngleShouldMatch() throws Exception {
        // Given:
        final double movingSpeed = 20;
        car.changeSpeed(movingSpeed);
        assertEventually(
                "Given: A moving car",
                () -> acceptable(car.speed.get(), movingSpeed, FaultTolerance.Speed),
                Timeout.Long
        );

        // When:
        final double expectedAngle = 10;
        try {
            car.steerCar(Double.toString(expectedAngle));
            assertTrue("When: Steering", true);
        }
        catch (Exception ex) {
            assertTrue("When: Steering", false);
        }

        // Then:
        assertEventually(
                "Then: the angle should match",
                () -> acceptable(car.speed.get(), expectedAngle, FaultTolerance.Speed),
                Timeout.Long
        );
    }

    @Test
    public void GivenAStandingCar_WhenSteering_ThenTheAngleShouldMatch() throws Exception {
        // Given:
        final double standingSpeed = 0;
        car.changeSpeed(standingSpeed);
        assertEventually(
                "Given: A standing car",
                () -> acceptable(car.speed.get(), standingSpeed, FaultTolerance.Speed),
                Timeout.Long
        );

        // When:
        final double expectedAngle = 10;
        try {
            car.steerCar(Double.toString(expectedAngle));
            assertTrue("When: Steering", true);
        }
        catch (Exception ex) {
            assertTrue("When: Steering", false);
        }

        // Then:
        assertEventually(
                "Then: nothing should happen",
                () -> acceptable(car.speed.get(), expectedAngle, FaultTolerance.Speed),
                Timeout.Long
        );
    }


    @Test
    public void GivenAStandingCar_WhenSpeedingFully_ThenTheSpeedShouldMatch() throws Exception {
        // Given:
        final double standingSpeed = 0;
        car.changeSpeed(standingSpeed);
        assertEventually(
                "Given: A standing car",
                () -> acceptable(car.speed.get(), standingSpeed, FaultTolerance.Speed),
                Timeout.Long
        );

        // When:
        final double expectedSpeed = 0.5;
        try {
            car.changeSpeed(expectedSpeed);
            assertTrue("When: Speeding fully", true);
        }
        catch (Exception ex) {
            assertTrue("When: Speeding fully", false);
        }

        // Then:
        assertEventually(
                "Then: the speed should match",
                () -> acceptable(car.speed.get(), expectedSpeed, FaultTolerance.Speed),
                Timeout.Long
        );
    }
}
