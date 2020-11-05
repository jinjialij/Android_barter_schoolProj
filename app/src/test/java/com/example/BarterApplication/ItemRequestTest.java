package com.example.BarterApplication;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ItemRequestTest {
    @Before
    public void setup(){

    }

    @After
    public void teardown(){
    }

    @Test
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
        for(int i = 0; i < request.getOfferings().size(); i++){
            assertTrue(request.getOfferings().contains(itemsOffered.get(i).getUid()));
        }
    }
}
