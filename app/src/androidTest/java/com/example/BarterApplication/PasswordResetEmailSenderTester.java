package com.example.BarterApplication;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
@LargeTest

public class PasswordResetEmailSenderTester {

    @Rule
    public ActivityScenarioRule<PasswordResetActivity> activityRule =
            new ActivityScenarioRule<>(PasswordResetActivity.class);

    @Before
    public void setup()
    {

    }


    @After
    public void teardown()
    {

    }

    @Test
    public void AT_1() /* Layout test */
    {
        onView(withId(R.id.passwordResetSendEmailButtonId)).check(matches(isDisplayed()));
        onView(withId(R.id.passwordResetEmailTextBoxId)).check(matches(isDisplayed()));
        onView(withId(R.id.passwordResetEmailStatusMessageTextViewId)).check(matches(isDisplayed()));
    }

    @Test
    public void AT_2() /* Test the password reset email status message */
    {
        onView(withId(R.id.passwordResetEmailTextBoxId)).perform(typeText("cmattatall2@gmail.com"));
        hideKeyboard();
        onView(withId(R.id.passwordResetSendEmailButtonId)).perform(click());
        onView(withId(R.id.passwordResetEmailStatusMessageTextViewId)).check(matches(withText(R.string.passwordResetEmailSent)));
    }

    @Test
    public void AT_3()
    {
        onView(withId(R.id.passwordResetEmailTextBoxId)).perform(typeText("cmattatall2@gmail.com"));
        hideKeyboard();
        onView(withId(R.id.passwordResetSendEmailButtonId)).perform(click());
        onView(withId(R.id.passwordResetEmailStatusMessageTextViewId)).check(matches(withText(R.string.passwordResetEmailSent)));
        onView(withId(R.id.buttonLogin)).check(matches(isDisplayed())); /* this is the same as AT_2 but we confirm that we've returned to login page */
    }

    private void hideKeyboard() /* on small screen sizes, the keyboard can cover the editTexts */
    {
        onView(isRoot()).perform(ViewActions.closeSoftKeyboard());
    }


}
