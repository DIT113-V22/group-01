package com.example.smartcarmqttapp;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.Callable;

@RunWith(AndroidJUnit4.class)
public class MqttCarUnitTest {

    /**
     * Intervals in ms.
     */
    public static final class Intervals {
        public static final int Short = 300;
        public static final int Medium = 500;
        public static final int Long = 1000;
    }

    /**
     * Timeout in ms.
     */
    public static final class Timeout {
        public static final int Short = 1000;
        public static final int Medium = 5000;
        public static final int Long = 10000;
    }

    /**
     * Fault tolerance
     */
    public static final class FaultTolerance {
        public static final double Speed = 0.01;
    }

    private static Context appContext;
    private static MqttCar car;

    @BeforeClass
    public static void setupCar() throws InterruptedException {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        car = new MqttCar(appContext, () -> {});

        while (!car.isConnected()) Thread.sleep(Intervals.Short);
    }

    // ToDo: Add tests for the mqtt car here

    @Test
    public void GivenAStandingCar_WhenSpeedingFully_ThenTheSpeedShouldMatch() throws Exception {
        // Given:
        final double standingSpeed = 0;
        car.changeSpeed(standingSpeed);
        assertTrue(
                "Given: A standing car",
                eventuallyTrue(() -> withinRange(car.speed.get(), standingSpeed, FaultTolerance.Speed), Timeout.Long)
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
        assertTrue(
                "Then: the speed should match",
                eventuallyTrue(() -> withinRange(car.speed.get(), expectedSpeed, FaultTolerance.Speed), Timeout.Long)
        );
    }

    private boolean withinRange(double value, double expected, double faultTolerance) {
        final double min = expected - faultTolerance;
        final double max = expected + faultTolerance;
        return value > min && value < max;
    }

    private boolean eventuallyTrue(Callable<Boolean> assertion) throws Exception {
        return eventuallyTrue(assertion, Timeout.Medium, Intervals.Medium);
    }

    private boolean eventuallyTrue(Callable<Boolean> assertion, int timeout) throws Exception {
        return eventuallyTrue(assertion, timeout, Intervals.Medium);
    }

    private boolean eventuallyTrue(Callable<Boolean> assertion, int timeout, int interval) throws Exception {
        int triesLeft = timeout / interval;
        boolean isTrue;

        do {
            isTrue = assertion.call();
            Thread.sleep(interval);
            triesLeft--;
        } while (!isTrue && triesLeft > 0);

        return isTrue;
    }
}
