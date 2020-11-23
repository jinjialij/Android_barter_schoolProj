package com.example.BarterApplication;

import androidx.test.espresso.action.ViewActions;
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

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsAnything.anything;

@RunWith(AndroidJUnit4.class)
public class ViewItemsPageTest {
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
    public void testViewItemButton_AT_16_01(){
        onView(withId(R.id.HomepageActivityGoToBarterButton)).check(matches(isDisplayed()));
        onView(withId(R.id.HomepageActivityGoToBarterButton)).check(matches(isClickable()));
    }

    @Test
    public void testViewItemButton_AT_16_01_redirecting(){
        onView(withId(R.id.HomepageActivityGoToBarterButton)).perform(click());
        onView(withId(R.id.viewItemTitleText)).check(matches(withText(R.string.viewItemTitle)));
    }

    @Test
    public void testViewItemButton_AT_16_01_check_lists(){
        onView(withId(R.id.HomepageActivityGoToBarterButton)).perform(click());
        onView(withId(R.id.ViewItemsSearchBoxEditText)).check(matches(withHint("Search Name or Labels")));
        onView(withId(R.id.ViewItemsFilteredItemsListView)).check(matches(isDisplayed()));
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onData(anything()).inAdapterView(withId(R.id.ViewItemsFilteredItemsListView)).atPosition(0).onChildView(withId(R.id.ViewItemsMakeRequestBtn)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.ViewItemsFilteredItemsListView)).atPosition(0).onChildView(withId(R.id.ViewItemsMakeRequestBtn)).check(matches(isClickable()));
    }

    @Test
    public void testViewItemButton_AT_16_02_check_disabled_request_button(){
        //sign in with an empty account that has no offering items
        onView(withId(R.id.buttonLogout))
                .perform(click());
        String email = "jialijin0@gmail.com";
        String pass = "123456";

        onView(withId(R.id.editTextTextEmailAddress))
                .perform(click())
                .perform(typeText(email), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword))
                .perform(click())
                .perform(typeText(pass), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.buttonLogin))
                .perform(click());

        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.HomepageActivityGoToBarterButton)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onData(anything()).inAdapterView(withId(R.id.ViewItemsFilteredItemsListView)).atPosition(0).onChildView(withId(R.id.ViewItemsMakeRequestBtn)).check(matches(IsNot.not(isEnabled())));
    }

    @Test
    public void testViewItemButton_AT_16_03_check_enabled_request_button(){
        onView(withId(R.id.HomepageActivityGoToBarterButton)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onData(anything()).inAdapterView(withId(R.id.ViewItemsFilteredItemsListView)).atPosition(0).onChildView(withId(R.id.ViewItemsMakeRequestBtn)).check(matches(isEnabled()));
    }

    @Test
    public void testViewItemButton_AT_16_04_request_button_redirecting(){
        onView(withId(R.id.HomepageActivityGoToBarterButton)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onData(anything()).inAdapterView(withId(R.id.ViewItemsFilteredItemsListView)).atPosition(0).onChildView(withId(R.id.ViewItemsMakeRequestBtn)).perform(click());
        onView(withId(R.id.BarterActivityActivityLabel)).check(matches(withText(R.string.BarterActivity_ActivityTitle)));
    }

    @After
    public void teardown()
    {
        FirebaseAuth.getInstance().signOut();
    }
}
