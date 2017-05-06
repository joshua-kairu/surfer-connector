package com.example.udacity.surfconnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

/**
 * Logs the user in.
 */
// begin LoginActivity
public class LoginActivity extends AppCompatActivity {

    /* CONSTANTS */

    /* Integers */

    private static final int APP_REQUEST_CODE = 1; // for starting with a result

    /* Strings */

    /* VARIABLES */

    /* CONSTRUCTOR */

    /* METHODS */

    /* Getters and Setters */

    /* Overrides */

    @Override
    // begin onCreate
    protected void onCreate( Bundle savedInstanceState ) {

        // 0. necessaries
        // 1. check if user has already logged in
        // 1a. if s/he has, no need to stick around LoginActivity

        // 0. necessaries

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        FontHelper.setCustomTypeface( findViewById( R.id.view_root ) );

        // 1. check if user has already logged in

        AccessToken currentAccessToken = AccountKit.getCurrentAccessToken();

        // 1a. if s/he has, no need to stick around LoginActivity

        if ( currentAccessToken != null ) { launchAccountActivity(); }

    } // end onCreate

    /* Other Methods */

    /** Implements the token request. */
    // begin method onLogin
    private void onLogin( LoginType loginType ) {

        // 0. create intent to launch account kit activity
        // 1. build an account kit config based on account type and response type
        // 2. package config as extra and launch account kit activity

        // 0. create intent to launch account kit activity

        Intent intent = new Intent( this, AccountKitActivity.class );

        // 1. build an account kit config based on account type and response type

        AccountKitConfiguration.AccountKitConfigurationBuilder builder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder( loginType,
                        AccountKitActivity.ResponseType.TOKEN  /*since we're using the client
                        access token method*/ );

        AccountKitConfiguration configuration = builder.build();

        // 2. package config as extra and launch account kit activity

        intent.putExtra( AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configuration );

        startActivityForResult( intent, APP_REQUEST_CODE );

    } // end method onLogin

    /**
     * Called when the "Login with Phone" button is tapped.
     *
     * Calls {@link LoginActivity#onLogin(LoginType)} with a Phone login type.
     */
    public void onPhoneLogin ( View view ) { onLogin( LoginType.PHONE ); }

    /**
     * Called when the "Login with Email" button is tapped.
     *
     * Calls {@link LoginActivity#onLogin(LoginType)} with an Email login type.
     */
    public void onEmailLogin ( View view ) { onLogin( LoginType.EMAIL ); }

    /** Launches the {@link AccountActivity} and terminates the {@link LoginActivity}. */
    // begin method launchAccountActivity
    private void launchAccountActivity() {
        Intent intent = new Intent( this, AccountActivity.class );
        startActivity( intent );
        finish();
    } // end method launchAccountActivity

} // end LoginActivity
