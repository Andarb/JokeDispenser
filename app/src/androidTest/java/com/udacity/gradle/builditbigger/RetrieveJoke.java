package com.udacity.gradle.builditbigger;

import android.content.res.Resources;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.github.andarb.jokedisplay.JokeActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


/**
 * Clicking on a `Tell A Joke` button should retrieve a joke from GCE.
 */
@RunWith(AndroidJUnit4.class)
public class RetrieveJoke {

    @Rule
    public IntentsTestRule<MainActivity> mActivityTestRule =
            new IntentsTestRule<>(MainActivity.class);

    private CountingIdlingResource mIdlingResource;


    // Register the idling resource before starting the test
    @Before
    public void registerIdlingResource() {
        // Retrieve the initialized idling resource from MainActivity
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();

        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @Test
    public void clickJokeButton_GetJoke() {
        Resources resources = mActivityTestRule.getActivity().getResources();
        String jokeError = resources.getString(R.string.error_missing_joke);

        onView(withId(R.id.get_joke_button))
                .perform(click());

        intended(hasExtra(JokeActivity.EXTRA_JOKE, jokeError));
    }

    // Unregister the idling resource after the test
    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(mIdlingResource);
    }
}