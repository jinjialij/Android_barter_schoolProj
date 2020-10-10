package com.example.BarterApplication;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.BarterApplication.helpers.ValidationHelper;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.xml.validation.ValidatorHandler;

import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class LoginFunctionTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);
//    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);
    static FirebaseAuth mAuth;

    @Test
    public void testLogin_withInvalidEmail() {
        String email = "jl";
        assertFalse(ValidationHelper.isValidEmail(email));
    }

    @BeforeClass
    public static void setup(){
        mAuth = FirebaseAuth.getInstance();
    }

    @Test
    public void testLogin_withAccount(){
        String email = "jl548339@dal.ca";
        String pass = "123456";
        onView(withId(R.id.editTextTextEmailAddress))
                .perform(click())
                .perform(typeText(email), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword))
                .perform(click())
                .perform(typeText(email), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.buttonLogin))
                .perform(click());
        mAuth.signInWithEmailAndPassword(email, pass);
        assertNotEquals(mAuth.getCurrentUser(), null);
    }

    @AfterClass
    public static void tearDown() { FirebaseAuth.getInstance().signOut(); }
}
