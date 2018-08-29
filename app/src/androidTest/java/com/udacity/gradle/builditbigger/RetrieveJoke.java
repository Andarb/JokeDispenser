package com.udacity.gradle.builditbigger;

import android.content.res.Resources;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.andarb.jokedisplay.JokeActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsNot.not;


/**
 * Clicking on a `Make me smile` button should retrieve a joke from GCE.
 * A predefined error message is sent instead of the joke if there is a problem.
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
        String jokeMissing = resources.getString(R.string.error_missing_joke);

        onView(withId(R.id.get_joke_button))
                .perform(click());

        intended(allOf(
                hasComponent(JokeActivity.class.getName()),
                not(hasExtra(JokeActivity.EXTRA_JOKE, jokeMissing))));
    }

    // Unregister the idling resource after the test
    @After
    public void unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(mIdlingResource);
    }
}