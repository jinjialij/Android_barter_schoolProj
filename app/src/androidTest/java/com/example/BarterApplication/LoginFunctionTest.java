package com.example.BarterApplication;

import android.support.test.espresso.action.ViewActions;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class LoginFunctionTest {
    @Rule
//    public Activity<MainActivity> activityScenarioRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);
    static FirebaseAuth mAuth;

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
        onView(withId(R.id.textView2))
                .check(matches(withText("Homepage")));
    }

    @AfterClass
    public static void tearDown() { FirebaseAuth.getInstance().signOut(); }
}
