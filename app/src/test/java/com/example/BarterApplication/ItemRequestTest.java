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
        String n = "my item";
        Item i = new Item(n, requesterId);
        assertEquals(i.get_name(), n);
        assertEquals(i.requesterId(), requesterId);
    }
}
