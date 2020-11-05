package com.example.BarterApplication;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ItemTester {

    @Before
    public void setup(){

    }

    @After
    public void teardown(){
    }

    @Test
    public void test_CTOR1(){
        String n = "my item";
        String ownerId = "123456";
        Item i = new Item(n, ownerId);
        assertEquals(i.get_name(), n);
        assertEquals(i.getOwnerId(), ownerId);
    }

    @Test
    public void test_CTOR2(){
        String n = "my item";
        String d = "my_description";
        String ownerId = "123456";
        Item i = new Item(n, d, ownerId);
        assertEquals(i.get_name(), n);
        assertEquals(i.get_description(), d);
        assertEquals(i.getOwnerId(), ownerId);
    }

    @Test
    public void test_CTOR3(){
        String n = "my item";
        String d = "my_description";
        String new_d = "new_description";
        String ownerId = "123456";
        ArrayList<String> labels = new ArrayList<>();
        labels.add("label1");
        labels.add("label2");
        Item i = new Item(n, d, labels, ownerId);
        assertEquals(i.get_name(), n);
        assertEquals(i.get_description(), d);
        assertTrue(i.get_labels().contains("label1"));
        assertTrue(i.get_labels().contains("label2"));
        assertEquals(i.getOwnerId(), ownerId);
    }

    @Test   //TEST SET DESCRIPTION
    public  void test_SETDESC()
    {
        String n = "my item";
        String d = "my_description";
        String new_d = "new_description";
        ArrayList<String> labels = new ArrayList<>();
        labels.add("label1");
        labels.add("label2");
        Item i = new Item(n, d, labels);
        assertEquals(i.get_name(), n);
        assertEquals(i.get_description(), d);
        assertTrue(i.get_labels().contains("label1"));
        assertTrue(i.get_labels().contains("label2"));
        i.set_description(new_d);
        assertEquals(new_d, i.get_description());
    }

    @Test
    public  void test_ADDLABEL()
    {
        String n = "my item";
        String d = "my_description";
        ArrayList<String> labels = new ArrayList<>();
        labels.add("label1");
        Item i = new Item(n, d, labels);
        assertEquals(i.get_name(), n);
        assertEquals(i.get_description(), d);
        assertTrue(i.get_labels().contains("label1"));
    }

    @Test
    public  void test_ADDLABEL_DUPLICATE()
    {
        String name = "my item";
        String desc = "my_description";
        String duplicate_label = "duplicate_label";
        ArrayList<String> labels = new ArrayList<>();
        Item item = new Item(name, desc, labels);
        item.add_label(duplicate_label);
        item.add_label(duplicate_label);
        int count = 0;
        for(String s : item.get_labels()){
            if(s.equals(duplicate_label)){
                count++;
            }
        }
        assertTrue(count == 1);
    }

    @Test
    public void test_DELLABEL(){

    }

}
