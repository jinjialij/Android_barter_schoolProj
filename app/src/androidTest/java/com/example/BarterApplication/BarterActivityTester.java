package com.example.BarterApplication;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.BarterApplication.helpers.TestHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class BarterActivityTester {
    private final String testerEmail = "cmattatall2@gmail.com";
    private final String testerPassword = "test123";

    @Rule
    public ActivityScenarioRule<BarterActivity> activityRule =
            new ActivityScenarioRule<>(BarterActivity.class);

    @Before
    public void setup() {
        /** @todo WE NEED TO LOGIN TO THE APPLICATION THE VERY FIRST TIME */
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser u = auth.getCurrentUser();
        if(u == null){
            auth.createUserWithEmailAndPassword(testerEmail, testerPassword);
            auth.signInWithEmailAndPassword(testerEmail, testerPassword);
        }
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
    public void itemNameDisplayCheck(){
        onView(withId(R.id.BarterActivityCurrentItemNameTextView)).check(matches(isDisplayed()));
    }

    @Test
    public void itemDescriptionDisplayCheck(){
        onView(withId(R.id.BarterActivityCurrentItemDescriptionTextView)).check(matches(isDisplayed()));
    }

    @Test
    public void searchRadiusEditTextDisplayCheck(){
        onView(withId(R.id.BarterActivityItemSearchRadiusEditText)).check(matches(isDisplayed()));
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

    @Test
    public void goBackOnClickCheck(){
        onView(withId(R.id.BarterActivityGoToHomepageButton)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(3000));
        /** @todo REFACTOR / Fix
         * I HAVE USED CHECKING FOR THE HOMEPAGE TITLE AS A PROXY FOR CHECKING
         * IF THE CURRENT ACTIVITY IS THE HOMEPAGE. I CANT GET THE COMMENTED LINE
         * OF CODE TO WORK AS INTENDED (carl) */
        //onView(withId(R.layout.activity_homepage)).check(matches(isDisplayed()));
        onView(withId(R.id.homepageActivityLabelTextView)).check(matches(isDisplayed()));
    }

    @Test
    public void itemImageDisplayCheck(){
        onView(withId(R.id.BarterActivityCurrentItemImageView)).check(matches(isDisplayed()));
    }

}
