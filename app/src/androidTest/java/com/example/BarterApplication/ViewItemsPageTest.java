package com.example.BarterApplication;

import androidx.core.widget.ListViewAutoScrollHelper;
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

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ViewItemsPageTest {
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
    public void testViewItemButton_AT_16_01(){
        onView(withId(R.id.viewItemBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.viewItemBtn)).check(matches(isClickable()));
    }

    @Test
    public void testViewItemButton_AT_16_01_redirecting(){
        onView(withId(R.id.viewItemBtn)).perform(click());
        onView(withId(R.id.viewItemTitleText)).check(matches(withText(R.string.viewItemTitle)));
    }

    @Test
    public void testViewItemButton_AT_16_01_check_lists(){
        onView(withId(R.id.viewItemBtn)).perform(click());
        onView(withId(R.id.ViewItemsSearchBoxEditText)).check(matches(withHint("Search Name or Labels")));
        onView(withId(R.id.ViewItemsFilteredItemsListView)).check(isDisplayed());
        onData(anything()).inAdapterView(withId(R.id.ViewItemsFilteredItemsListView)).atPosition(0).onChildView(withId(R.id.MakeRequestBtn)).check(matches(isDisplayed()));
    }



    @After
    public void teardown()
    {
        FirebaseAuth.getInstance().signOut();
    }
}
