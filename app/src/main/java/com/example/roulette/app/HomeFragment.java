package com.example.roulette.app;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by zach on 6/11/14.
 */
public class HomeFragment extends Fragment {
    // Declare Variables
    Button loginbutton;
    Button signup;
    String usernametxt;
    String passwordtxt;
    EditText password;
    EditText username;

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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Locate EditTexts in fragment_home.xml
        username = (EditText) view.findViewById(R.id.username);
        password = (EditText) view.findViewById(R.id.password);

        // Locate Buttons in fragment_home.xml
        loginbutton = (Button) view.findViewById(R.id.login);
        signup = (Button) view.findViewById(R.id.signup);

        // Login Button Click Listener
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve the text entered from the EditText
                usernametxt = username.getText().toString();
                passwordtxt = password.getText().toString();

                // Send data to Parse.com for verification
                ParseUser.logInInBackground(usernametxt, passwordtxt, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            // If user exists and authenticated, send user to Find Restaurant Fragment

                            // Hides keyboard before switching fragments
                            final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                            // Create new fragment and transaction
                            FindRestaurantFragment newFragment = new FindRestaurantFragment();
                            FragmentTransaction transaction = getFragmentManager().beginTransaction();

                            // Replace whatever is in the fragment_container view with this fragment,
                            // and add the transaction to the back stack
                            transaction.replace(R.id.frame_container, newFragment);
                            transaction.addToBackStack(null);

                            // Commit the transaction
                            transaction.commit();

                            Toast.makeText(getActivity(), "Successfully logged in!", Toast.LENGTH_LONG).show();
                        } else {
                            // If user does not exist
                            Toast.makeText(getActivity(), "No such user exists. Please register.", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });

        //Sign up Button Click Listener
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Retrieve the ext entered from EditText
                usernametxt = username.getText().toString();
                passwordtxt = password.getText().toString();

                // Force user to fill up the form
                if (usernametxt.equals("") || passwordtxt.equals("")) {
                    Toast.makeText(getActivity(), "Please fully complete the forms.", Toast.LENGTH_LONG).show();
                } else {
                    // Save new user data into Parse.com Data Storage
                    ParseUser user = new ParseUser();
                    user.setUsername(usernametxt);
                    user.setPassword(passwordtxt);
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                // Show a simple Toast message upon successful registration
                                Toast.makeText(getActivity(), "Sucessfully signed up! Please login.", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getActivity(), "Sign Up Error.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        return view;
    }


}
