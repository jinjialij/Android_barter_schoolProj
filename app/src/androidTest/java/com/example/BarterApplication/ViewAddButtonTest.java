package com.example.BarterApplication;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.BarterApplication.helpers.ValidationHelper;
import com.google.firebase.auth.FirebaseAuth;

import org.hamcrest.Matcher;
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
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


public class ViewAddButtonTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);
    FirebaseAuth mAuth;

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
    public void testViewMyRequestButton_AT_06_01(){
        onView(withId(R.id.viewMyAddBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.viewMyAddBtn)).check(matches(isClickable()));
    }

    @Test
    public void testViewMyRequestButton_AT_06_02(){
        onView(withId(R.id.viewMyAddBtn)).perform(click());
        onView(withId(R.id.viewMyItemsDescription)).check(matches(withText("Description")));
        onView(withId(R.id.viewMyItemsTitle)).check(matches(withText("Title")));
        onView(withId(R.id.viewConfirmAddItem)).check(matches(isDisplayed()));
        onView(withId(R.id.viewConfirmAddItem)).check(matches(isClickable()));
    }

    @Test
    public void testViewMyRequestButton_AT_06_03(){
        String description = "This is my test item";
        String Title = "Test Item";

        onView(withId(R.id.viewMyAddBtn)).perform(click());
        onView(withId(R.id.viewMyItemsDescription))
                .perform(click())
                .perform(typeText(description), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword))
                .perform(click())
                .perform(typeText(Title), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.viewConfirmAddItem))
                .perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withText(R.string.addItemSuccess)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @After
    public void teardown()
    {
        FirebaseAuth.getInstance().signOut();
    }

}
