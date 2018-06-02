package com.udacity.gradle.builditbigger;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;


/**
 * Test clicking on the first recipe downloaded from JSON.
 * Clicking on it should open a list of steps and ingredients for that recipe.
 */
@RunWith(AndroidJUnit4.class)
public class RetrieveJoke {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    private CountingIdlingResource mIdlingResource;


    // Register the idling resource before starting the test
    @Before
    public void registerIdlingResource() {
        // Retrieve the initialized idling resource from MainActivity
        mIdlingResource = mActivityTestRule.getActivity().getIdlingResource();

        Espresso.registerIdlingResources(mIdlingResource);
    }

    @Test
    public void clickJokeButton_GetJoke() {
        onView(withId(R.id.get_joke_button))
                .perform(click());

        onView(withId(R.id.joke_text_view))
                .check(matches(not(withText(R.string.error_missing_joke))));
    }

    // Unregister the idling resource after the test
    @After
    public void unregisterIdlingResource() {
        Espresso.unregisterIdlingResources(mIdlingResource);
    }
}