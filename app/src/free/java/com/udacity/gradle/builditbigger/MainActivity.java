package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.github.andarb.jokedisplay.JokeActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;

    private CountingIdlingResource mIdlingResource =
            new CountingIdlingResource("WaitForJoke");

    ProgressBar mLoadingPB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingPB = findViewById(R.id.joke_loading_pb);

        // Load an Interstitial ad
        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        // Load a banner ad
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
    }

    /* onClick method for the `Tell A Joke` button */
    public void tellAJoke(View view) {
        mLoadingPB.setVisibility(View.VISIBLE);

        // Pause Espresso testing during the network call
        mIdlingResource.increment();
        new EndpointsAsyncTask().execute(this);
    }

    /* Contacts local Endpoint to retrieve a joke, and passes it on to our Android library */
    class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {
        private MyApi myApiService = null;
        private Context context;

        @Override
        protected String doInBackground(Context... params) {
            if (myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                // end options for devappserver

                myApiService = builder.build();
            }

            context = params[0];

            try {
                return myApiService.getJoke().execute().getData();
            } catch (IOException e) {
                return getString(R.string.error_missing_joke);
            }
        }

        @Override
        protected void onPostExecute(String result) {
            mLoadingPB.setVisibility(View.GONE);

            // Pass the joke to the Android library
            Intent intent = new Intent(context, JokeActivity.class);
            intent.putExtra(JokeActivity.EXTRA_JOKE, result);
            startActivity(intent);

            // Resume Espresso testing
            mIdlingResource.decrement();

            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
        }
    }

    /* Retrieve Espresso idling resource for testing purposes */
    public CountingIdlingResource getIdlingResource() {
        return mIdlingResource;
    }
}
