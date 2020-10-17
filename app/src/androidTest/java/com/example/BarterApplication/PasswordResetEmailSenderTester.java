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
    public void AT_1()
    {
        onView(withId(R.id.passwordResetSendEmailButtonId)).check(matches(isDisplayed()));
        onView(withId(R.id.passwordResetEmailTextBoxId)).check(matches(isDisplayed()));
        onView(withId(R.id.passwordResetEmailStatusMessageTextViewId)).check(matches(isDisplayed()));
    }

    @Test
    public void AT_2()
    {
        typeEmail("cmattatall2@gmail.com");
        onView(withId(R.id.passwordResetSendEmailButtonId)).perform(click());
        onView(withId(R.id.passwordResetEmailStatusMessageTextViewId)).check(matches(withText(R.string.passwordResetEmailSent)));
    }

    @Test
    public void AT_3()
    {
        onView(withId(R.id.backButton)).perform(click());
        onView(withId(R.id.buttonLogin)).check(matches(isDisplayed()));
    }

    @Test
    public void AT_4()
    {
        typeEmail("");
        onView(withId(R.id.passwordResetSendEmailButtonId)).perform(click());
        onView(withId(R.id.passwordResetEmailStatusMessageTextViewId)).check(matches(withText(R.string.passwordResetEmptyEmailError)));
    }

    private void typeEmail(String s)
    {
        onView(withId(R.id.passwordResetEmailTextBoxId)).perform(typeText(s));
        onView(isRoot()).perform(ViewActions.closeSoftKeyboard());
    }
}
