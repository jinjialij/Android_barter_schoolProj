package com.example.BarterApplication;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.BarterApplication.helpers.ItemRequestService;
import com.example.BarterApplication.helpers.ItemService;
import com.example.BarterApplication.helpers.TestHelper;
import com.example.BarterApplication.helpers.UidService;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class DeleteMatchTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);
    private FirebaseAuth mAuth;
    private ItemRequest request;

    @Before
    public void setup()
    {
        FirebaseAuth.getInstance().signOut();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String email = "yc270707@dal.ca";
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
    public void testViewMyAddButton_AT_12_01(){//check delete button
        onView(withId(R.id.viewMyRequestBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.requestRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.deleteMatchButton)).check(matches(isDisplayed()));
        onView(withId(R.id.deleteMatchButton)).check(matches(isClickable()));
    }

    @Test
    public void testViewMyAddButton_AT_12_02(){//check if the message will be shown and redirect to request view page.
        onView(withId(R.id.viewMyRequestBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.requestRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.deleteMatchButton)).check(matches(isDisplayed()));
        onView(withId(R.id.deleteMatchButton)).check(matches(isClickable()));
        onView(withId(R.id.deleteMatchButton)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withText("The match has been deleted")).check(matches(isDisplayed()));
        onView(withId(R.id.requestRecyclerView)).check((matches(isDisplayed())));
    }

    @After
    public void teardown()
    {
        FirebaseAuth.getInstance().signOut();
    }
}
