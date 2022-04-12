package com.example.smartcarmqttapp;

import static org.junit.Assert.*;

import androidx.databinding.ObservableField;

import java.util.concurrent.Callable;

/**
 * A simple toolset for testing integrations with other components which
 * might take some time to respond or return not completely accurate results.
 */
public class IntegrationTestUtils {
    /**
     * Timeouts in ms.
     */
    public static final class Timeout {
        public static final int Short = 1000;
        public static final int Medium = 5000;
        public static final int Long = 10000;
    }

    /**
     * Interval in ms.
     */
    public static final int interval = 500;

    /**
     * Checks of a value is within a acceptable range of the expected value.
     *
     * @param value the value to check
     * @param expected the expected value
     * @param faultTolerance the acceptable deviation from the expected value
     * @return if the value is within a acceptable range of the expected value
     */
    public static boolean acceptable(double value, double expected, double faultTolerance) {
        final double min = expected - faultTolerance;
        final double max = expected + faultTolerance;
        return value > min && value < max;
    }

    /**
     * Gives some time to meet the given assertion.
     * Uses a default timeout (Timeout.Medium).
     *
     * @param assertion The assertion that needs to be met
     * @return If the assertion was met eventually
     * @throws Exception If the assertion cannot be called or the thread cannot be paused
     */
    public static boolean eventually(Callable<Boolean> assertion) throws Exception {
        return eventually(assertion, Timeout.Medium);
    }

    /**
     * Gives some time to meet the given assertion.
     *
     * @param assertion The assertion that needs to be met
     * @param timeout The max time to give before timing out (default: Timeout.Medium)
     * @return If the assertion was met eventually
     * @throws Exception If the assertion cannot be called or the thread cannot be paused
     */
    public static boolean eventually(Callable<Boolean> assertion, int timeout) throws Exception {
        int triesLeft = timeout / interval; // yes, this will not enforce an exact timeout, but good enough
        boolean assertionMet;

        do {
            assertionMet = assertion.call();
            Thread.sleep(interval);
            triesLeft--;
        } while (!assertionMet && triesLeft > 0);

        return assertionMet;
    }

    /**
     * Asserts that eventually the given assertion will be met.
     * Uses a default timeout (Timeout.Medium).
     *
     * @param message The message for the assertion
     * @param assertion The assertion that needs to be met
     * @throws Exception If the assertion cannot be called or the thread cannot be paused
     */
    public static void assertEventually(
            String message,
            Callable<Boolean> assertion
    ) throws Exception {
        assertEventually(message, assertion, Timeout.Medium);
    }

    /**
     * Asserts that eventually the given assertion will be met.
     *
     * @param message The message for the assertion
     * @param assertion The assertion that needs to be met
     * @param timeout The max time to give before timing out (default: Timeout.Medium)
     * @throws Exception When eventually cannot be used or the observable value yields null
     */
    public static void assertEventually(
            String message,
            Callable<Boolean> assertion,
            int timeout
    ) throws Exception {
        assertTrue(message, eventually(assertion, timeout));
    }
}
