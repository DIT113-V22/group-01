#include <Smartcar.h>
#include <vector>
#include <MQTT.h>
#include <WiFi.h>
#ifdef __SMCE__
  #include <OV767X.h>
#endif

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
const char CAMERA_TOPIC[] = "smartcar/sensor/camera";
//status topics
const String BLINKERS_TOPIC = MAINMQTT_TOPIC + "status/blinkers";

const int MAX_DISTANCE = 80;
const int TRIGGER_PIN = 6;
const int ECHO_PIN = 7;

const unsigned long PULSES_PER_M = 400;

ArduinoRuntime arduinoRuntime;
//Motors
BrushedMotor leftMotor(arduinoRuntime,smartcarlib::pins::v2::leftMotorPins);
BrushedMotor rightMotor(arduinoRuntime,smartcarlib::pins::v2::rightMotorPins);
DifferentialControl control(rightMotor, leftMotor);

//Sensors
SR04 frontUltrasonic(arduinoRuntime, TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE);
GP2Y0A21 frontIR(arduinoRuntime, 0);
GY50 gyroscope(arduinoRuntime, 0, 100UL);
DirectionlessOdometer leftOdometer(arduinoRuntime, 
                                  smartcarlib::pins::v2::leftOdometerPin, 
                                  []() { leftOdometer.update();}, PULSES_PER_M);
DirectionlessOdometer rightOdometer(arduinoRuntime, 
                                  smartcarlib::pins::v2::rightOdometerPin, 
                                  []() { leftOdometer.update();}, PULSES_PER_M);

//For the camera
std::vector<char> frameBuffer;

void setup() {
  Serial.begin(9600);

  //camera is initialized with a frame buffer
  // should be RGB888 so if something breaks start here
  #ifdef __SMCE__
    Camera.begin(QVGA, RGB888, 15);
    frameBuffer.resize(Camera.width() * Camera.height() * Camera.bytesPerPixel());
  #endif
  
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
    if(mqtt.connected()){
      Serial.println("MQTT Connection: ACTIVE");
    }
    else{return;}
    Serial.println(".");
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
      control.setSpeed(message.toInt());
      //enter commands interpret received commands
    }
  });
}

void loop() {
  //when connected, keep checking for messages
  if (mqtt.connected()) {
    mqtt.loop();
    //delay to not overload the CPU
    delay(1);

  //publishing requires the value to be parsed to a String before sending
    const auto ir_distance = String(frontIR.getDistance());
    mqtt.publish("/smartcar/sensor/ir", ir_distance);

    //cant really get the pulses but can only get speed and distance
    const auto odometer = String(leftOdometer.getDistance());
    mqtt.publish("/smartcar/sensor/odometer", ir_distance);
  }
}