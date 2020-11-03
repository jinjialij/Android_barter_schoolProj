package com.example.BarterApplication;

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
    public void test_CTOR1(){
        String n = "my item";
        Item i = new Item(n);
        assertEquals(i.get_name(), n);
    }

    @Test
    public void test_CTOR2(){
        String n = "my item";
        String d = "my_description";
        Item i = new Item(n, d);
        assertEquals(i.get_name(), n);
        assertEquals(i.get_description(), d);
    }

    @Test
    public void test_CTOR3(){
        String n = "my item";
        String d = "my_description";
        String[] l = new String[] {"label1", "label2"};
        Item i = new Item(n, d, l);
        assertEquals(i.get_name(), n);
        assertEquals(i.get_description(), d);
        assertEquals(i.get_labels(), l);
    }

    @Test   //TEST SET DESCRIPTION
    public  void test_SETDESC()
    {
        String n = "my item";
        String d = "my_description";
        String new_d = "new_description";
        String[] l = new String[] {"label1", "label2"};
        Item i = new Item(n, d, l);
        assertEquals(i.get_name(), n);
        assertEquals(i.get_description(), d);
        assertEquals(i.get_labels(), l);
        i.set_description(new_d);
        assertEquals(new_d, i.get_description());
    }

    @Test
    public  void test_ADDLABEL()
    {
        String n = "my item";
        String d = "my_description";
        String[] l = new String[] {"label1", "label2"};
        Item i = new Item(n, d, l);
        assertEquals(i.get_name(), n);
        assertEquals(i.get_description(), d);
        assertEquals(i.get_labels(), l);

        String new_l = "new label";
        i.add_label(new_l);
        String[] labels = i.get_labels();
        int num_labels_added = 1;
        int instance_count = 0;
        for(String s : labels){
            if(s.equals(new_l)){
                instance_count++;
            }
        }
        assertEquals(instance_count, num_labels_added);
    }

    @Test
    public  void test_ADDLABEL_DUPLICATE()
    {
        String n = "my item";
        String d = "my_description";
        String[] l = new String[] {"label1", "label2"};
        Item i = new Item(n, d, l);
        assertEquals(i.get_name(), n);
        assertEquals(i.get_description(), d);
        assertEquals(i.get_labels(), l);

        String new_l = "duplicate label";
        i.add_label(new_l);
        i.add_label(new_l);

        String[] labels = i.get_labels();
        int num_labels_added = 2;
        int instance_count = 1;
        for(String s : labels){
            if(s.equals(new_l)){
                instance_count++;
            }
        }

        //DUPLICATE LABELS SHOULD NOT BE ADDED
        assertEquals(instance_count, 1);
    }

    @Test
    public void test_DELLABEL(){
        String n = "my item";
        String d = "my_description";
        String[] l = new String[] {"label1", "label2"};
        Item i = new Item(n, d, l);
        assertEquals(i.get_name(), n);
        assertEquals(i.get_description(), d);
        assertEquals(i.get_labels(), l);
    }


}
