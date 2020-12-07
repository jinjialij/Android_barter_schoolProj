package com.example.BarterApplication;

import com.example.BarterApplication.helpers.ValidationHelper;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidationHelperTest {
    @Test
    public void email_isCorrect() {
        String email = "abc@dal.ca";
        String email2 = "abc";
        String email3 = "abc@dal";

        assertTrue(ValidationHelper.isValidEmail(email));
        assertFalse(ValidationHelper.isValidEmail(email2));
        assertFalse(ValidationHelper.isValidEmail(email3));
    }

    @Test
    public void title_isCorrect() {
        String name = "item";
        String invalidName = "@@item";

        assertTrue(ValidationHelper.isValidItemTitle(name));
    }
}
