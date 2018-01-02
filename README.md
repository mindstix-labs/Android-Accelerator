Mindstix Android Accelerator
============================

## About

Mindstix Android Accelerator is a baseline framework for Android. It helps any Android developer to kickstart native Android application development.

## Features

Baseline framework provides,

* Basic directory structure for Android application project.
* Build variants - Demo, Dev, QA, Prod.
* Network layer using Retrofit.
* Analytics integration using Google Firebase Analytics.
* Crashlytics integration using Google Firebase Crashlytics.
* Sample service integration using OpenWeatherMap Weather and Forecast APIs.
* Sample Fragment showing Weather information fetched from API.

## Developer Guide

* Replace package name in 'app' build.gradle file with your required package name.
* Replace package name in 'AndroidManifest.xml' file with same package name provided in build.gradle.
* For Firebase Crashlytics and Analytics to work in your app, you need to login to [Firebase Console][firebase-console]. Create you app by simply providing application package.
* Replace Firebase Analytics and Crashlytics configuration file 'google-services.json' under 'app' module with your Firebase configuration file. Get started [here][firebase-crashlytics].
* Replace UI components like MainActivity, HomeFragement, HomeAdapter with your required components.
* Replace XML layout components like activity_main, fragment_home, weather_details with your required layout components.

## Releases

Version 0.1 - 29th December 2017.

* Basic directory structure for Android application project.
* Build variants - Demo, Dev, QA, Prod.
* Network layer using Retrofit.
* Analytics integration using Google Firebase Analytics.
* Crashlytics integration using Google Firebase Crashlytics.
* Sample service integration using OpenWeatherMap Weather and Forecast APIs.
* Sample Fragment showing Weather information fetched from API.

## Pipeline

* Local storage access APIs - Shared Preferences, SQLite.
* Push Notification integration - Firebase.
* Localisation support.
* Utilities.
* Unit test coverage.

## License

MIT License

Copyright (c) 2017 Mindstix Software Labs, Inc.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

[firebase-console]: https://firebase.google.com/console/
[firebase-crashlytics]: https://firebase.google.com/docs/crashlytics/
