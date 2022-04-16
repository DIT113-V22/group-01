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

ArduinoRuntime arduinoRuntime;
//Motors
BrushedMotor leftMotor(arduinoRuntime,smartcarlib::pins::v2::leftMotorPins);
BrushedMotor rightMotor(arduinoRuntime,smartcarlib::pins::v2::rightMotorPins);
DifferentialControl control(rightMotor, leftMotor);
SimpleCar car(control);

void setup() {
  car.setSpeed(10);
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
    Serial.println("MQTT Connecting...");
    delay(1000);
  }
  if(mqtt.connected()) {
    Serial.println("Connected to MQTT Broker");
  }

  //subscribe to main topic w/ wildcard attached
  mqtt.subscribe(MAINMQTT_TOPIC + "#", 1);
  //on specific topics, it will do certain things
  mqtt.onMessage([](String topic, String message){
    if(topic == "/smartcar/control/drive"){
      //enter commands interpret received commands
    //When emergency stop topic message is recieved, car speed is set to 0. 
    } else if (topic == ESTOP_TOPIC) {
      car.setSpeed(0);
      mqtt.publish(ESTOP_TOPIC, "Emergency Stop. Speed has been set to zero.");
      Serial.println("Car has stopped");
    }
  });

}



void loop() {
  //when connected, keep checking for messages
  if (mqtt.connected()) {
    mqtt.loop();
    //delay to not overload the CPU
    delay(100);
    mqtt.publish("/smartcar/derivedData/speed");
  }
}
