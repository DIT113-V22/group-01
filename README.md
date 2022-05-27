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
