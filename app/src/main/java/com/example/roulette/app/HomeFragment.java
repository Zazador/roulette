package com.example.roulette.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * Created by zach on 6/11/14.
 */
public class HomeFragment extends Fragment {
    SharedPreferences mPrefs;
    final String welcomeScreenShownPref = "welcomeScreenShown";

    public HomeFragment() {}

    /**
     * THIS NEEDS TO BE IMPLEMENTED BEFORE YOU CAN USE getActivity()
     */
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        DBHelper = new DatabaseHelper(activity);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

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

        return rootView;
    }


}
