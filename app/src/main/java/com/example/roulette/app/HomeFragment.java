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
        setHasOptionsMenu(false);
        return rootView;
    }


}
