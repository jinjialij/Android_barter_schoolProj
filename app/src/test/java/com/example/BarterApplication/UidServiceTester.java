package com.example.BarterApplication;

import com.example.BarterApplication.helpers.UidService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

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

    @Test
    public void findItemByIdTest(){
        ArrayList<Item> items = new ArrayList<Item>();
        Item item = new Item("item1", "owner1");
        items.add(item);
        Item resultItem = UidService.findItemByItemUid(item.getUid(), items);
        assertNotNull(resultItem);
        assertEquals(item.getUid(), resultItem.getUid());
    }

    @Test
    public void findItemOwnerByItemOwnerIdTest(){
        String e = "validEmail@test.com";
        String uid = "1234567";
        UserAccount userAccount = new UserAccount(e, uid);
        ArrayList<UserAccount> userAccounts = new ArrayList<UserAccount>();
        Item item = new Item("item1", "1234567");
        userAccounts.add(userAccount);
        User owner = UidService.findItemOwnerByOwnerId(item.getOwnerId(), userAccounts);
        assertEquals(item.getOwnerId(), owner.getUid());
    }
}
