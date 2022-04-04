/**
 * \class BrushedMotor
 * A brushed motor is controlled via two wires where the flow of current determines
 * the direction of rotation and the duty cycle the speed.
 *
 * For their control, we will assume the existince of a half-bridge such as the
 * L293D chip where three signals are needed, two for determining the direction
 * and another for determining the speed.
 */
#pragma once

#include <stdint.h> // NOLINT(modernize-deprecated-headers)

#include "../../../runtime/Runtime.hpp"
#include "../../Motor.hpp"

/**
 * @brief Helper class to represent brushed motor pins
 */
struct BrushedMotorPins
{
    /**
     * @brief Construct a BrushedMotorPins object
     *
     * @param forwardPin  The direction pin that when set to HIGH makes the motor spin forward
     * @param backwardPin The direction pin that when set to HIGH makes the motor spin backward
     * @param enablePin   The pin  that controls the motor's speed
     */
    BrushedMotorPins(uint8_t forwardPin, uint8_t backwardPin, uint8_t enablePin)
        : forward{ forwardPin }
        , backward{ backwardPin }
        , enable{ enablePin }
    {
    }

    const uint8_t forward;  // NOLINT: It's OK for these to be public
    const uint8_t backward; // NOLINT
    const uint8_t enable;   // NOLINT
};

class BrushedMotor : public Motor
{
public:
    /**
     * Constructs a brushed DC motor instance
     * @param runtime     The runtime environment you want to run the class for
     * @param forwardPin  The direction pin that when set to HIGH makes the motor spin forward
     * @param backwardPin The direction pin that when set to HIGH makes the motor spin forward
     * @param enablePin   The pin  that controls the motor's speed
     *
     * **Example:**
     * \code
     * ArduinoRuntime arduinoRuntime;
     * BrushedMotor leftMotor(arduinoRuntime, 8, 9, 10);
     * \endcode
     */
    BrushedMotor(Runtime& runtime, uint8_t forwardPin, uint8_t backwardPin, uint8_t enablePin);

    /**
     * Constructs a brushed DC motor instance
     * @param runtime The runtime environment you want to run the class for
     * @param pins    The `BrushedMotorPins` object with the pins of the motor
     *
     * **Example:**
     * \code
     * ArduinoRuntime arduinoRuntime;
     * BrushedMotor leftMotor(arduinoRuntime, smartcarlib::pins::v2::leftMotorPins);
     * \endcode
     */
    BrushedMotor(Runtime& runtime, BrushedMotorPins pins);

    /* Check `Motor` interface for documentation */
    void setSpeed(int speed) override;

private:
    void attach();
    const uint8_t kForwardPin;
    const uint8_t kBackwardPin;
    const uint8_t kEnablePin;
    Runtime& mRuntime;
    const uint8_t kOutput;
    const uint8_t kLow;
    const uint8_t kHigh;
    bool mAttached{ false };
};
