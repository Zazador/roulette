package com.example.roulette.app;

/**
 * Created by zach on 5/28/14.
 */

import com.parse.ParseUser;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

public class Welcome extends Activity {

    // Declare Variable
    Button logout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // Get the view from singleitemview.xml
        setContentView(R.layout.welcome);

        // Retrieve current user from Parse.com
        ParseUser currentUser = ParseUser.getCurrentUser();

        // Convert currentUser into String
        String struser = currentUser.getUsername().toString();

        // Locate TextView in welcome.xml
        TextView txtuser = (TextView) findViewById(R.id.txtuser);

        // Set the currentUser String into TextView
        if (currentUser.isNew())
            txtuser.setText("Welcome" + struser + "! Let's get started.");
        else
            txtuser.setText("You are logged in as " + struser);

        // Locate Button in welcome.xml
        logout = (Button) findViewById(R.id.logout);


        // Logout Button Click Listener
        logout.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                // Logout current user
                ParseUser.logOut();
                finish();
            }
        });

        // Execute a signed call to the Yelp service
        OAuthService service = new ServiceBuilder()
                .provider(YelpV2API.class)
                .apiKey("M8HBn3S_-bKLXLIRemDf9w")
                .apiSecret("iynjTDafI6Tth7fPtiz56vJLd70")
                .build();
        Token accessToken = new Token("z6jDMmgQEE60NQMWl70oVGd6x--Ypv5Y", "bgeFFbkd5GXttsHzYtIsehYUHFE");
        //We want to perform a search
        OAuthRequest request = new OAuthRequest(Verb.GET,
                "http://api.yelp.com/v2/search");
        // Based on a GPS coordinate latitude / longitude
        request.addQuerystringParameter("ll", "30.361471, -87.164326");
        // Looking for any restaurants
        request.addQuerystringParameter("category", "restaurants");
        service.signRequest(accessToken, request);
        Response response = request.send();
        String rawData = response.getBody();

        // Display the returned results.
        System.out.println(rawData);

    }
}
