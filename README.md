Mindstix Android Accelerator
============================

##About

Mindstix Android Accelerator is a baseline framework for Android. It helps any Android developer to kickstart native Android application development.

##Features

Baseline framework provides,

* Basic directory structure for Android application project.
* Build variants - Demo, Dev, QA, Prod.
* Network layer using Retrofit.
* Analytics integration using Google Firebase Analytics.
* Crashlytics integration using Google Firebase Crashlytics.
* Sample service integration using OpenWeatherMap Weather and Forecast APIs.
* Sample Fragment showing Weather information fetched from API.

##Developer Guide

* Replace package name in 'app' build.gradle file with your required package name.
* Replace package name in 'AndroidManifest.xml' file with same package name provided in build.gradle.
* For Firebase Crashlytics and Analytics to work in your app, you need to login to [Firebase Console][firebase-console]. Create you app by simply providing application package.
* Replace Firebase Analytics and Crashlytics configuration file 'google-services.json' under 'app' module with your Firebase configuration file. Get started [here][firebase-crashlytics].
* Replace UI components like MainActivity, HomeFragement, HomeAdapter with your required components.
* Replace XML layout components like activity_main, fragment_home, weather_details with your required layout components.

##Releases

Version 0.1 - 29th December 2017.

* Basic directory structure for Android application project.
* Build variants - Demo, Dev, QA, Prod.
* Network layer using Retrofit.
* Analytics integration using Google Firebase Analytics.
* Crashlytics integration using Google Firebase Crashlytics.
* Sample service integration using OpenWeatherMap Weather and Forecast APIs.
* Sample Fragment showing Weather information fetched from API.

##Pipeline

* Local Storage Access APIs - Shared Preferences, SQLite.
* Push Notification integration - Firebase.
* Localisation Support.
* Utilities.
* Unit test coverage.

---
[firebase-console]: https://firebase.google.com/console/
[firebase-crashlytics]: https://firebase.google.com/docs/crashlytics/