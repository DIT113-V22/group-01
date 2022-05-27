![Crushers #1](https://img.shields.io/static/v1?label=%F0%9F%90%B1%E2%80%8D%F0%9F%92%BB%20Crushers&message=%231&labelColor=9454bf&color=9454bf&style=for-the-badge) 
![Arduino Build Status](https://github.com/DIT113-V22/group-01/actions/workflows/arduino-build.yml/badge.svg)
![Android Build Status](https://github.com/DIT113-V22/group-01/actions/workflows/android-build.yml/badge.svg)

# Crushers
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
   - 3.3. [ How to run the mobile app ](#mobileApp)
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
    - We will need a broker to connect the app to the SMCE so we can practice driving.

    - **How may a Mosquitto broker be set up?**
    - Here's a clear explanation for how to download Mosquitto. Choose your operating system and follow the instructions:[Download Mosquitto](https://mosquitto.org/download/) 

2. We need to download SMCE (the car environment), which will allow you to practice and improve your skills.
    - **How are we going about doing it?**
    - Please see this page for instructions on how to install SMCE on [MacOs](https://github.com/ItJustWorksTM/smce-gd/wiki/MacOS-setup), [Windows](https://github.com/ItJustWorksTM/smce-gd/wiki/Windows-setup), or [Debian based Linux](https://github.com/ItJustWorksTM/smce-gd/wiki/Debian-based-Linux-setup)

3. Download **Crusher's crash course** from Google play

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

