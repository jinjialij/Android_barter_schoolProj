package com.example.BarterApplication;

import com.example.BarterApplication.helpers.UidService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class UidServiceTester {

    @Before
    public void setup(){

    }

    @After
    public void teardown(){

    }

    @Test
    public void uniquenessTest(){
        String first = UidService.newUID();
        String second = UidService.newUID();
        assertFalse(first.equals(second));
    }
}
