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
        assertEquals(i.getName(), n);
        assertEquals(i.getOwnerId(), ownerId);
    }

    @Test
    public void test_CTOR2(){
        String n = "my item";
        String d = "my_description";
        String ownerId = "123456";
        Item i = new Item(n, d, ownerId);
        assertEquals(i.getName(), n);
        assertEquals(i.getDescription(), d);
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
        assertEquals(i.getName(), n);
        assertEquals(i.getDescription(), d);
        assertTrue(i.getLabels().contains("label1"));
        assertTrue(i.getLabels().contains("label2"));
        assertEquals(i.getOwnerId(), ownerId);
    }

    @Test   //TEST SET DESCRIPTION
    public  void test_SETDESC()
    {
        String n = "my item";
        String d = "my_description";
        String new_d = "new_description";
        String ownerId = "123456";
        ArrayList<String> labels = new ArrayList<>();
        labels.add("label1");
        labels.add("label2");
        Item i = new Item(n, d, labels, ownerId);
        assertEquals(i.getName(), n);
        assertEquals(i.getDescription(), d);
        assertTrue(i.getLabels().contains("label1"));
        assertTrue(i.getLabels().contains("label2"));
        i.setDescription(new_d);
        assertEquals(new_d, i.getDescription());
    }

    @Test
    public  void test_ADDLABEL()
    {
        String n = "my item";
        String d = "my_description";
        String ownerId = "123456";
        ArrayList<String> labels = new ArrayList<>();
        labels.add("label1");
        Item i = new Item(n, d, labels, ownerId);
        assertEquals(i.getName(), n);
        assertEquals(i.getDescription(), d);
        assertTrue(i.getLabels().contains("label1"));
    }

    @Test
    public  void test_ADDLABEL_DUPLICATE()
    {
        String name = "my item";
        String desc = "my_description";
        String duplicate_label = "duplicate_label";
        String ownerId = "123456";
        ArrayList<String> labels = new ArrayList<>();
        Item item = new Item(name, desc, labels, ownerId);
        item.addLabel(duplicate_label);
        item.addLabel(duplicate_label);
        int count = 0;
        for(String s : item.getLabels()){
            if(s.equals(duplicate_label)){
                count++;
            }
        }
        assertTrue(count == 1);
    }

    @Test
    public void test_DELLABEL(){

    }

    @Test
    public  void test_addLabels()
    {
        String n = "my item";
        String d = "my_description";
        String ownerId = "123456";
        Item i = new Item(n, ownerId);
        ArrayList<String> labels = new ArrayList<>();
        labels.add("label1");
        labels.add("label2");
        i.addLabels(labels);

        assertEquals(i.getLabels().size(), 2);
        assertEquals(i.getLabels(), labels);
    }
}
