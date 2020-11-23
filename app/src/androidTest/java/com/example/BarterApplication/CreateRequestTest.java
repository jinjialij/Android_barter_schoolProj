package com.example.BarterApplication;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.BarterApplication.helpers.ItemService;
import com.example.BarterApplication.helpers.TestHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
import static androidx.test.espresso.matcher.ViewMatchers.hasContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.core.IsAnything.anything;

@RunWith(AndroidJUnit4.class)
public class CreateRequestTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);
    private FirebaseAuth mAuth;
    private static FirebaseUser currentUser;
    private static String requestedItemOnViewItemsPage;
    private static DataInteraction requestedItemData;

    @Before
    public void setup()
    {
        FirebaseAuth.getInstance().signOut();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String email = "jl548339@dal.ca";
        String pass = "123456";
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        onView(withId(R.id.editTextTextEmailAddress))
                .perform(click())
                .perform(typeText(email), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword))
                .perform(click())
                .perform(typeText(pass), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.buttonLogin))
                .perform(click());

        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.viewItemBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        requestedItemData = onData(anything()).inAdapterView(withId(R.id.ViewItemsFilteredItemsListView)).atPosition(0).onChildView(withId(R.id.ViewItemsInfoTextView));
        onData(anything()).inAdapterView(withId(R.id.ViewItemsFilteredItemsListView)).atPosition(0).onChildView(withId(R.id.ViewItemsMakeRequestBtn)).perform(click());
    }

    @Test
    public void testCreateRequest_AT_16_02_display_requestedItem(){
        onView(withId(R.id.CreateNewRequestRequestedItemTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.CreateNewRequestRequestedItemInfo)).check(matches(isDisplayed()));
        onView(withId(R.id.CreateNewRequestRequestedItemInfo)).check(matches(withText(containsString("Name"))));
    }

    @After
    public void teardown()
    {
        FirebaseAuth.getInstance().signOut();
    }
}
