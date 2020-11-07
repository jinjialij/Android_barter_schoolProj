package com.example.BarterApplication.helpers;

import android.text.TextUtils;
import android.util.Patterns;

import org.w3c.dom.Text;

import java.util.regex.Pattern;



public class CredentialHelper {
    private static String emailRegexExpr = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$";
    private static Pattern emailRegex = Pattern.compile(emailRegexExpr, Pattern.CASE_INSENSITIVE);
    public static boolean isValidEmail(CharSequence target) {
        if(target == null){
            return false;
        }
        else {
            return emailRegex.matcher(target).matches();
        }
    }

    public static boolean isValidPassword(String s) {
        if(s == null){
            return false;
        }
        else
        {
            Pattern PASSWORD_PATTERN
                    = Pattern.compile(
                    "[a-zA-Z0-9\\!\\@\\#\\$]{6,24}");
            return !TextUtils.isEmpty(s) && PASSWORD_PATTERN.matcher(s).matches();
        }
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
