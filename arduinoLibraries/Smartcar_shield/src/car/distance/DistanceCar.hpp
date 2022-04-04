/**
 * \class DistanceCar
 * A class to programmatically represent a vehicle equipped with odometers
 */
#pragma once

#include "../../runtime/Runtime.hpp"
#include "../../sensors/odometer/Odometer.hpp"
#include "../simple/SimpleCar.hpp"

namespace smartcarlib
{
namespace constants
{
namespace car
{
const unsigned long kDefaultPidFrequency = 80;
const float kDefaultProportional         = 5.0F;
const float kDefaultIntegral             = 0.0F;
const float kDefaultDerivative           = 10.0F;
const int kOdometersNotAttachedError     = -1000;
const auto kBreakSpeedScale              = 10;
} // namespace car
} // namespace constants
} // namespace smartcarlib

class DistanceCar : virtual public SimpleCar
{
public:
    /**
     * Constructs a car equipped with a distance sensor
     * @param runtime  The runtime environment you want to run the class for
     * @param control  The car's control
     * @param odometer The odometer
     *
     * **Example:**
     * \code
     * ArduinoRuntime arduinoRuntime;
     * BrushedMotor leftMotor(arduinoRuntime, smartcarlib::pins::v2::leftMotorPins);
     * BrushedMotor rightMotor(arduinoRuntime, smartcarlib::pins::v2::rightMotorPins);
     * DifferentialControl control(leftMotor, rightMotor);
     *
     * DirectionlessOdometer odometer(
     *   arduinoRuntime, smartcarlib::pins::v2::leftOdometerPin, []() { odometer.update(); }, 100);

     * DistanceCar car(arduinoRuntime, control, odometer);
     * \endcode
     */
    DistanceCar(Runtime& runtime, Control& control, Odometer& odometer);

    /**
     * Constructs a car equipped with a distance sensor
     * @param runtime       The runtime environment you want to run the class for
     * @param control       The car's control
     * @param odometerLeft  The left odometer
     * @param odometerRight The right odometer
     *
     * **Example:**
     * \code
     * BrushedMotor leftMotor(smartcarlib::pins::v2::leftMotorPins);
     * BrushedMotor rightMotor(smartcarlib::pins::v2::rightMotorPins);
     * DifferentialControl control(leftMotor, rightMotor);
     *
     * const auto pulsesPerMeter = 600;
     *
     * DirectionlessOdometer leftOdometer(
     *     smartcarlib::pins::v2::leftOdometerPin, []() { leftOdometer.update(); }, pulsesPerMeter);
     * DirectionlessOdometer rightOdometer(
     *     smartcarlib::pins::v2::rightOdometerPin, []() { rightOdometer.update(); },
     *     pulsesPerMeter);
     *
     * DistanceCar car(control, gyroscope, leftOdometer, rightOdometer);
     * \endcode
     */
    DistanceCar(Runtime& runtime,
                Control& control,
                Odometer& odometerLeft,
                Odometer& odometerRight);

    /**
     * Gets the car's travelled distance
     * @return The car's travelled distance
     *
     * **Example:**
     * \code
     * // After we have attached the odometers, we can get the travelled distance
     * long travelledDistance = car.getDistance();
     * \endcode
     */
    long getDistance();

    /**
     * Sets the car's speed in meters per second if cruise control is enabled
     * otherwise as a percentage of the motor speed. Sign in both cases determines
     * direction.
     * @param speed The car's speed
     *
     * **Example (with cruise control):**
     * \code
     * car.enableCruiseControl();
     * float speedInMetersPerSecond = -0.8;
     * // Set speed to 0.8 meters per second backward
     * car.setSpeed(speedInMetersPerSecond);
     * \endcode
     *
     * **Example (without cruise control):**
     * \code
     * float speedInPercentageOfMotorPower = 50;
     * // Set speed to half of the maximum motor speed forward
     * car.setSpeed(speedInPercentageOfMotorPower);
     * \endcode
     */
    void setSpeed(float speed) override;

    /**
     * Gets the car's current speed in meters per second
     * @return The car's speed
     *
     * **Example:**
     * \code
     * float currentSpeed = car.getSpeed();
     * \endcode
     */
    float getSpeed();

    /**
     * Enables the car to move with a stable speed using the odometers
     * @param proportional The proportional value of the PID controller
     * @param integral     The integral value of the PID controller
     * @param derivative   The derivative value of the PID controller
     * @param frequency    How often to adjust the speed using `update()`
     *
     * **Example:**
     * \code
     * // Start adjusting the speed using a PID controller with
     * // P = 2, I = 0.1, D = 0.5 every 80 milliseconds
     * car.enableCruiseControl(2, 0.1, 0.5, 80);
     * \endcode
     */
    void enableCruiseControl(float proportional = smartcarlib::constants::car::kDefaultProportional,
                             float integral     = smartcarlib::constants::car::kDefaultIntegral,
                             float derivative   = smartcarlib::constants::car::kDefaultDerivative,
                             unsigned long frequency
                             = smartcarlib::constants::car::kDefaultPidFrequency);

    /**
     * Adjusts the cruise control speed. You must have this being executed as often
     * as possible when having cruise control enabled. When cruise control is not
     * enabled this has no effect.
     *
     * **Example:**
     * \code
     * void loop() {
     *   // Update the car readings as often as possible
     *   car.update();
     *   // Other functionality
     * }
     * \endcode
     */
    virtual void update();

    /**
     * Disable cruise control.
     *
     * **Example:**
     * \code
     * car.disableCruiseControl();
     * \endcode
     */
    void disableCruiseControl();

    /**
     * Sets the motor speed individually as a percentage of the motors` total
     * power unless cruise control is enabled in which case has no effect.
     * Use with caution.
     * @param firstMotorSpeed  The speed of the motor passed as first argument
     *                         argument to the car's control class [-100, 100]
     * @param secondMotorSpeed The speed of the motor passed as second argument
     *                         argument to the car's control class [-100, 100]
     *
     * **Example:**
     * \code
     * // Make the car spin around
     * car.overrideMotorSpeed(100, -100);
     * \endcode
     */
    void overrideMotorSpeed(int firstMotorSpeed, int secondMotorSpeed) override;

private:
    Odometer& mOdometerLeft;
    Odometer& mOdometerRight;
    Runtime& mRuntime;
    bool mCruiseControlEnabled{ false };
    float mProportional{ 0 };
    float mIntegral{ 0 };
    float mDerivative{ 0 };
    unsigned long mFrequency{ 0 };
    unsigned long mPreviousUpdate{ 0 };
    float mTargetSpeed{ 0 };
    float mPreviousControlledSpeed{ 0 };
    float mIntegratedError{ 0 };
    float mPreviousError{ 0 };

    bool areOdometersAttached();
    bool areOdometersDirectional();
    float controlMotorSpeed(const float& previousSpeed,
                            const float& targetSpeed,
                            const float& currentSpeed);

    /**
     * Used internally making an attempt to stop the car based on the current speed.
     */
    void brake();
};

/**
 * \example DistanceCar.ino
 * A basic example on how to use the core functionality of the DistanceCar class.
 *
 * \example manualWithCruiseControl.ino
 * An example on how to manually control a DistanceCar via Serial when having
 * cruise control enabled.
 *
 * \example PidControllerMonitor.ino
 * An example on how to observe the behavior of the PID controller used by
 * cruise control of a DistanceCar, via the Arduino IDE's Serial Plotter
 * feature. The sketch can be used during the cruise control's tuning process.
 */
