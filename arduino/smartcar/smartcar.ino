#include <Smartcar.h>
#include <vector>
#include <MQTT.h>
#include <WiFi.h>

MQTTClient mqtt;
WiFiClient net;


//wifi SSID and password defined below
//empty ssid and pass means connect to localhost (only change for physical car)
const char ssid[] = " ";
const char pass[] = " ";

//Identifying tag
const String ID_TAG = "MQTTSmartCar";
//main topic to derive others from
const String MAINMQTT_TOPIC = "/smartcar/";
//control topics
const String STEERING_TOPIC = MAINMQTT_TOPIC + "controls/steering"; 
const String THROTTLE_TOPIC = MAINMQTT_TOPIC + "controls/throttle"; 
const String ESTOP_TOPIC = MAINMQTT_TOPIC + "controls/stop";

//sensor topics
const String IR_TOPIC = MAINMQTT_TOPIC + "sensor/ir";
const String ULTRASONIC_TOPIC = MAINMQTT_TOPIC + "sensor/ultrasonic";
const String GYROSCOPE_TOPIC = MAINMQTT_TOPIC + "sensor/gyroscope";
const String ODOMETER_TOPIC = MAINMQTT_TOPIC + "sensor/odometer";
const String CAMERA_TOPIC = MAINMQTT_TOPIC + "sensor/camera";
//status topics
const String BLINKERS_TOPIC = MAINMQTT_TOPIC + "status/blinkers";



const int fSpeed   = 50;  // 70% of the full speed forward
const int bSpeed   = -50; // 70% of the full speed backward
const int lDegrees = -45; // degrees to turn left
const int rDegrees = 45;  // degrees to turn right

ArduinoRuntime arduinoRuntime;
BrushedMotor leftMotor(arduinoRuntime, smartcarlib::pins::v2::leftMotorPins);
BrushedMotor rightMotor(arduinoRuntime, smartcarlib::pins::v2::rightMotorPins);
DifferentialControl control(leftMotor, rightMotor);

SimpleCar car(control);

const int triggerPin           = 6; // D6
const int echoPin              = 7; // D7
const unsigned int maxDistance = 100;

SR04 front{arduinoRuntime, triggerPin, echoPin, maxDistance};

void setup() {
  Serial.begin(9600);

  //starts wifi connection to localhost
  WiFi.begin(ssid, pass);
  auto wifiStatus = WiFi.status();

  //checks wifistatus to display if its not connected to either 
  //  the localhost or to actual WiFi
  //
  while (wifiStatus != WL_CONNECTED && wifiStatus != WL_NO_SHIELD){
    Serial.println(wifiStatus);
    delay(1000);
    wifiStatus = WiFi.status();
  }

  //begin broker on localhost at port 1883  
  mqtt.begin("127.0.0.1", 1883, net);

  //print . while arduino is not connected to car
  while(!mqtt.connect("SmartCarMQTT", "SmartCarMQTT", " ")) {
    Serial.println("MQTT Connected: " + mqtt.connected());
    Serial.println(".");
    delay(1000);
  }
  if(mqtt.connected()) {
    Serial.println("Connected to MQTT Broker");
  }

  //subscribe to main topic w/ wildcard attached
  mqtt.subscribe(MAINMQTT_TOPIC + "#", 1);
  //on specific topics, it will do certain things
  mqtt.onMessage([](String topic, String message) {
    if (topic == THROTTLE_TOPIC) {
      car.setSpeed(message.toInt());
    } else if (topic == STEERING_TOPIC) {
      car.setAngle(message.toInt());
    } else {
      Serial.println(topic + " " + message);
    }
  });

}

void loop() {
  //when connected, keep checking for messages
  if (mqtt.connected()) {
    mqtt.loop();
    //delay to not overload the CPU
    delay(1);
  }
}