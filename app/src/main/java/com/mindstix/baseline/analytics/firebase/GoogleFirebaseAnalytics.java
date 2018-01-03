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

package com.mindstix.baseline.analytics.firebase;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.mindstix.baseline.analytics.Analytics;
import com.mindstix.baseline.analytics.exceptions.FirebaseException;

import java.util.Map;

/**
 * Class to send analytics report to Google Firebase Analytics platform.
 *
 * @author Mindstix Software Labs, Inc.
 */

public class GoogleFirebaseAnalytics {

    // Instance of FirebaseAnalytics.
    private static FirebaseAnalytics firebaseAnalytics = null;

    /**
     * Method to initialise Google Firebase Analytics.
     *
     * @param appContext - Application context to initialise FirebaseAnalytics.
     * @throws FirebaseException - If application context is not passed.
     */
    public static void initialiseFirebaseAnalytics(Context appContext) throws FirebaseException {
        // Validate application context.
        if(appContext == null) {
            throw new FirebaseException("Empty or invalid application context.");
        }

        firebaseAnalytics = FirebaseAnalytics.getInstance(appContext);
    }

    /**
     * Method to log event to Firebase Anlytics platform.
     *
     * @param eventParams - Set of parameters to be logged for event type.
     * @throws FirebaseException - If Firebase is not initialised or event params are not valid.
     */
    public static void logEvent(Map<String, String> eventParams) throws FirebaseException {
        // Validate FirebaseAnalytics instance.
        if(firebaseAnalytics == null) {
            throw new FirebaseException("Firebase analytics not initialised properly.");
        }

        // Validate event parameters collection.
        if(eventParams == null || eventParams.size() == 0) {
            throw new FirebaseException("Empty parameters passed to Firebase analytics.");
        }

        // Validate parameter id.
        if(TextUtils.isEmpty(eventParams.get(Analytics.PARAM_ID))) {
            throw new FirebaseException("Empty or invalid parameter id is passed.");
        }

        // Validate parameter name.
        if(TextUtils.isEmpty(eventParams.get(Analytics.PARAM_NAME))) {
            throw new FirebaseException("Empty or invalid parameter name is passed.");
        }

        // Validate parameter type.
        if(TextUtils.isEmpty(eventParams.get(Analytics.PARAM_TYPE))) {
            throw new FirebaseException("Empty or invalid parameter type is passed.");
        }

        // Validate event type.
        if(TextUtils.isEmpty(eventParams.get(Analytics.EVENT_TYPE))) {
            throw new FirebaseException("Empty or invalid event type is passed.");
        }

        Bundle eventBundle = new Bundle();
        eventBundle.putString(FirebaseAnalytics.Param.ITEM_ID, eventParams.get(Analytics.PARAM_ID));
        eventBundle.putString(FirebaseAnalytics.Param.ITEM_NAME, eventParams.get(Analytics.PARAM_NAME));
        eventBundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, eventParams.get(Analytics.PARAM_TYPE));

        // Log Firebase Analytics event.
        firebaseAnalytics.logEvent(eventParams.get(Analytics.EVENT_TYPE), eventBundle);
    }
}
