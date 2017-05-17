package com.example.udacity.surfconnect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.PhoneNumber;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.Locale;

public class AccountActivity extends AppCompatActivity {

    TextView id;
    TextView infoLabel;
    TextView info;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {

        // 0. necessaries
        // 1. extract account info
        // 1a. account kit id
        // 1b. phone (or email if phone is unavailable)
        // 1-last. toast any errors

        // 0. necessaries

        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_account );
        FontHelper.setCustomTypeface( findViewById( R.id.view_root ) );

        id = ( TextView ) findViewById( R.id.id );
        infoLabel = ( TextView ) findViewById( R.id.info_label );
        info = ( TextView ) findViewById( R.id.info );

        // 1. extract account info

        // begin AccountKit.getCurrentAccount
        AccountKit.getCurrentAccount( new AccountKitCallback< Account >() {

            @Override
            // begin onSuccess
            public void onSuccess( Account account ) {

                // 1a. account kit id

                String accountKitId = account.getId();

                id.setText( accountKitId );

                // 1b. phone (or email if phone is unavailable)

                PhoneNumber phoneNumber = account.getPhoneNumber();

                // begin if there is a phone number
                if ( phoneNumber != null ) {

                    String formattedPhoneNumber = formatPhoneNumber( phoneNumber.toString() );

                    info.setText( formattedPhoneNumber );
                    infoLabel.setText( R.string.phone_label );

                } // end if there is a phone number

            } // end onSuccess

            // 1-last. toast any errors

            @Override
            public void onError( AccountKitError accountKitError ) {
                String toastMessage = accountKitError.getErrorType().getMessage();
                Toast.makeText( AccountActivity.this, toastMessage, Toast.LENGTH_SHORT ).show();
            }

        } ); // end AccountKit.getCurrentAccount

    }

    private void launchLoginActivity() {
        Intent intent = new Intent( this, LoginActivity.class );
        startActivity( intent );
        finish();
    }

    private String formatPhoneNumber( String phoneNumber ) {
        // helper method to format the phone number for display
        try {
            PhoneNumberUtil pnu = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber pn = pnu.parse( phoneNumber, Locale.getDefault().getCountry() );
            phoneNumber = pnu.format( pn, PhoneNumberUtil.PhoneNumberFormat.NATIONAL );
        } catch ( NumberParseException e ) {
            e.printStackTrace();
        }
        return phoneNumber;
    }

}
