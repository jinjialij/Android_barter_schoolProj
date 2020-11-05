package com.example.BarterApplication;

import com.example.BarterApplication.helpers.UID_Service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class UID_ServiceTester {

    @Before
    public void setup(){

    }

    @After
    public void teardown(){

    }

    @Test
    public void uniquenessTest(){
        String first = UID_Service.newUID();
        String second = UID_Service.newUID();
        assertFalse(first.equals(second));
    }

}
