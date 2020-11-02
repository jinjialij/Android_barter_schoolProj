package com.example.BarterApplication;

import android.Manifest;
import android.view.View;

import com.example.BarterApplication.helpers.LocationHelper;
import com.example.BarterApplication.helpers.TestHelper;
import com.example.BarterApplication.helpers.ToastMatcher;
import com.google.firebase.auth.FirebaseAuth;

import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.internal.platform.content.PermissionGranter;
import androidx.test.rule.GrantPermissionRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class Location {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);
    @Rule
    public GrantPermissionRule mRuntimePermissionRule
            = GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION);


    public ViewAction waitFor(final long delay) {
        return new ViewAction() {
            @Override public Matcher<View> getConstraints() {
                return ViewMatchers.isRoot();
            }

            @Override public String getDescription() {
                return "wait for " + delay + "milliseconds";
            }

            @Override public void perform(UiController uiController, View view) {
                uiController.loopMainThreadForAtLeast(delay);
            }
        };
    }

    /*
        We cant unaccept an accepted permission, so we can only check that the permission request
        doesn't appear
     */
    @Test
    public void AT_4_01(){
        onView(withText(R.string.locationPermissionUnallowed)).check(doesNotExist());
        onView(withText(R.string.locationPermissionAllowed)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void AT_4_02() {
        // check for longitude/latitude values
        onView(isRoot()).perform(TestHelper.waitFor(2500));
        Assert.assertNotNull(LocationHelper.location);
    }
}
