package com.example.BarterApplication;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.BarterApplication.helpers.ItemRequestService;
import com.example.BarterApplication.helpers.ItemService;
import com.example.BarterApplication.helpers.TestHelper;
import com.example.BarterApplication.helpers.UidService;
import com.google.firebase.auth.FirebaseAuth;

import org.hamcrest.core.IsNot;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.Espresso.pressBack;

@RunWith(AndroidJUnit4.class)
public class MyRequestPageTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<MainActivity>(MainActivity.class);
    private FirebaseAuth mAuth;
    private Item offerItem;
    private Item requestItem;
    private ItemRequest request;

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
    }

    @Test
    public void testMyRequest_AT_08_03(){
        onView(withId(R.id.viewMyRequestBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.requestRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.itemRequestTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.requestID)).check(matches(isDisplayed()));
        pressBack();
    }

    @Test
    public void testMyRequest_AT_08_04(){
        //ensure the request is accepted
        onView(withId(R.id.viewMyRequestBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.requestRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.acceptRequestBtn)).perform(click());
        onView(withId(R.id.closeBtn)).perform(click());
        //check refuse button is disabled after clicking refuse.
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.viewMyRequestBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.requestRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.refuseRequestBtn)).perform(click());
        onView(withId(R.id.closeBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.viewMyRequestBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.requestRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.refuseRequestBtn)).check(matches(IsNot.not(isEnabled())));
        pressBack();
    }

    @Test
    public void testMyRequest_AT_08_05(){
        //ensure the request is refused
        onView(withId(R.id.viewMyRequestBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.requestRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.refuseRequestBtn)).perform(click());
        onView(withId(R.id.closeBtn)).perform(click());
        //check refuse button is disabled after clicking accept.
        onView(withId(R.id.viewMyRequestBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.requestRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.acceptRequestBtn)).perform(click());
        onView(withId(R.id.closeBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.viewMyRequestBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.requestRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.acceptRequestBtn)).check(matches(IsNot.not(isEnabled())));
        pressBack();
    }

    @Test
    public void testMyRequest_AT_08_06(){
        onView(withId(R.id.viewMyRequestBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.requestRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.closeBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.closeBtn)).check(matches(isClickable()));
        onView(withId(R.id.closeBtn)).check(matches(isEnabled()));
        onView(withId(R.id.acceptRequestBtn)).perform(click());
        onView(withId(R.id.closeBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.viewMyRequestBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.requestRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.acceptRequestBtn)).check(matches(IsNot.not(isEnabled())));
        pressBack();
    }

    @Test
    public void testMyRequest_AT_08_07(){
        onView(withId(R.id.viewMyRequestBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.requestRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.acceptRequestBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.refuseRequestBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.acceptRequestBtn)).check(matches(isClickable()));
        onView(withId(R.id.refuseRequestBtn)).check(matches(isClickable()));
        pressBack();
    }

    @Test
    public void testMyRequest_AT_08_08(){
        if (ItemRequestService.getItemRequestList().isEmpty()){
            //insert test data in firebase
            String requesterId = "HhbguXQAWvXuCPgpVLOV3H3syQy1";
            ArrayList<String> labels = new ArrayList<>();
            labels.add("testLabels");
            offerItem = new Item("offerItem" + UidService.newUID(), "test desc", labels, requesterId);
            requestItem = new Item("requestItem" + UidService.newUID(), "test desc", labels, "1IBtBykzk1PegTxIsABKy7dqGtx1");
            ItemService.addItem(offerItem);
            ItemService.addItem(requestItem);
            request = new ItemRequest(requesterId, requestItem, offerItem);
            ItemRequestService.addItemRequest(request);
        }
        else{
            request = ItemRequestService.getItemRequestList().get(0);
        }

        onView(withId(R.id.viewMyRequestBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.requestRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.requestID)).check(matches(withText(request.getUid())));
        onView(withId(R.id.requestItemInfo)).check(matches(isDisplayed()));
        onView(withId(R.id.offeredItemInfo)).check(matches(IsNot.not(withText(""))));
        onView(withId(R.id.requestItemInfo)).check(matches(IsNot.not(withText(""))));
        pressBack();
    }

    @After
    public void teardown()
    {
        FirebaseAuth.getInstance().signOut();
    }
}
