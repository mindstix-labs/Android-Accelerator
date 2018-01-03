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

package com.mindstix.baseline.analytics;

import android.content.Context;
import android.text.TextUtils;

import com.mindstix.baseline.analytics.firebase.GoogleFirebaseAnalytics;
import com.mindstix.baseline.analytics.exceptions.AnalyticsException;
import com.mindstix.baseline.analytics.exceptions.FirebaseException;

import java.util.Map;

/**
 * Wrapper class for all Analytics platforms integrated in framework.
 * Supported Analytics platforms are
 * - Google Firebase Analytics.
 *
 * @author Mindstix Software Labs, Inc.
 */

public class Analytics {

    // Constants for providing type of Analytics platform one wants to use.
    public static final String PLATFORM_FIREBASE_ANALYTICS = "Firebase";

    public static final String PARAM_ID = "param_id";
    public static final String PARAM_NAME = "param_name";
    public static final String PARAM_TYPE = "param_type";
    public static final String EVENT_TYPE = "event_type";

    // Singleton instance of Analytics class.
    private static Analytics analytics;

    private static String analyticsPlatform = null;

    private Analytics() {

    }

    /**
     * For thread-safe approach, double checking singleton instance.
     *
     * @param analyticsPlatform - Type of Analytics platform one wants to use.
     * @return instance - Instance of type Analytics.
     * @throws AnalyticsException - If Analytics platform is not provided.
     */
    public static Analytics getInstance(String analyticsPlatform, Context appContext)
            throws AnalyticsException, FirebaseException {

        // Validate application context.
        if(appContext == null) {
            throw new AnalyticsException("Empty or invalid application context.");
        }

        // Validate Analytics platform.
        if(TextUtils.isEmpty(analyticsPlatform)) {
            throw new AnalyticsException("Analytics platform not provided.");
        }

        if (analytics == null) {
            synchronized (Analytics.class) {
                // Double check for thread safe.
                if (analytics == null) {
                    analytics = new Analytics();
                    Analytics.setAnalyticsPlatform(analyticsPlatform, appContext);
                }
            }
        }

        return analytics;
    }

    /**
     * Private method to set type of Analytics platform one wants to use.
     *
     * @param analyticsPlatform - Type of Analytics platform one wants to use.
     * @param appContext - Context to be passed to individual Analytics platform.
     * @throws AnalyticsException - If Analytics platform is not provided.
     */
    private static void setAnalyticsPlatform(String analyticsPlatform, Context appContext)
            throws AnalyticsException, FirebaseException {

        // Remember analytics platform.
        Analytics.analyticsPlatform = analyticsPlatform;

        switch (analyticsPlatform) {
            case Analytics.PLATFORM_FIREBASE_ANALYTICS:
                // Get FirebaseAnalytics instance.
                GoogleFirebaseAnalytics.initialiseFirebaseAnalytics(appContext);
                break;

            default:
                throw new AnalyticsException("Invalid Analytics platform provided.");
        }
    }

    /**
     * Method to log events to chosen Analytics platform.
     *
     * @param eventParams - Collection of analytics event parameters to be logged.
     * @throws AnalyticsException - If Analytics platform is not chosen.
     */
    public void logEvent(Map<String, String> eventParams)
            throws AnalyticsException, FirebaseException {

        switch (analyticsPlatform) {
            case Analytics.PLATFORM_FIREBASE_ANALYTICS:

                // Log Firebase Analytics event.
                GoogleFirebaseAnalytics.logEvent(eventParams);

                break;

            default:
                throw new AnalyticsException("Invalid Analytics platform provided.");
        }
    }
}
