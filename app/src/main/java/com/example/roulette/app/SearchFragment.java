package com.example.roulette.app;

import android.app.Fragment;
import android.os.AsyncTask;
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

import java.lang.ref.WeakReference;

/**
 * Created by zach on 6/11/14.
 */
public class SearchFragment extends Fragment {
    public SearchFragment() {
    }

    private WeakReference<MyAsyncTask> asyncTaskWeakRef;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        startNewAsyncTask();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        return rootView;
    }

    private void startNewAsyncTask() {
        MyAsyncTask task = new MyAsyncTask(this);
        this.asyncTaskWeakRef = new WeakReference<MyAsyncTask>(task);
        task.execute();
    }

    private static class MyAsyncTask extends AsyncTask<String, Void, Void> {
        private WeakReference<SearchFragment> fragmentWeakReference;
        private Exception exception;

        private MyAsyncTask(SearchFragment searchFrag) {
            this.fragmentWeakReference = new WeakReference<SearchFragment>(searchFrag);
        }

        @Override
        protected Void doInBackground(String... terms) {
            try {
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
                System.out.println(rawData);
                return null;
            } catch (Exception e) {
                this.exception = e;
                return null;
            }
        }

        @Override
        protected void onPostExecute(Void response) {
            super.onPostExecute(response);
            if (this.fragmentWeakReference.get() != null) {
                //TODO: treat the result
            }
        }
    }
}
