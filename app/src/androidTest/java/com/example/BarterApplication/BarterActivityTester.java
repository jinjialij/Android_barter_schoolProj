package com.example.BarterApplication;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BarterActivityTester {

    @Rule
    public ActivityScenarioRule<BarterActivity> activityRule =
            new ActivityScenarioRule<>(BarterActivity.class);

    @Before
    public void setup() {

    }


    @After
    public void teardown() {

    }

    @Test
    public void backButtonDisplayCheck(){
        onView(withId(R.id.BarterActivityGoToHomepageButton)).check(matches(isDisplayed()));
    }

    @Test
    public void sendRequestButtonDisplayCheck(){
        onView(withId(R.id.BarterActivitySendRequestButton)).check(matches(isDisplayed()));
    }

    @Test
    public void viewNextItemButtonDisplayCheck(){
        onView(withId(R.id.BarterActivityViewNextItemButton)).check(matches(isDisplayed()));
    }

    @Test
    public void activityTitleDisplayCheck(){
        onView(withId(R.id.BarterActivityActivityLabel)).check(matches(isDisplayed()));
    }

    @Test
    public void backButtonLabelCheck(){
        onView(withId(R.id.BarterActivityGoToHomepageButton)).check(matches(withText(R.string.back)));
    }

    @Test
    public void sendRequestButtonLabelCheck(){
        onView(withId(R.id.BarterActivitySendRequestButton)).check(matches(withText(R.string.BarterActivity_SendRequestButtonLabel)));

    }

    @Test
    public void viewNextItemButtonLabelCheck(){
        onView(withId(R.id.BarterActivityViewNextItemButton)).check(matches(withText(R.string.BarterActivity_ViewNextItemButtonLabel)));
    }



}
