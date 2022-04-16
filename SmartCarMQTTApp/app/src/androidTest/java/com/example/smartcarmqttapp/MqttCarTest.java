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

    @Test
    public void GivenAMovingCar_WhenEmergencyStopEngaged_ThenSpeedIsZero(){
        //Given:
        car.changeSpeed(5);
        assertEventually(
            "Given: A Moving Car",
            () -> acceptable(car.speed.get(), 5, FaultTolerance.Speed),
            Timeout.Medium
        );

        //When:
        try {
            car.emergencyStop();
            assertTrue(
                "Car has stopped.", true
            );
        } catch (Exception e) {
            //TODO: handle exception
            assertTrue(
                "Car has stopped.", false
            );
        }

        //Then:
        assertEventually(
            "Then: Car has stopped.",
            () -> acceptable(car.speed.get(), 0, 0),
            Timeout.Short
        );
    }

}
