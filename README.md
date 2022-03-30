![Crushers #1](https://img.shields.io/static/v1?label=%F0%9F%90%B1%E2%80%8D%F0%9F%92%BB%20Crushers&message=%231&labelColor=9454bf&color=9454bf&style=for-the-badge) 

Tests:

![example workflow](https://github.com/DIT113-V22/group-01/actions/workflows/arduino-build.yml/badge.svg)

[🖼 Project Idea Presentation](https://docs.google.com/presentation/d/16SZ2ToYdbLL906brSV6_ACyWLJ0RKk2l5obWzS27Ddo/edit#slide=id.p)

***

# Crushers
A group of 6 like-minded people ready to tackle any challenge!<br>
For more info on how we tackle the challenge, have a look at our [wiki](https://github.com/DIT113-V22/group-01/wiki)!


## The Project: Crash Course

### What we are going to make

Crushers is dedicated to prepare you to **crush the test**, the theoretical drivers license test that is. We are going to do so by providing an app which quizes you on the traffic laws, signs, and scenarios. Our app will provide you with explanations, visual illustrations, and a hands on manual driving mode to give you the full experience and prepare you like you have never before been prepared for a test.

### Why we will make it

The simple reason why we assigned ourselves with this mission is the fail rate for the drivers license exam, theory and driving. Keeping calm and focused in a busy and noisy environment as the road, especially within cities, can be difficult and leads to accidents yearly. Some accidents, however, could be mitigated through better knowledge of driving theory. To date most apps focus on the theory exam, but our goal is to combine the theory with visual illustrations showing why a certain traffic law/sign is necessary and a manual driving mode in a virtual city to also train you for the driving test.

**What problem it solves**

Fail rate of drivers license exams in Sweden (2021)
![fail rate at drivers license exams in Sweden](https://user-images.githubusercontent.com/52662014/160342758-7d2307f5-fed8-4d68-b21b-6e3106f1162e.png)

*source: https://korkortonline.se/en/facts/statistics/*

### How we are going to make it
To accomplish our goal, we will create a remotely accessible api for the car, provide our end users with an android app, and lastly create a city environment for the emulator for the manual driving and scenario demonstrations. The main focus for the project will lie on the app, and depending on the time available at the end we have some cool ideas for the emulators city environment.

**What kind of technology we are going to use**
| Component | Technologies |
| --------- | ------------ |
| Car controller | Arduino, C++, smartcar shield |
| Car - App communication | MQTT |
| App | Android, Java |
| City environment | SMCE, Blender, etc. |
