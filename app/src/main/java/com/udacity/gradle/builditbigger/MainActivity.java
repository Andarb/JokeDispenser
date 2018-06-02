package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.andarb.jokedisplay.JokeActivity;
import com.github.andarb.jokesource.JokeSource;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load a banner with adverts
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
    }

    public void tellAJoke(View view) {
        // Retrieve a joke from our Java library
        JokeSource jokeSource = new JokeSource();
        String joke = jokeSource.retrieveJoke();

        // Pass on the joke to our Android library
        Intent intent = new Intent(this, JokeActivity.class);
        intent.putExtra(JokeActivity.EXTRA_JOKE, joke);
        startActivity(intent);
    }
}
