package com.example.BarterApplication;

import android.widget.TextView;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.runner.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MyRequestPageTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);
    private FirebaseAuth mAuth;

    @Before
    public void setup()
    {
        FirebaseAuth.getInstance().signOut();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String email = "jl548339@dal.ca";
        String pass = "123456";
        mAuth = FirebaseAuth.getInstance();

        onView(withId(R.id.editTextTextEmailAddress))
                .perform(click())
                .perform(typeText(email), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword))
                .perform(click())
                .perform(typeText(pass), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.buttonLogin))
                .perform(click());

        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.viewMyRequestBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.requestRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void testMyRequest_AT_08_03(){
        onView(withId(R.id.itemRequestTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.reqestID)).check(matches(isDisplayed()));
    }

    @Test
    public void testMyRequest_AT_08_08(){
        onView(withId(R.id.reqestID)).check(matches(withText("38b1991f-36b0-4f9c-8f9b-2f02c9fbd1e1")));
        onView(withId(R.id.requestItemInfo)).check(matches(isDisplayed()));
        onView(withId(R.id.offeredItemInfo)).check(matches(isDisplayed()));
    }

    @Test
    public void testMyRequest_AT_08_07(){
        onView(withId(R.id.acceptBtn)).check(isDisplayed());
        onView(withId(R.id.refuseBtn)).check(isDisplayed());
        onView(withId(R.id.acceptBtn)).check(isClickable());
        onView(withId(R.id.refuseBtn)).check(isClickable());
    }

    @After
    public void teardown()
    {
        FirebaseAuth.getInstance().signOut();
    }
}
