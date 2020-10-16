package com.example.BarterApplication.helpers;

import android.text.TextUtils;
import android.util.Patterns;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

public class CredentialHelper {

    /* Can someone explain why these methods must be static? Really not understanding it but static makes them work. - Carl */
    /* Is static keyword in java different than static in C / C++ */

    public static boolean isValidEmail(CharSequence target) {
        return (charSeq_isEmailEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean isValidPassword(String s) {
        Pattern PASSWORD_PATTERN
                = Pattern.compile(
                "[a-zA-Z0-9\\!\\@\\#\\$]{4,24}");
        return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
    }


    public static boolean charSeq_isEmailEmpty(CharSequence target)
    {
        return target != null && TextUtils.isEmpty(target);
    }


    public static boolean string_isEmailEmpty(String s)
    {
        return s != null && s.isEmpty();
    }
}
