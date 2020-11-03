package com.example.BarterApplication;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;

public class DrodownTest {
    @Test
    public void Check_choosing_options(){


        //selecting the item
        onView(withId(R.id.itemList)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("chair"))).perform(click());
        onView(withId(R.id.filter)).check(matches(withText("chair")));


    }
    @Test
    public void Filtering_button() {
        //filtering items with user input text

        onView(withId(R.id.Search)).perform(click());

         // when clicked , it is going to start filtering

    }







}
