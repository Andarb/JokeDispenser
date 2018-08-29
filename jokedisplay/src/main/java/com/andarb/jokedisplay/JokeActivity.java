package com.andarb.jokedisplay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.andarb.jokedisplay.R;

public class JokeActivity extends AppCompatActivity {

    public static final String EXTRA_JOKE = "com.andarb.JOKE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        String joke = getIntent().getStringExtra(EXTRA_JOKE);

        TextView jokeTV = findViewById(R.id.joke_text_view);
        jokeTV.setText(joke);
    }
}
