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
  Serial.println(frontUltraSonic.getDistance());
  obstacleAvoidance();
}

void obstacleAvoidance(){
  //since if the sensors pick up nothing the distance is 0
  /**
   * @if if the distance is greater than 0,
   * meaning it has picked up an obstacle, it stops.
   * 
   * @else drive normally
   * 
   */
  auto distance = frontUltraSonic.getDistance();
  if(distance > 0 && distance < 60){
    control.setSpeed(0);
    delay(100);
  }
  else{
    control.setSpeed(20);
  }
}