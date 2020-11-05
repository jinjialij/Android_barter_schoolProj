package com.example.BarterApplication;

import com.example.BarterApplication.helpers.CredentialHelper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.security.cert.Certificate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CredentialHelperTester {


    @Before
    public void setup(){

    }


    @After
    public void teardown(){

    }


    @Test
    public void emailTestPositive(){
        assertTrue(CredentialHelper.isValidEmail("test@Test.com"));
        assertTrue(CredentialHelper.isValidEmail("test123@test123.com"));
        assertTrue(CredentialHelper.isValidEmail("test@test.ca"));
        assertTrue(CredentialHelper.isValidEmail("test_with_underscore@test.com"));
        assertTrue(CredentialHelper.isValidEmail("user@email_with_an_underscore.com"));
        assertTrue(CredentialHelper.isValidEmail("superVeryVeryVeryVeryVeryVeryVeryLongUserNameForAnEmailUserName@email.com"));
        assertTrue(CredentialHelper.isValidEmail("user@superVeryVeryVeryVeryVeryVeryVeryLongEmailAddress.com"));
    }

    @Test
    public void emailTestNegative(){
        assertFalse(CredentialHelper.isValidEmail("test@"));
        assertFalse(CredentialHelper.isValidEmail("test@.com"));
        assertFalse(CredentialHelper.isValidEmail("test with a sspace@test.com"));
        assertFalse(CredentialHelper.isValidEmail("tester123@email with a space.com"));
        assertFalse(CredentialHelper.isValidEmail("user@double_email.com.com"));
        assertFalse(CredentialHelper.isValidEmail("user@no_domain."));
        assertFalse(CredentialHelper.isValidEmail("user@numeric_domain.1023103219"));
        assertFalse(CredentialHelper.isValidEmail("user@.domain_starting_with_a_space.com"));
        assertFalse(CredentialHelper.isValidEmail("user@domain_with_garbage#$$$$@#$@$e.com"));
        assertFalse(CredentialHelper.isValidEmail("")); // empty password
        assertFalse(CredentialHelper.isValidEmail(null));
    }


    @Test
    public void passwordTestPositive(){
        assertTrue(CredentialHelper.isValidPassword("validPassword123"));
        assertTrue(CredentialHelper.isValidPassword("embedded123alphanumeric"));
        assertTrue(CredentialHelper.isValidPassword("password_with_underscores"));
        assertTrue(CredentialHelper.isValidPassword("password_with_underscores_and_numbers1234"));

    }

    @Test
    public  void passwordTestNegative(){
        assertFalse(CredentialHelper.isValidPassword("purely_alphabetic_password"));
        assertFalse(CredentialHelper.isValidPassword("123409454059")); //purely numeric password
        assertFalse(CredentialHelper.isValidPassword("")); // empty password
        assertFalse(CredentialHelper.isValidPassword(null)); // empty password
        assertFalse(CredentialHelper.isValidPassword("garbage_in_the_password@#$(@#$(!@"));
        assertFalse(CredentialHelper.isValidPassword("spaces in the password"));
        assertFalse(CredentialHelper.isValidPassword("_______"));
        assertFalse(CredentialHelper.isValidPassword("short1")); // short password
        assertFalse(CredentialHelper.isValidPassword("password-with-hyphens"));
    }
}
