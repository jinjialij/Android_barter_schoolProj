package com.example.BarterApplication;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class ItemRequestTest {
    @Before
    public void setup(){

    }

    @After
    public void teardown(){
    }

    //@Test     //This test will fail until UID_Service tester is finished
    /*
    public void test_CTOR(){
        String userId = "userId";
        String userEmail = "userEmail@User.com";
        String name = "my item";
        UserAccount u = new UserAccount(userEmail, userId);
        Item itemBeingRequested = new Item(name, userId);
        String requesterId = "requesterId";
        String offered_item_name1 = "item_to_offer1";
        String offered_item_name2 = "item_to_offer2";
        ArrayList<Item> itemsOffered = new ArrayList<Item>();
        itemsOffered.add(new Item(offered_item_name1, requesterId));
        itemsOffered.add(new Item(offered_item_name2, requesterId));
        ItemRequest request = new ItemRequest(u, itemBeingRequested, itemsOffered);
        assertEquals(request.getItem().get_name(), name);
        assertEquals(request.getRequesterId(), requesterId);
        assertEquals(request.getRequester().getUid(), requesterId);
        for(int i = 0; i < request.getItemIdsOffered().size(); i++){
            assertTrue(request.getItemIdsOffered().contains(itemsOffered.get(i).getUid()));
        }
    }
    */

    @Test
    public void test_CTOR_for_firebase_currentUser_use(){
        //in this case, user is the requester
        String userId = "userId";
        String name = "my item";
        Item itemBeingRequested = new Item(name, userId);
        String offered_item_name1 = "item_to_offer1";
        String offered_item_name2 = "item_to_offer2";
        ArrayList<Item> itemsOffered = new ArrayList<Item>();
        Item offer1 = new Item(offered_item_name1, userId);
        Item offer2 = new Item(offered_item_name2, userId);
        itemsOffered.add(offer1);
        itemsOffered.add(offer2);
        ItemRequest request = new ItemRequest(userId, itemBeingRequested, itemsOffered);
        assertEquals(request.getRequestedItemId(), itemBeingRequested.getUid());
        assertEquals(request.getRequesterId(), userId);
        assertEquals(request.getItemIdsOffered().size(), itemsOffered.size());

        ArrayList<String> itemIdsOffered = new ArrayList<String>();
        itemIdsOffered.add(offer1.getUid());
        itemIdsOffered.add(offer2.getUid());
        assertTrue(itemIdsOffered.containsAll(request.getItemIdsOffered()));
    }
}
