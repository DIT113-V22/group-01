/**
 * \class SR04
 *  The SR04 (aka SRF05) is an inexpensive ultrasonic sensor controllable over two
 * digital pins.
 *
 * For a more advanced solution (faster readings, not using `pulseIn`, non-blocking measurements
 * etc) please use the [NewPing library](https://bitbucket.org/teckel12/arduino-new-ping/wiki/Home).
 */
#pragma once

#include <stdint.h> // NOLINT(modernize-deprecated-headers)

#include "../../../../runtime/Runtime.hpp"
#include "../../DistanceSensor.hpp"

namespace smartcarlib
{
namespace constants
{
namespace sr04
{
const uint8_t kDefaultIterations       = 5;
const unsigned int kDefaultMaxDistance = 70;
const unsigned int kError              = 0;
} // namespace sr04
} // namespace constants
} // namespace smartcarlib

class SR04 : public DistanceSensor
{
public:
    /**
     * Constructs an SR04 ultrasonic sensor
     * @param runtime     The runtime environment you want to run the class for
     * @param triggerPin  The pin to produce the trigger signal
     * @param echoPin     The pin to receive the echo signal
     * @param maxDistance The maximum measurement distance in centimeters
     *
     * **Example:**
     * \code
     * unsigned short TRIGGER_PIN = 6;
     * unsigned short ECHO_PIN = 7;
     *
     * ArduinoRuntime arduinoRuntime;
     * SR04 sr04(arduinoRuntime, TRIGGER_PIN, ECHO_PIN);
     * \endcode
     */
    SR04(Runtime& runtime,
         uint8_t triggerPin,
         uint8_t echoPin,
         unsigned int maxDistance = smartcarlib::constants::sr04::kDefaultMaxDistance);

    /* Check `DistanceSensor` interface for documentation */
    unsigned int getDistance() override;

    /* Check `DistanceSensor` interface for documentation */
    unsigned int getMedianDistance(uint8_t iterations
                                   = smartcarlib::constants::sr04::kDefaultIterations) override;

private:
    const uint8_t kTriggerPin;
    const uint8_t kEchoPin;
    const unsigned int kMaxDistance;
    const unsigned long kTimeout;
    Runtime& mRuntime;
    bool mAttached{ false };
    const uint8_t kOutput;
    const uint8_t kInput;
    const uint8_t kLow;
    const uint8_t kHigh;

    void attach();
};

/**
 * \example SR04.ino
 * An example that indicates how to conduct a single measurement with the SR04
 * ultrasonic sensor and print the results over Serial.
 */
