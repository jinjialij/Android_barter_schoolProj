package com.example.BarterApplication;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ItemRequestTest {
    @Before
    public void setup(){

    }

    @After
    public void teardown(){
    }

    @Test
    public void test_CTOR(){
        String requesterId = "requesterId";
        String name = "my item";
        Item item = new Item(name, requesterId);
        ItemRequest itemRequest = new ItemRequest(requesterId, item);
        assertEquals(itemRequest.getItem().get_name(), name);
        assertEquals(itemRequest.getRequesterId(), requesterId);
    }
}
