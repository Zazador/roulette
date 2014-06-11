package com.example.roulette.app;

/**
 * Created by zach on 5/28/14.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseUser;

public class LoginRegisterActivity extends Activity{
    SharedPreferences mPrefs;
    final String welcomeScreenShownPref = "welcomeScreenShown";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // second argument is the default to use if the preference can't be found
        Boolean welcomeScreenShown = mPrefs.getBoolean(welcomeScreenShownPref, false);

        if (!welcomeScreenShown) {
            // here you can launch another activity if you like
            // the code below will display a popup

            String whatsNewTitle = getResources().getString(R.string.whatsNewTitle);
            String whatsNewText = getResources().getString(R.string.whatsNewText);
            Toast.makeText(getApplicationContext(), "WELCOME SCREEN", Toast.LENGTH_SHORT).show();
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle(whatsNewTitle).setMessage(whatsNewText).setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putBoolean(welcomeScreenShownPref, true);
            editor.commit(); // Saves the preference
        }

        // Determine whether the current user is an anonymous user
        if (ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
            // If user is anonymous, send the user to LoginSignupActivity.class
            Intent intent = new Intent(LoginRegisterActivity.this,
                    SignupActivity.class);
            startActivity(intent);
            finish();
        } else {
            // If current user is NOT anonymous user
            // Get current user data from Parse.com
            ParseUser currentUser = ParseUser.getCurrentUser();
            if (currentUser != null) {
                // Send logged in users to Welcome.class
                Intent intent = new Intent(LoginRegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Send user to LoginSignupActivity.class
                Intent intent = new Intent(LoginRegisterActivity.this,
                        SignupActivity.class);
                startActivity(intent);
                finish();
            }
        }

    }
}
