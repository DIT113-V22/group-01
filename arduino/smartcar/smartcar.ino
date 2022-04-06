#include <Smartcar.h>

//for the ultrasonic sensor
const unsigned int MAX_DISTANCE = 80;
//sends impulse
const int TRIGGER_PIN = 6;
//receives reply
const int ECHO_PIN = 7;

ArduinoRuntime arduinoRuntime;
BrushedMotor leftMotor(arduinoRuntime,smartcarlib::pins::v2::leftMotorPins);
BrushedMotor rightMotor(arduinoRuntime,smartcarlib::pins::v2::rightMotorPins);
DifferentialControl control(rightMotor, leftMotor);
SR04 frontUltraSonic(arduinoRuntime, TRIGGER_PIN,
ECHO_PIN, MAX_DISTANCE);

void setup() {
  Serial.begin(9600);
}

void loop() {
  obstacleAvoidance();
}

void obstacleAvoidance(){
  //since if the sensors pick up nothing the distance is 0
  /**
   * @brief if the distance is greater than 0 and less than 30
   * meaning it has picked up an obstacle but waits until it has reached
   * a point before crashing, then it stops.
   *
   * Value for min distance to obstalce = 30;
   * 
   * @else drive normally
   * 
   */
  if(frontUltraSonic.getDistance() > 0 && frontUltraSonic.getDistance() < 30){
    control.setSpeed(0);
    delay(100);
    control.setSpeed(-30);
    control.setAngle(30);
  }
  else{
    control.setSpeed(30);
  }
}