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

package com.mindstix.baseline.social;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

/**
 * Activity for different social media logins.
 *
 * @author Mindstix Software Labs, Inc.
 */

public class SocialActivity extends AppCompatActivity {

    // Facebook.
    private CallbackManager callbackManager = null;

    // Twitter.
    private TwitterLoginButton twitterLoginButton = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);

        // Redirect to Facebook Login handler.
        facebookLogin();

        // Redirect to Twitter Login handler.
        twitterLogin();
    }

    /**
     * Facebook login handler.
     */
    private void facebookLogin() {
        // Register Facebook Login button callback.
        LoginButton facebookLoginButton = findViewById(R.id.facebook_login_button);
        facebookLoginButton.setReadPermissions("email");

        // If using in a fragment.
        // loginButton.setFragment(this);

        callbackManager = CallbackManager.Factory.create();

        // Callback registration.
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // TODO: Log Firebase event.
                Toast.makeText(getApplicationContext(), "Facebook Login Succeeded.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                // TODO: Log Firebase event.
                Toast.makeText(getApplicationContext(), "Facebook Login Cancelled.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // TODO: Log Firebase event.
                Toast.makeText(getApplicationContext(), "Facebook Login Error.", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Twitter login handler.
     */
    private void twitterLogin() {
        // Register Twitter Login button callback.
        twitterLoginButton = findViewById(R.id.twitter_login_button);

        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                // TODO: Log Firebase event.
                Toast.makeText(getApplicationContext(), "Twitter Login Succeeded.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(TwitterException exception) {
                // TODO: Log Firebase event.
                Toast.makeText(getApplicationContext(), "Twitter Login Failure.", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Facebook call needs to be before super method call.
        // Return Facebook Login result.
        callbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }
}
