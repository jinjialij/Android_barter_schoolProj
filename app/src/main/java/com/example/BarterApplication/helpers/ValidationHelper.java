package com.example.BarterApplication.helpers;

import java.util.regex.Pattern;

public class ValidationHelper {
    private static Pattern emailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z0-9_]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static Pattern itemTitleRegex = Pattern.compile("^[^@][a-zA-Z0-9 ].*$");

    public static boolean isValidEmail(String input){
        if (!emailRegex.matcher(input).find()){
            return false;
        }
        return true;
    }

    public static boolean isValidItemTitle(String title){
        if (!itemTitleRegex.matcher(title).find()){
            return false;
        }
        return true;
    }
}
