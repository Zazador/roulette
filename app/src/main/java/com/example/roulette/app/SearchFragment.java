package com.example.roulette.app;

import android.app.Fragment;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.v2.Business;
import com.google.gson.Gson;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import java.lang.ref.WeakReference;


/**
 * Created by zach on 6/11/14.
 */
public class SearchFragment extends Fragment {
    public SearchFragment() {
    }

    private WeakReference<MyAsyncTask> asyncTaskWeakRef;
    public EditText foodTypeText;
    EditText foodLocationText;
    Button searchButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        foodTypeText = (EditText) rootView.findViewById(R.id.foodtype);
        foodLocationText = (EditText) rootView.findViewById(R.id.foodlocation);
        searchButton = (Button) rootView.findViewById(R.id.search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewAsyncTask();
            }
        });
        return rootView;
    }

    private void startNewAsyncTask() {
        MyAsyncTask task = new MyAsyncTask(this);
        this.asyncTaskWeakRef = new WeakReference<MyAsyncTask>(task);
        task.execute();
    }

    private class MyAsyncTask extends AsyncTask<String, Void, String> {
        private WeakReference<SearchFragment> fragmentWeakReference;
        private Exception exception;

        private MyAsyncTask(SearchFragment searchFrag) {
            this.fragmentWeakReference = new WeakReference<SearchFragment>(searchFrag);
        }

        @Override
        protected String doInBackground(String... terms) {
            try {
                LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
                String mlocProvider;
                Criteria hdCrit = new Criteria();
                hdCrit.setAccuracy(Criteria.ACCURACY_COARSE);
                mlocProvider = locationManager.getBestProvider(hdCrit, true);
                Location currentLoc = locationManager.getLastKnownLocation(mlocProvider);
                double currentLat = currentLoc.getLatitude();
                double currentLong = currentLoc.getLongitude();
                System.out.println(String.valueOf(currentLat) + ", " + String.valueOf(currentLong));

                String foodType = foodTypeText.getText().toString();
                String loc = foodLocationText.getText().toString();
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
                request.addQuerystringParameter("ll", String.valueOf(currentLat) + ", " + String.valueOf(currentLong));
                // Looking for any restaurants
                request.addQuerystringParameter("term", foodType);
                service.signRequest(accessToken, request);
                Response response = request.send();
                String rawData = response.getBody();
                System.out.println(rawData);
                return rawData;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(String rawData) {
            super.onPostExecute(rawData);
            if (this.fragmentWeakReference.get() != null) {
                try {
                    YelpSearchResult places = new Gson().fromJson(rawData, YelpSearchResult.class);
                    System.out.println("Your search found " + places.getTotal() + " results.");
                    System.out.println("Yelp returned " + places.getBusinesses().size() + " businesses in this request.");
                    System.out.println();

                    for (Business biz : places.getBusinesses()) {
                        System.out.println(biz.getName());
                        for (String address : biz.getLocation().getAddress()) {
                            System.out.println(" " + address);
                        }
                        System.out.println(" " + biz.getLocation().getCity());
                        System.out.println(biz.getUrl());
                        System.out.println();
                    }
                } catch (Exception e) {
                    System.out.println("Error, could not parse returned data!");
                }
            }
        }
    }
}
