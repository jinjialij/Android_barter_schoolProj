package com.example.BarterApplication;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.BarterApplication.helpers.TestHelper;
import com.google.firebase.auth.FirebaseAuth;

import org.hamcrest.core.IsNot;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.Espresso.pressBack;

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
    }

    @Test
    public void testMyRequest_AT_08_03(){
        onView(withId(R.id.viewMyRequestBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.requestRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.itemRequestTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.requestID)).check(matches(isDisplayed()));
        pressBack();
    }

    @Test
    public void testMyRequest_AT_08_04(){
        onView(withId(R.id.viewMyRequestBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.requestRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.refuseRequestBtn)).perform(click());
        pressBack();
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.viewMyRequestBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.requestRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.refuseRequestBtn)).check(matches(IsNot.not(isEnabled())));
        pressBack();
    }

    @Test
    public void testMyRequest_AT_08_05(){
        onView(withId(R.id.viewMyRequestBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.requestRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.acceptRequestBtn)).perform(click());
        pressBack();
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.viewMyRequestBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.requestRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.acceptRequestBtn)).check(matches(IsNot.not(isEnabled())));
        pressBack();
    }

    @Test
    public void testMyRequest_AT_08_06(){
        onView(withId(R.id.viewMyRequestBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.requestRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.closeBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.closeBtn)).check(matches(isClickable()));
        onView(withId(R.id.closeBtn)).check(matches(isEnabled()));
        onView(withId(R.id.acceptRequestBtn)).perform(click());
        onView(withId(R.id.closeBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.viewMyRequestBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.requestRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.acceptRequestBtn)).check(matches(IsNot.not(isEnabled())));
        pressBack();
    }

    @Test
    public void testMyRequest_AT_08_07(){
        onView(withId(R.id.viewMyRequestBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.requestRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.acceptRequestBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.refuseRequestBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.acceptRequestBtn)).check(matches(isClickable()));
        onView(withId(R.id.refuseRequestBtn)).check(matches(isClickable()));
        pressBack();
    }

    @Test
    public void testMyRequest_AT_08_08(){
        onView(withId(R.id.viewMyRequestBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.requestRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.requestID)).check(matches(withText("38b1991f-36b0-4f9c-8f9b-2f02c9fbd1e1")));
        onView(withId(R.id.requestItemInfo)).check(matches(isDisplayed()));
        onView(withId(R.id.offeredItemInfo)).check(matches(IsNot.not(withText(""))));
        onView(withId(R.id.requestItemInfo)).check(matches(IsNot.not(withText(""))));
        pressBack();
    }

    @After
    public void teardown()
    {
        FirebaseAuth.getInstance().signOut();
    }
}
