package com.example.roulette.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import org.w3c.dom.Text;

/**
 * Created by zach on 6/11/14.
 */
public class SearchFragment extends Fragment {
        public SearchFragment() {}

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_search, container, false);

            // Execute a signed call to the Yelp service
            OAuthService service = new ServiceBuilder()
                    .provider(YelpV2API.class)
                    .apiKey("M8HBn3S_-bKLXLIRemDf9w")
                    .apiSecret("iynjTDafI6Tth7fPtiz56vJLd70")
                    .build();
            Token accessToken = new Token("z6jDMmgQEE60NQMWl70oVGd6x--Ypv5Y", "bgeFFbkd5GXttsHzYtIsehYUHFE");

            // We want to perform a search.
            OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.yelp.com/v2/search");
            // Based on a GPS coordinate latitude/longitude
            request.addQuerystringParameter("ll", "30.267153, -97.743061");
            // Looking for any restaurants
            request.addQuerystringParameter("category", "restaurants");
            service.signRequest(accessToken, request);
            Response response = request.send();
            String rawData = response.getBody();

            TextView tv = (TextView) rootView.findViewById(R.id.txtLabel);
            tv.setText(rawData);

            return rootView;
        }
    }
