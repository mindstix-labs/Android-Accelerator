/*
 * Copyright (c) 2017-18 Mindstix Software Labs, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.mindstix.baseline;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;
import com.mindstix.baseline.analytics.Analytics;
import com.mindstix.baseline.home.HomeFragment;
import com.mindstix.baseline.social.SocialActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Launcher Activity/Starting point of the application.
 * This class is an entry point of the application. User will be landed here upon launching the application.
 *
 * @author Mindstix Software Labs, Inc.
 */

public class MainActivity extends AppCompatActivity {

    // Global constants.
    private static final int SOCIAL_REQUEST_CODE = 10001;
    private static final int SOCIAL_RESULT_CODE = 20001;

    // Singleton analytics instance.
    private Analytics analyticsInstance = null;

    /**
     * You must implement this callback, which fires when the system first creates the activity.
     * On activity creation, the activity enters the Created state. In the onCreate() method,
     * you perform basic application startup logic that should happen only once for the entire life
     * of the activity. For example, your implementation of onCreate() might bind data to lists,
     * initialize background threads, and instantiate some class-scope variables.
     *
     * @param savedInstanceState - Bundle object containing the activity's previously saved state.
     *                           If the activity has never existed before, the value of the Bundle object is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (FlavorSpecific.SOCIAL_LOGIN) {
            Intent socialActivityIntent = new Intent(this, SocialActivity.class);
            startActivityForResult(socialActivityIntent, SOCIAL_REQUEST_CODE);
        } else {
            // Bind XML layout with Activity.
            setContentView(R.layout.activity_main);

            // Start Home Fragment.
            HomeFragment homeFragment = new HomeFragment();

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, homeFragment);
            fragmentTransaction.commit();

            // Initialise analytics instance.
            try {
                analyticsInstance = Analytics.getInstance(Analytics.PLATFORM_FIREBASE_ANALYTICS, getApplicationContext());
            } catch (Exception exception) {
                FirebaseCrash.report(exception);
                exception.printStackTrace();
            }

            // Track analytics events at the end of the block.
            if (analyticsInstance != null) {
                Map<String, String> eventParams = new HashMap<>();
                eventParams.put(Analytics.PARAM_ID, "1");
                eventParams.put(Analytics.PARAM_NAME, "MainActivity");
                eventParams.put(Analytics.PARAM_TYPE, "onCreate");
                eventParams.put(Analytics.EVENT_TYPE, FirebaseAnalytics.Event.APP_OPEN);

                try {
                    analyticsInstance.logEvent(eventParams);
                } catch (Exception e) {
                    FirebaseCrash.report(e);
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Track analytics events at the end of the block.
        if (analyticsInstance != null) {
            Map<String, String> eventParams = new HashMap<>();
            eventParams.put(Analytics.PARAM_ID, "1");
            eventParams.put(Analytics.PARAM_NAME, "MainActivity");
            eventParams.put(Analytics.PARAM_TYPE, "onResume");
            eventParams.put(Analytics.EVENT_TYPE, FirebaseAnalytics.Event.APP_OPEN);

            try {
                analyticsInstance.logEvent(eventParams);
            } catch (Exception e) {
                FirebaseCrash.report(e);
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Track analytics events at the end of the block.
        if (analyticsInstance != null) {
            Map<String, String> eventParams = new HashMap<>();
            eventParams.put(Analytics.PARAM_ID, "1");
            eventParams.put(Analytics.PARAM_NAME, "MainActivity");
            eventParams.put(Analytics.PARAM_TYPE, "onStart");
            eventParams.put(Analytics.EVENT_TYPE, FirebaseAnalytics.Event.APP_OPEN);

            try {
                analyticsInstance.logEvent(eventParams);
            } catch (Exception e) {
                FirebaseCrash.report(e);
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // Track analytics events at the end of the block.
        if (analyticsInstance != null) {
            Map<String, String> eventParams = new HashMap<>();
            eventParams.put(Analytics.PARAM_ID, "1");
            eventParams.put(Analytics.PARAM_NAME, "MainActivity");
            eventParams.put(Analytics.PARAM_TYPE, "onRestart");
            eventParams.put(Analytics.EVENT_TYPE, FirebaseAnalytics.Event.APP_OPEN);

            try {
                analyticsInstance.logEvent(eventParams);
            } catch (Exception e) {
                FirebaseCrash.report(e);
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Track analytics events at the end of the block.
        if (analyticsInstance != null) {
            Map<String, String> eventParams = new HashMap<>();
            eventParams.put(Analytics.PARAM_ID, "1");
            eventParams.put(Analytics.PARAM_NAME, "MainActivity");
            eventParams.put(Analytics.PARAM_TYPE, "onPause");
            eventParams.put(Analytics.EVENT_TYPE, FirebaseAnalytics.Event.APP_OPEN);

            try {
                analyticsInstance.logEvent(eventParams);
            } catch (Exception e) {
                FirebaseCrash.report(e);
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Track analytics events at the end of the block.
        if (analyticsInstance != null) {
            Map<String, String> eventParams = new HashMap<>();
            eventParams.put(Analytics.PARAM_ID, "1");
            eventParams.put(Analytics.PARAM_NAME, "MainActivity");
            eventParams.put(Analytics.PARAM_TYPE, "onStop");
            eventParams.put(Analytics.EVENT_TYPE, FirebaseAnalytics.Event.APP_OPEN);

            try {
                analyticsInstance.logEvent(eventParams);
            } catch (Exception e) {
                FirebaseCrash.report(e);
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Track analytics events at the end of the block.
        if (analyticsInstance != null) {
            Map<String, String> eventParams = new HashMap<>();
            eventParams.put(Analytics.PARAM_ID, "1");
            eventParams.put(Analytics.PARAM_NAME, "MainActivity");
            eventParams.put(Analytics.PARAM_TYPE, "onDestroy");
            eventParams.put(Analytics.EVENT_TYPE, FirebaseAnalytics.Event.APP_OPEN);

            try {
                analyticsInstance.logEvent(eventParams);
            } catch (Exception e) {
                FirebaseCrash.report(e);
                e.printStackTrace();
            }
        }
    }
}
