#include <Smartcar.h>
#include <vector>
#include <MQTT.h>
#include <WiFi.h>
#ifdef __SMCE__
#include <OV767X.h>
#endif

MQTTClient mqtt;
WiFiClient net;

// wifi SSID and password defined below
// empty ssid and pass means connect to localhost (only change for physical car)
const char ssid[] = " ";
const char pass[] = " ";

// Identifying tag
const String ID_TAG = "MQTTSmartCar";
// main topic to derive others from
const String MAINMQTT_TOPIC = "/smartcar/";
// control topics
const String STEERING_TOPIC = MAINMQTT_TOPIC + "controls/steering";
const String THROTTLE_TOPIC = MAINMQTT_TOPIC + "controls/throttle";
const String ESTOP_TOPIC = MAINMQTT_TOPIC + "controls/stop";

// sensor topics
const String IR_TOPIC = MAINMQTT_TOPIC + "sensor/ir";
const String ULTRASONIC_TOPIC = MAINMQTT_TOPIC + "sensor/ultrasonic";
const String GYROSCOPE_TOPIC = MAINMQTT_TOPIC + "sensor/gyroscope";
const String ODOMETER_TOPIC = MAINMQTT_TOPIC + "sensor/odometer";
const char CAMERA_TOPIC[] = "/smartcar/sensor/camera";
// status topics
const String BLINKERS_TOPIC = MAINMQTT_TOPIC + "status/blinkers";

// calculated data
const String ODOMETER_DISTANCE = MAINMQTT_TOPIC + "status/odometer/distance";
const String ODOMETER_SPEED = MAINMQTT_TOPIC + "status/odometer/speed";

const int MAX_DISTANCE = 80;
const int TRIGGER_PIN = 6;
const int ECHO_PIN = 7;

const unsigned long PULSES_PER_M = 400;

ArduinoRuntime arduinoRuntime;
// Motors
BrushedMotor leftMotor(arduinoRuntime, smartcarlib::pins::v2::leftMotorPins);
BrushedMotor rightMotor(arduinoRuntime, smartcarlib::pins::v2::rightMotorPins);
DifferentialControl control(rightMotor, leftMotor);

// Sensors
SR04 frontUltrasonic(arduinoRuntime, TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE);
GP2Y0A21 frontIR(arduinoRuntime, 0);
GY50 gyroscope(arduinoRuntime, 0, 100UL);
DirectionlessOdometer leftOdometer(
    arduinoRuntime,
    smartcarlib::pins::v2::leftOdometerPin,
    []()
    { leftOdometer.update(); },
    PULSES_PER_M);
DirectionlessOdometer rightOdometer(
    arduinoRuntime,
    smartcarlib::pins::v2::rightOdometerPin,
    []()
    { leftOdometer.update(); },
    PULSES_PER_M);

// For the camera
std::vector<char> frameBuffer;

void setup()
{
  Serial.begin(9600);

// camera is initialized with a frame buffer
//  should be RGB888 so if something breaks start here
#ifdef __SMCE__
  Camera.begin(QVGA, RGB888, 15);
  frameBuffer.resize(Camera.width() * Camera.height() * Camera.bytesPerPixel());
#endif

  // starts wifi connection to localhost
  WiFi.begin(ssid, pass);
  auto wifiStatus = WiFi.status();

  // checks wifistatus to display if its not connected to either
  //   the localhost or to actual WiFi
  //
  while (wifiStatus != WL_CONNECTED && wifiStatus != WL_NO_SHIELD)
  {
    Serial.println(wifiStatus);
    delay(1000);
    wifiStatus = WiFi.status();
  }

  // begin broker on localhost at port 1883
  mqtt.begin("127.0.0.1", 1883, net);

  // print . while arduino is not connected to car
  while (!mqtt.connect("SmartCarMQTT", "SmartCarMQTT", " "))
  {
    Serial.println("MQTT Connecting...");
    delay(1000);
  }
  if (mqtt.connected())
  {
    Serial.println("Connected to MQTT Broker");
  }

  // subscribe to main topic w/ wildcard attached
  mqtt.subscribe(THROTTLE_TOPIC, 1);
  // on specific topics, it will do certain things
  mqtt.onMessage([](String topic, String message)
                 {
    if(topic == "/smartcar/control/drive"){
      control.setSpeed(message.toInt());
      //enter commands interpret received commands
    } });
}

void loop()
{
  gyroscope.update();
  control.setSpeed(20);
  // when connected, keep checking for messages
  if (mqtt.connected())
  {
    mqtt.loop();
    // delay to not overload the CPU
    delay(1);

    const auto currentTime = millis();

    /**
     * @brief if the it has been 1 second since the last transmission
     */
    static auto previousTransmission = 0UL;
    static auto previousHeading = 0;
    if (currentTime - previousTransmission >= 1000UL)
    {
      // Publishing IR sensor
      const auto ir_distance = String(frontIR.getDistance());
      mqtt.publish(IR_TOPIC, ir_distance);

      // Publishing average distance travelled via both Odometers
      //  (distance logged is in centimeters)
      const auto avg_distance = String((leftOdometer.getDistance() + rightOdometer.getDistance()) / 2);
      mqtt.publish(ODOMETER_DISTANCE, avg_distance);

      // Publishing average speed of the car via both Odometers
      //  (speed logged is in meters per second)
      const auto avg_speed = String((leftOdometer.getSpeed() + rightOdometer.getSpeed()) / 2);
      mqtt.publish(ODOMETER_SPEED, avg_speed);

      // Publish heading angle (degrees: [0, 360]) using gyroscope
      const auto currentHeading = gyroscope.getHeading();
      mqtt.publish(GYROSCOPE_TOPIC, String(currentHeading));

      // Publish distance to front (cm: [0, MAX_DISTANCE]) using front ultrasonic
      const auto frontDistance = String(frontUltrasonic.getDistance());
      mqtt.publish(ULTRASONIC_TOPIC, frontDistance);

      if(currentHeading > previousHeading) {
        mqtt.publish(BLINKERS_TOPIC, "L");
      }else if(currentHeading < previousHeading) {
        mqtt.publish(BLINKERS_TOPIC, "R");
      }else{
        mqtt.publish(BLINKERS_TOPIC, "F");
      }
      previousHeading = currentHeading;

      // Brushed motor (throttle, direction) readings omitted

      previousTransmission = currentTime;
    }

/**
 * @brief every 65 milliseconds, it will publish the data from the frame
 * that was captured by the camera and the frame's size
 */
#ifdef __SMCE__
    static auto previousFrame = 0UL;
    if (currentTime - previousFrame >= 65)
    {
      previousFrame = currentTime;
      Camera.readFrame(frameBuffer.data());
      mqtt.publish(CAMERA_TOPIC, frameBuffer.data(), frameBuffer.size(),
                   false, 0);
    }
#endif
  }
}