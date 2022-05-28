![Crushers #1](https://img.shields.io/static/v1?label=%F0%9F%90%B1%E2%80%8D%F0%9F%92%BB%20Crushers&message=%231&labelColor=9454bf&color=9454bf&style=for-the-badge) 
![Arduino Build Status](https://github.com/DIT113-V22/group-01/actions/workflows/arduino-build.yml/badge.svg)
![Android Build Status](https://github.com/DIT113-V22/group-01/actions/workflows/android-build.yml/badge.svg)

# Crusher's Car Crash Course
A group of 6 like-minded people ready to tackle any challenge!<br>
For more info on how we tackle the challenge, have a look at our [wiki](https://github.com/DIT113-V22/group-01/wiki)!

[🖼 Project Idea Presentation](https://docs.google.com/presentation/d/16SZ2ToYdbLL906brSV6_ACyWLJ0RKk2l5obWzS27Ddo/edit#slide=id.p)

# Table of Contents 
1. [ Introduction ](#introduction)
   - 1.1.   [ What we are going to make ](#what) 
   - 1.2.   [ Why we will make it ](#why)
   - 1.3.   [ How we are going to make it ](#how)
2. [ Installation Manual ](#install)
   - 2.1.  [ Pre-requisites ](#PreRequisites)
   - 2.2.  [ Installation Procedure ](#installation)
   - 2.3.  [ Installation Procedure (for developers)](#installationForDevelopers)
3. [ How to use our mobile app ](#use) 
   - 3.1. [ How to run Mosquitto ](#mosquitto)
   - 3.2. [ How to run the environment (SMCE) ](#runSmce)
   - 3.3. [ How to change the environment (SMCE) ](#changeSmce)
   - 3.4. [ How to run the mobile app ](#mobileApp)
4. [ Learn more about our app ](#moreAboutOurApp)
   - 4.1. [ Software Architecture ](#softwareArchitecture)
   - 4.2. [ Hardware Architecture ](#hardwareArchitecture)

<a name="introduction"></a>
## Introduction

<a name="what"></a>
### What we are going to make

Crushers is dedicated to prepare you to **crush the test**, the theoretical drivers license test that is. We are going to do so by providing an app which quizes you on the traffic laws, signs, and scenarios. Our app will provide you with explanations, visual illustrations, and a hands on manual driving mode to give you the full experience and prepare you like you have never before been prepared for a test.

<a name="why"></a>
### Why we will make it

The simple reason why we assigned ourselves with this mission is the fail rate for the drivers license exam, theory and driving. Keeping calm and focused in a busy and noisy environment as the road, especially within cities, can be difficult and leads to accidents yearly. Some accidents, however, could be mitigated through better knowledge of driving theory. To date most apps focus on the theoretical exam, but our goal is to combine the theory with visual illustrations showing why a certain traffic law/sign is necessary and a manual driving mode in a virtual city to also train you for the driving test.

**What problem it solves**

Fail rate of drivers license exams in Sweden (2021)

![59% fail the theory test and 52% fail the driving test](https://user-images.githubusercontent.com/52662014/160342758-7d2307f5-fed8-4d68-b21b-6e3106f1162e.png)

*source: https://korkortonline.se/en/facts/statistics/*

<a name="how"></a>
### How we are going to make it
To accomplish our goal, we will create a remotely accessible api for the car, provide our end users with an android app, and lastly create a city environment for the emulator for the manual driving and scenario demonstrations. The main focus for the project will lie on the app, and depending on the time available at the end we have some cool ideas for the emulators city environment.

**What kind of technology we are going to use**
| Component | Technologies |
| --------- | ------------ |
| Car controller | Arduino, C++, smartcar shield |
| Car - App communication | MQTT |
| App | Android, Java |
| City environment | SMCE, Blender, etc. |


<a name="install"></a>
## Installation Manual

<a name="PreRequisites"></a>
### Pre-requisites
  - #### Operating System Requirements (Laptop / Desktop)
    - **Windows:** 10 or newer
    - **MAC:** OS Big Sur or newer
    - **Linux:** Ubuntu 18.04 or newer

  - #### Operating System Requirements (Smartphone)
    - **Android:** pie (9.0) or newer
      
    
  - #### Hardware Requirements
      *We strongly recommend a computer fewer than 5 years old*
    - **Processor:** Minimum 1 GHz; Recommended 2GHz or more
    - **Ethernet connection:** (LAN) OR a wireless adapter (Wi-Fi)
    - **Hard Drive:** Minimum 32 GB; Recommended 64 GB or more
    - **Memory (RAM):** Minimum 1 GB; Recommended 4 GB or above
    - Sound card w/speakers

<a name="installation"></a>
### Installation Procedure

1. We require an MQTT broker in order to be able to drive via app. There are a variety of MQTT brokers to choose from, however we choose Mosquitto for our needs.
    - **What exactly is an MQTT broker?**
    <br>It is a software running on a computer. it could be any computer, from raspberry pi to a pc to a server. Broker is available as either open-source
      or proprietary with extra features added on. 
      Broker acts like a post-office, where instead a device sending a message to or getting a message from another device directly. They send to
      post- office (broker) and then it is forwarded to everyone that needs that message.
      
    - **Why do we need a broker?**
    <br>We will need a broker to connect the app to the SMCE so we can practice driving.

    - **How may a Mosquitto broker be set up?**
    <br>Here's a clear explanation for how to download Mosquitto. Choose your operating system and follow the instructions:[Download Mosquitto](https://mosquitto.org/download/) 

2. We need to download SMCE (the car environment), which will allow you to practice and improve your skills.
    - **How are we going about doing it?**
    <br>Please see this page for instructions on how to install SMCE on [MacOs](https://github.com/ItJustWorksTM/smce-gd/wiki/MacOS-setup), [Windows](https://github.com/ItJustWorksTM/smce-gd/wiki/Windows-setup), or [Debian based Linux](https://github.com/ItJustWorksTM/smce-gd/wiki/Debian-based-Linux-setup)

3. Find our project in the [current release](https://github.com/DIT113-V22/group-01/releases/tag/v0.0.9)

<a name="installationForDevelopers"></a>
### Installation Procedure (for developers)
1. As a developer, you must first download and install SMCE, a MQTT broker, as described [ here ](#installation)
2. Download and Install JDK 11
   - 2.1. Go to [link](https://www.oracle.com/se/java/technologies/javase/jdk11-archive-downloads.html). Click on JDK Download for Java based on your operating system download JDK 11 latest version.
   - 2.2. Once the Java JDK 11 download is complete, run the exe for install JDK. Click Next.
   - 2.3. Select the PATH to install Java in Windows. You can leave it Default. Click next.
   - 2.4. Once you install Java in windows, click Close.
   - *Installation for JDK here is for Windows. If you are using a different operating system, go [here](https://docs.oracle.com/javase/9/install/installation-jdk-and-jre-macos.htm#JSJIG-GUID-577CEA7C-E51C-416D-B9C6-B1469F45AC78) for Mac and [here](https://www.digitalocean.com/community/tutorials/how-to-install-java-with-apt-on-ubuntu-20-04) for Ubuntu.*
3. Set environment variables
   - 3.1. Right Click on the My Computer and Select the properties
   - 3.2. Click on advanced system settings
   - 3.3. Click on Environment Variables to set Java runtime environment
   - 3.4. Click on new Button of User variables
   - 3.5. Type PATH in the Variable name.
   - 3.6. Copy the path of bin folder which is installed in JDK folder.
   - 3.7. Paste Path of bin folder in Variable value. Click on OK Button.
   - 3.8. You can set CLASSPATH in a similar way.
   - 3.9. Click on OK button
4. Download and Install Android Studio
   - 4.1. To download the Android Studio, visit the official [Android Studio](https://developer.android.com/studio) website in your web browser.
   - 4.2. When you have finished downloading Android Studio, double click on the downloaded "Android Studio-ide.exe" file.
   - 4.3. "Android Studio Setup" will appear on the screen and click "Next" to proceed.
   - 4.4. Select the components that you want to install and click on the "Next" button.
   - 4.5. Now, browse the location where you want to install the Android Studio and click "Next" to proceed.
   - 4.6. Choose a start menu folder for the "Android Studio" shortcut and click the "Install" button to proceed.
   - 4.7. After the successful completion of the installation, click on the "Next" button.
   - 4.8.Click on the "Finish" button to proceed.
   - 4.9. The welcome screen for Android Studio will now appear on the screen.
   - *Installation for Android Studio here is for Windows. If you are using a different operating system, go [Android Studio official website](https://developer.android.com/studio/install) for both Mac and Ubuntu.*
5. Clone the repo: `git clone https://github.com/DIT113-V22/group-01.git`
6. Open our project in Android Studio
7. Start working!

<a name="use"></a>
## How to use our mobile app
Before using our mobile app, you need to run mosquitto in order to establish connection between car and our app. Question arises, HOW?

<a name="mosquitto"></a>
### How to run Mosquitto 
*These methods apply to Windows, MacOS, and Ubuntu*
  - To start the broker, open a command prompt (Terminal)
  - In the command prompt, navigate to the Mosquitto root folder.
  - tart the Mosquitto service by running the command: `net start mosquitto`.
  - Acknowledge the message: The Mosquitto Broker service was started successfully.

<a name="runSmce"></a>
### How to run the environment (SMCE)
  - Open the "SMCE" shortcut.
  - The start screen for SMCE will now appear on the screen
  - Select the `Start Fresh` button and then the `+` sign on the next screen.
  - Click on `Add new`.
  - Select the Arduino folder's location and open it (file with the `.ino` extension).
  - Click on `Compile`, on top left and wait. When the build succeeds, you will be told; if you want to keep an eye on things, open the `Log` window next to the `compile` button. It's also where you will find compilation errors if something goes wrong.
  - Then press `Start`. You will notice that a car has appeared and that the attachment list has been filled. You should notice text being printed out if you open the `Serial` window on the bottom left.
  - Enjoy driving!

<a name="changeSmce"></a>
### How to change the environment
Here's a `.zip` file that gives you the option to adjust the SMCE environment. Add the `.zip` file to the mod folder in the SMCE user directory.

you can find the user directory in these places:
  - **Windows:** `%APPDATA%\Godot\app_userdata\SMCE`
  - **macOS:** `~/Library/Application Support/Godot/SMCE`
  - **Linux:** `~/.local/share/godot/SMCE`

  1. Click the top left hamburger menu
  
![env1](https://user-images.githubusercontent.com/102043616/170826879-82e18c7b-bf64-4f0b-83f1-d2bb52f41026.png)
  
  2. Use the world dropdown to select Lindholmen

![env2](https://user-images.githubusercontent.com/102043616/170826882-b59c2c15-9f25-4719-aab7-b8a6b3b6dbe1.png)

  3. Welcome on campus

![env3](https://user-images.githubusercontent.com/102043616/170826886-3c2b7bf3-d7b5-4fef-a72e-b22fd6e992d8.png)

*If you have any questions, you can look up the [instructions](https://github.com/ItJustWorksTM/smce-gd/wiki/Modding) for altering the environment in SMCE.*

<a name="mobileApp"></a>
### How to run the mobile app
Now that you have installed our app, check out the [user manual](https://github.com/DIT113-V22/group-01/wiki/9.-User-Manual) to learn how to use it

<a name="moreAboutOurApp"></a>
## Learn more about our app

<a name="softwareArchitecture"></a>
### Software Architecture
An overview of our project's structure can be found here
We designated to [UML diagram and EER diagram](https://github.com/DIT113-V22/group-01/wiki/10.-UML-Diagrams), which show our program's and database's structure in greater detail, respectively.

**Deployment Diagram:** This diagram depicts how the components communicate with one another at a high level of abstraction.

![deployment](https://user-images.githubusercontent.com/102043616/170822976-d018883e-340e-478d-a609-dc0e60c8d68d.png)


<a name="hardwareArchitecture"></a>
### Hardware Architecture
In this section we will talk about SMCE environment.
We use a variety of features for various applications, which we shall discuss in further full detail.
  - **Sensors**
 <br>**Ultra Sonic:** The car comes equipped with a number of useful sensors by default. Pins 6 and 7 are connected to an ultrasonic sensor (SR04) on the front of the car with a long range in the default setup. This sensor is used to identify obstacles. The car will come to a halt if an obstacle is within 60 cm.</br>
 <br>**Infrared:** This sensor is used to measure distances on the sides of the car between 12 and 78 cm. Their range is limited, but they are more precise and quick. In general, there are four infrared sensors on the car, front, back, left, and right.</br>
 <br>**Odometer:** Two directional odometers, one on each side, that track the distance traveled by the vehicle. In contrast to their directionless cousins, these sensors normally reveal three signal lines. The direction of movement can be deduced based on the status of this third signal.</br>
 <br>**Gyroscope:** gyroscope that can give you the car's direction in degrees [0, 360]. Clockwise movement increases the degree count, while counter-clockwise movement decreases it.</br>
 <br>**Camera:** The car has a camera that may be used to transmit what it sees. It reads a frame from the camera every 65 milliseconds and copies it into a frameBuffer. This is then sent out to the Android app via the MQTT protocol.</br></br>
 
 
  - **The current configuration for the smartcar has these attachments:**
    - 4x infrared sensors
    - 2x odometer sensors
    - 1x gyroscope sensors
    - 1x ultrasonic sensors
    - 1x camera
    - 2x brushed motors


  - **Car shield library structure (for developers):**
  <br>The purpose of this [page](https://platisd.github.io/smartcar_shield/) is to provide instructions on how to use the library's API, including explanations and examples for each exposed method.</br>
