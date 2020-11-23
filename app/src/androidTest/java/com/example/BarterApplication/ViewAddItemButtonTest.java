package com.example.BarterApplication;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;

import com.example.BarterApplication.helpers.TestHelper;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

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


public class ViewAddItemButtonTest {
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
    public void testViewMyAddButton_AT_06_01(){//check add button
        onView(withId(R.id.viewAddItemBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.viewAddItemBtn)).check(matches(isClickable()));
    }

    @Test
    public void testViewMyAddButton_AT_06_02(){//check item title and description
        onView(withId(R.id.viewAddItemBtn)).perform(click());
        onView(withId(R.id.AddItemDescriptionEditText)).check(matches(withHint("Description (optional)")));
        onView(withId(R.id.AddItemNameEditText)).check(matches(withHint("Name (required)")));
        onView(withId(R.id.AddItemSubmitButton)).check(matches(isDisplayed()));
        onView(withId(R.id.AddItemSubmitButton)).check(matches(isClickable()));
    }

    @Test
    public void testViewMyAddButton_AT_06_04(){//check confirm add successful
        String description = "test";
        String Name = "TestItem";
        String label = "testLabel";

        onView(withId(R.id.viewAddItemBtn)).perform(click());
        onView(withId(R.id.AddItemDescriptionEditText))
                .perform(clearText());
        onView(withId(R.id.AddItemDescriptionEditText))
                .perform(click())
                .perform(typeText(description), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.AddItemNameEditText))
                .perform(clearText());
        onView(withId(R.id.AddItemNameEditText))
                .perform(click())
                .perform(typeText(Name), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.AddItemSubmitButton))
                .perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.homepageActivityLabelTextView)).check(matches(withText("Homepage")));
    }

    @Test
    public void testViewMyAddButton_AT_06_04_with_label(){
        String description = "test";
        String Name = "TestItem";
        String label = "testLabel";

        onView(withId(R.id.viewAddItemBtn)).perform(click());
        onView(withId(R.id.AddItemDescriptionEditText))
                .perform(clearText());
        onView(withId(R.id.AddItemDescriptionEditText))
                .perform(click())
                .perform(typeText(description), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.AddItemNameEditText))
                .perform(clearText());
        onView(withId(R.id.AddItemNameEditText))
                .perform(click())
                .perform(typeText(Name), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.AddItemLabelsEditText))
                .perform(clearText());
        onView(withId(R.id.AddItemLabelsEditText))
                .perform(click())
                .perform(typeText(label), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.AddItemSubmitButton))
                .perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.homepageActivityLabelTextView)).check(matches(withText("Homepage")));
    }

    @Test
    public void testViewMyAddButton_AT_06_05(){//check confirm add failed
        String description = "Test description";
        String Title = "@@@";

        onView(withId(R.id.viewAddItemBtn)).perform(click());
        onView(withId(R.id.AddItemDescriptionEditText))
                .perform(click())
                .perform(typeText(description), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.AddItemNameEditText))
                .perform(click())
                .perform(typeText(Title), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.AddItemSubmitButton))
                .perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(3000));
        onView(withId(R.id.textView4)).check(matches(withText(R.string.addItem)));
    }

    @Test
    public void testViewMyAddButton_AT_06_06(){//redirect to homepage
        String description = "test";
        String Title = "TestItem";

        onView(withId(R.id.viewAddItemBtn)).perform(click());
        onView(withId(R.id.AddItemDescriptionEditText))
                .perform(click())
                .perform(typeText(description), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.AddItemNameEditText))
                .perform(click())
                .perform(typeText(Title), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.AddItemSubmitButton))
                .perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.homepageActivityLabelTextView)).check(matches(withText("Homepage")));
    }

    @After
    public void teardown()
    {
        FirebaseAuth.getInstance().signOut();
    }

}
