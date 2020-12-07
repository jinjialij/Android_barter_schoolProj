package com.example.BarterApplication;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.BarterApplication.helpers.ItemRequestService;
import com.example.BarterApplication.helpers.ItemService;
import com.example.BarterApplication.helpers.TestHelper;
import com.example.BarterApplication.helpers.UidService;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;

@RunWith(AndroidJUnit4.class)
public class ViewMyRequestTest {
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

        //ensure firebase has data to test
        if (ItemRequestService.getItemRequestList().isEmpty()){
            //insert test data in firebase
            String requesterId = "HhbguXQAWvXuCPgpVLOV3H3syQy1";
            ArrayList<String> labels = new ArrayList<>();
            labels.add("testLabel");
            offerItem = new Item("offerItem" + UidService.newUID(), "desc", labels , requesterId);
            requestItem = new Item("requestItem" + UidService.newUID(), "1IBtBykzk1PegTxIsABKy7dqGtx1");
            ItemService.addItem(offerItem);
            ItemService.addItem(requestItem);
            request = new ItemRequest(requesterId, requestItem, offerItem);
            ItemRequestService.addItemRequest(request);
        }
        else{
            request = ItemRequestService.getItemRequestList().get(0);
            requestItem = ItemService.findItemByUid(request.getRequestItemId());
        }
        //reload homepage to reload data
        onView(withId(R.id.viewAddItemBtn)).perform(click());
        pressBack();
    }

    @Test
    public void testViewMyRequestButton_AT_08_01(){
        onView(withId(R.id.viewMyRequestBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.viewMyRequestBtn)).check(matches(isClickable()));
    }

    @Test
    public void testViewMyRequestButton_redirecting_feature_AT_08_02(){
        onView(withId(R.id.viewMyRequestBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.viewMyRequestBtn)).check(doesNotExist());
        onView(withId(R.id.viewMyRequestTitle)).check(matches(isDisplayed()));
    }

    @Test
    public void testViewMyRequestButton_show_itemRequests_AT_08_02(){
        onView(withId(R.id.viewMyRequestBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.requestRecyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.requestRecyclerView)).check(matches(hasDescendant(withText(containsString("Request id: ")))));
    }

    @Test
    public void testViewMyRequestButton_show_request_from_others_AT_1901(){
        onView(withId(R.id.viewMyRequestBtn)).perform(click());
        onView(isRoot()).perform(TestHelper.waitFor(5000));
        onView(withId(R.id.viewAllRequestsBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.viewSentRequestBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.viewReceivedRequestBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.viewAllRequestsBtn)).check(matches(isClickable()));
        onView(withId(R.id.viewSentRequestBtn)).check(matches(isClickable()));
        onView(withId(R.id.viewReceivedRequestBtn)).check(matches(isClickable()));
        onView(withId(R.id.ViewMyRequestCompletedBtn)).check(matches(isDisplayed()));
        onView(withId(R.id.ViewMyRequestCompletedBtn)).check(matches(isClickable()));
    }

    @After
    public void teardown()
    {
        FirebaseAuth.getInstance().signOut();
    }
}
