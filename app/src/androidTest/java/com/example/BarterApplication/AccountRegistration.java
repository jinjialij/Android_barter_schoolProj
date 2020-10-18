package com.example.BarterApplication;

import android.view.View;
import com.google.firebase.auth.FirebaseAuth;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.runner.AndroidJUnit4;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class AccountRegistration {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    FirebaseAuth mAuth;
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

    @Before
    public void setup()
    {
        onView(withId(R.id.regBtn)).perform(click());
    }

    @Test
    public void AT_01(){
        onView(withId(R.id.title)).check(matches(isDisplayed()));
        onView(withId(R.id.emailIcon)).check(matches(isDisplayed()));
        onView(withId(R.id.passwordIcon)).check(matches(isDisplayed()));
        onView(withId(R.id.registerEmailInitialTextBoxId)).check(matches(isDisplayed()));
        onView(withId(R.id.registerEmailConfirmTextBoxId)).check(matches(isDisplayed()));
        onView(withId(R.id.registerPasswordConfirmTextBoxId)).check(matches(isDisplayed()));
        onView(withId(R.id.registerPasswordInitialTextBoxId)).check(matches(isDisplayed()));
        onView(withId(R.id.registerBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.backButton)).check(matches(isDisplayed()));
    }

    @Test
    public void AT_02(){
        String invalidEmail = "a";
        String password = "1234";
        onView(withId(R.id.registerEmailInitialTextBoxId)).perform(typeText(invalidEmail));
        onView(withId(R.id.registerEmailConfirmTextBoxId)).perform(typeText(invalidEmail));

        onView(withId(R.id.registerPasswordInitialTextBoxId)).perform(typeText(password));
        onView(withId(R.id.registerPasswordConfirmTextBoxId)).perform(typeText(password));

        onView(withId(R.id.registerBtn)).perform(click());
        onView(withText(R.string.registerInvalidEmailError)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void AT_03(){

        // mismatch email
        onView(withId(R.id.registerEmailInitialTextBoxId)).perform(typeText("adam@mat.com"));
        onView(withId(R.id.registerEmailConfirmTextBoxId)).perform(typeText("adam@mattatall.com"));

        onView(withId(R.id.registerPasswordInitialTextBoxId)).perform(typeText("password1234"));
        onView(withId(R.id.registerPasswordConfirmTextBoxId)).perform(typeText("password1234"));

        onView(withId(R.id.registerBtn)).perform(click());
        onView(withText(R.string.registerFieldsMismatchError)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));

        // clear text
        onView(withId(R.id.registerEmailInitialTextBoxId)).perform(clearText());
        onView(withId(R.id.registerEmailConfirmTextBoxId)).perform(clearText());
        onView(withId(R.id.registerPasswordInitialTextBoxId)).perform(clearText());
        onView(withId(R.id.registerPasswordConfirmTextBoxId)).perform(clearText());

        // mismatch password
        onView(withId(R.id.registerEmailInitialTextBoxId)).perform(typeText("adam@mattatall.com"));
        onView(withId(R.id.registerEmailConfirmTextBoxId)).perform(typeText("adam@mattatall.com"));

        onView(withId(R.id.registerPasswordInitialTextBoxId)).perform(typeText("password1234"));
        onView(withId(R.id.registerPasswordConfirmTextBoxId)).perform(typeText("password12345"));

        onView(withId(R.id.registerBtn)).perform(click());
        onView(withText(R.string.registerFieldsMismatchError)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void AT_04(){
        onView(withId(R.id.registerEmailInitialTextBoxId)).perform(typeText("adam@mattatall.com"));
        onView(withId(R.id.registerEmailConfirmTextBoxId)).perform(typeText("adam@mattatall.com"));

        onView(withId(R.id.registerPasswordInitialTextBoxId)).perform(typeText("1"));
        onView(withId(R.id.registerPasswordConfirmTextBoxId)).perform(typeText("1"));

        onView(withId(R.id.registerBtn)).perform(click());
        onView(withText(R.string.registerPasswordLengthError)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));

    }

    @Test
    public void AT_05(){
        // random email
        String email = UUID.randomUUID().toString().replace("-","") + "@dal.ca";

        onView(withId(R.id.registerEmailInitialTextBoxId)).perform(typeText(email));
        onView(withId(R.id.registerEmailConfirmTextBoxId)).perform(typeText(email));

        onView(withId(R.id.registerPasswordInitialTextBoxId)).perform(typeText("123456"));
        onView(withId(R.id.registerPasswordConfirmTextBoxId)).perform(typeText("123456"));

        onView(withId(R.id.registerBtn)).perform(click());
        onView(withText(R.string.registerSuccessMessage)).inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));

        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.loginLogo)).check(matches(isDisplayed()));
    }

    @After
    public void teardown()
    {
    }
}
