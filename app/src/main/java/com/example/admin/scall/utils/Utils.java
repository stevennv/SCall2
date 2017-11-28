package com.example.admin.scall.utils;

/**
 * Created by Admin on 11/28/2017.
 */

public class Utils {
    public static String formatNumber(String number) {
        String result1 = number.replace("-", "");
        String result2 = result1.replace("(", "");
        String result3 = result2.replace(")", "");
        String result = result3.replace(" ", "");

        return result;
    }
}
