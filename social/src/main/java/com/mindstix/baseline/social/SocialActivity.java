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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import java.util.Arrays;

/**
 * Activity for different social media logins.
 *
 * @author Mindstix Software Labs, Inc.
 */

public class SocialActivity extends AppCompatActivity {

    // Global constants.
    public static final String FACEBOOK_LOGIN = "isFacebookEnabled";
    public static final String TWITTER_LOGIN = "isTwitterEnabled";
    public static final String GOOGLE_LOGIN = "isGoogleEnabled";
    public static final String INSTAGRAM_LOGIN = "isInstagramEnabled";
    public static final String LINKEDIN_LOGIN = "isLinkedInEnabled";

    private static final int GOOGLE_REQUEST_CODE = 50001;

    // Facebook.
    private Button facebookLoginButton = null;
    private CallbackManager callbackManager = null;

    // Twitter.
    private Button twitterLoginButton = null;
    private TwitterAuthClient twitterAuthClient = null;

    // Google.
    private Button googleLoginButton = null;
    private GoogleSignInOptions googleSignInOptions = null;
    private GoogleSignInClient googleSignInClient = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);

        facebookLoginButton = findViewById(R.id.facebook_login_button);
        twitterLoginButton = findViewById(R.id.twitter_login_button);

        handleSocialLogins(getIntent());
    }

    private void handleSocialLogins(Intent socialIntent) {
        LinearLayout facebookLayout = findViewById(R.id.facebook_layout);
        LinearLayout twitterLayout = findViewById(R.id.twitter_layout);
        LinearLayout googleLayout = findViewById(R.id.google_layout);

        // Get which social logins to be enabled.
        boolean isFacebook = socialIntent.getBooleanExtra(FACEBOOK_LOGIN, false);
        boolean isTwitter = socialIntent.getBooleanExtra(TWITTER_LOGIN, false);
        boolean isGoogle = socialIntent.getBooleanExtra(GOOGLE_LOGIN, false);
        boolean isInstagram = socialIntent.getBooleanExtra(INSTAGRAM_LOGIN, false);
        boolean isLinkedin = socialIntent.getBooleanExtra(LINKEDIN_LOGIN, false);

        // If none of the social platform is enabled then kill activity.
        boolean isSocialEnabled = false;

        if (isFacebook) {
            isSocialEnabled = true;

            facebookLayout.setVisibility(LinearLayout.VISIBLE);

            // Redirect to Facebook Login handler.
            facebookLogin();
        }

        if (isTwitter) {
            isSocialEnabled = true;

            twitterLayout.setVisibility(LinearLayout.VISIBLE);

            // Redirect to Twitter Login handler.
            twitterLogin();
        }

        if (isGoogle) {
            isSocialEnabled = true;

            googleLayout.setVisibility(LinearLayout.VISIBLE);

            // Configure Google Sign In.
            configureGoogleSignIn();
        }

        // If none of the social platform is enabled then kill activity.
        if (!isSocialEnabled) {
            this.finish();
        }
    }

    /**
     * Facebook login button click listener.
     *
     * @param facebookLayout
     */
    public void onFacebookLoginClick(View facebookLayout) {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"));
    }

    /**
     * Facebook login handler.
     */
    private void facebookLogin() {
        // Register a callback.
        callbackManager = CallbackManager.Factory.create();

        // Callback registration.
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
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
     * Twitter login button click listener.
     *
     * @param twitterLayout
     */
    public void onTwitterLoginClick(View twitterLayout) {
        // Register Twitter Login button callback.
        twitterAuthClient.authorize(this, new Callback<TwitterSession>() {
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

    /**
     * Twitter login handler.
     */
    private void twitterLogin() {
        // Initialise Twitter auth client.
        twitterAuthClient = new TwitterAuthClient();
    }

    /**
     * Configure Google login.
     */
    private void configureGoogleSignIn() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by GoogleSignInOptions.
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
    }

    /**
     * Google login button click listener.
     */
    public void onGoogleLoginClick(View googleLoginButton) {
        Log.d("Baseline", "Google clicked");

        Intent googleLoginIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(googleLoginIntent, GOOGLE_REQUEST_CODE);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        // updateUI(account);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Return Facebook Login result.
        callbackManager.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the Twitter login button.
        twitterAuthClient.onActivityResult(requestCode, resultCode, data);

        // Google login result.
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == GOOGLE_REQUEST_CODE) {
            // The Task returned from this call is always completed, no need to attach a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            // handleSignInResult(task);
        }

    }
}
