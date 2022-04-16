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
        public static final double Distance = 0.1;
        public static final double Ir_Distance = 4;
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

    //Testing for distance might be setting speed for a while and then checking distance
    @Test
    public void GivenAMovingCar_WhenSpeedingForSomeTime_ThenTheDistanceShouldMatch() throws Exception {
        // Given:
        final double movingSpeed = 0.2; //0.5 m/s == 50 cm/s
        car.changeSpeed(movingSpeed);
        assertEventually(
                "Given: A moving car",
                () -> acceptable(car.speed.get(), movingSpeed, FaultTolerance.Speed),
                Timeout.Long
        );

        // When:
        Thread.sleep(1000); //Travels for 1 second
        final double expectedDistance = 20; //Travelled 1 second == distance travelled: 50 cm
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
                () -> acceptable(car.distance.get(), expectedDistance, FaultTolerance.Distance),
                Timeout.Long
        );
    }

    @Test
    public void GivenAMovingCar_WhenWallIsHit_ThenIRDistanceShouldChange() throws Exception {
        //Given:
        final double movingSpeed = 0.2;
        car.changeSpeed(movingSpeed);
        assertEventually(
                "Given: A moving car",
                () -> acceptable(car.speed.get(), movingSpeed, FaultTolerance.Speed),
                Timeout.Long
        );

        //When:
        final double objectMinDistance = 6; //values go from 50 to ~6 cm)
        try{
            car.changeSpeed(0);
            assertTrue("When: Wall is hit", true);
        }
        catch (Exception ex){
            assertTrue("When: Wall is hit", false);
        }

        //Then:
        assertEventually(
                "Then: the distance should match",
                () -> acceptable(car.ir_distance.get(), objectMinDistance, FaultTolerance.Ir_Distance),
                Timeout.Long
        );
    }

}
