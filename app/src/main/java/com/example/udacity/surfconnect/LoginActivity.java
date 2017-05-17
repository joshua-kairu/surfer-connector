package com.example.udacity.surfconnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.appevents.AppEventsLogger;

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

    /* AppEventsLoggers */

    private AppEventsLogger mLogger; // analytics logger

    /* METHODS */

    /* Getters and Setters */

    /* Overrides */

    @Override
    // begin onCreate
    protected void onCreate( Bundle savedInstanceState ) {

        // 0. necessaries
        // 1. check if user has already logged in
        // 1a. if s/he has, no need to stick around LoginActivity
        // 2. register app events logger

        // 0. necessaries

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        FontHelper.setCustomTypeface( findViewById( R.id.view_root ) );

        // 1. check if user has already logged in

        AccessToken currentAccessToken = AccountKit.getCurrentAccessToken();

        // 1a. if s/he has, no need to stick around LoginActivity

        if ( currentAccessToken != null ) { launchAccountActivity(); }

        // 2. register app events logger

        mLogger = AppEventsLogger.newLogger( this );

    } // end onCreate

    @Override
    // begin onActivityResult
    // this is where we know the status of the login
    protected void onActivityResult( int requestCode, int resultCode, Intent data ) {

        // 0. super stuff
        // 1. if this is what we requested
        // 1a. if the login failed, toast the user
        // 1b. else successful login, so go to account activity

        // 0. super stuff

        super.onActivityResult( requestCode, resultCode, data );

        // 1. if this is what we requested

        // begin if this is our request code
        if ( requestCode == APP_REQUEST_CODE ) {

            AccountKitLoginResult loginResult = data.getParcelableExtra(
                    AccountKitLoginResult.RESULT_KEY );

            // 1a. if the login failed, toast the user

            if ( loginResult.getError() != null ) {
                String toastMessage = loginResult.getError().getErrorType().getMessage();
                Toast.makeText( this, toastMessage, Toast.LENGTH_SHORT ).show();
            }

            // 1b. else successful login, so go to account activity

            else {
                launchAccountActivity();
            }

        } // end if this is our request code

    } // end onActivityResult

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
    // begin method onPhoneLogin
    public void onPhoneLogin ( View view ) {

        // 0. log that we're logging in via phone
        // 1. log in via phone

        // 0. log that we're logging in via phone

        mLogger.logEvent( "onPhoneLogin" );

        // 1. log in via phone

        onLogin( LoginType.PHONE );

    } // end method onPhoneLogin

    /**
     * Called when the "Login with Email" button is tapped.
     *
     * Calls {@link LoginActivity#onLogin(LoginType)} with an Email login type.
     */
    // begin method onEmailLogin
    public void onEmailLogin ( View view ) {

        // 0. log that we're logging in via email
        // 1. log in via email

        // 0. log that we're logging in via email

        mLogger.logEvent( "onEmailLogin" );

        // 1. log in via email

        onLogin( LoginType.EMAIL );

    } // end method onEmailLogin

    /** Launches the {@link AccountActivity} and terminates the {@link LoginActivity}. */
    // begin method launchAccountActivity
    private void launchAccountActivity() {
        Intent intent = new Intent( this, AccountActivity.class );
        startActivity( intent );
        finish();
    } // end method launchAccountActivity

} // end LoginActivity
