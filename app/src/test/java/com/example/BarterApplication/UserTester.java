package com.example.BarterApplication;


import com.example.BarterApplication.Item;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTester {


    @Before
    public void setup(){

    }

    @After
    public void teardown(){

    }

    @Test
    public void creationTest(){
        String e = "validEmail@test.com";
        String p = "testPassword";
        User u = new User(e, p);
        assertEquals(u.getEmail(), e);
        assertEquals(u.getPassword(), p);
    }
}
