package com.example.BarterApplication;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.BarterApplication.helpers.TestHelper;
import com.example.BarterApplication.helpers.ToastMatcher;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Assert;
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
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsAnything.anything;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.instanceOf;

@RunWith(AndroidJUnit4.class)
public class CreateRequestTest {
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
        onView(withId(R.id.HomepageActivityGoToBarterButton)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onData(anything()).inAdapterView(withId(R.id.ViewItemsFilteredItemsListView)).atPosition(0).onChildView(withId(R.id.ViewItemsMakeRequestBtn)).perform(click());
        onView(withId(R.id.BarterActivitySendRequestButton)).perform(click());
    }

    @Test
    public void testCreateRequest_AT_16_05_display_requestedItem(){
        onView(withId(R.id.CreateNewRequestRequestedItemTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.CreateNewRequestRequestedItemInfo)).check(matches(isDisplayed()));
        onView(withId(R.id.CreateNewRequestRequestedItemInfo)).check(matches(withText(containsString("Name"))));
    }

    @Test
    public void testCreateRequest_AT_16_05_07_display_offeringItemSection_and_buttons(){
        onView(withId(R.id.CreateNewRequestOfferingItemSectionTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.CreateNewRequestAddedItemsList)).check(matches(isDisplayed()));
        onView(withId(R.id.CreateNewRequestOfferingItemSpinner)).check(matches(isDisplayed()));
        onView(withId(R.id.CreateNewRequestOfferingItemAddBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.CreateNewRequestOfferingItemSubmitBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.CreateNewRequestOfferingItemCancelBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.CreateNewRequestOfferingItemAddBtn)).check(matches(isClickable()));
        onView(withId(R.id.CreateNewRequestOfferingItemSubmitBtn)).check(matches(isClickable()));
        onView(withId(R.id.CreateNewRequestOfferingItemCancelBtn)).check(matches(isClickable()));
    }

    @Test
    public void testCreateRequest_AT_16_06_cancel(){
        onView(withId(R.id.CreateNewRequestOfferingItemCancelBtn)).perform(click());
        onView(withId(R.id.viewItemTitleText)).check(matches(withText(R.string.viewItemTitle)));
    }

    @Test
    public void testCreateRequest_AT_16_08_09_add_offeringItem(){
        onView(withId(R.id.CreateNewRequestOfferingItemSpinner)).perform(click());
        onData(anyOf(is(instanceOf(String.class)))).perform(click());
        onView(withId(R.id.CreateNewRequestOfferingItemAddBtn)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.CreateNewRequestAddedItemsList)).atPosition(0).onChildView(withId(R.id.ViewItemsInfoTextView)).check(matches(withText(containsString("Name"))));
    }

    @Test
    public void testCreateRequest_AT_16_10_delete_added_offeringItem(){
        onView(withId(R.id.CreateNewRequestOfferingItemSpinner)).perform(click());
        onData(anyOf(is(instanceOf(String.class)))).perform(click());
        onView(withId(R.id.CreateNewRequestOfferingItemAddBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(1000));
        onData(anything()).inAdapterView(withId(R.id.CreateNewRequestAddedItemsList)).atPosition(0).onChildView(withId(R.id.ViewItemsMakeRequestBtn)).perform(click());
        try {
            onData(anything()).inAdapterView(withId(R.id.CreateNewRequestAddedItemsList)).atPosition(0);
        } catch (Exception e){
            Assert.assertNotNull(e);
        }
    }

    @Test
    public void testCreateRequest_AT_16_11_submit_without_offeringItem(){
        onView(withId(R.id.CreateNewRequestOfferingItemSubmitBtn)).perform(click());
        onView(withText(R.string.NewRequestSubmissionError)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void testCreateRequest_AT_16_11_submit_success(){
        onView(withId(R.id.CreateNewRequestOfferingItemSpinner)).perform(click());
        onData(anyOf(is(instanceOf(String.class)))).perform(click());
        onView(withId(R.id.CreateNewRequestOfferingItemAddBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(1000));
        onView(withId(R.id.CreateNewRequestOfferingItemSubmitBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(3000));
        onView(withText(R.string.NewRequestSubmissionSuccessMessage)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
        onView(withId(R.id.homepageActivityLabelTextView)).check(matches(withText(R.string.HomePageTitle)));
    }

    @After
    public void teardown()
    {
        FirebaseAuth.getInstance().signOut();
    }
}
