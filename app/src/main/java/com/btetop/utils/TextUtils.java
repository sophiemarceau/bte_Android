package com.btetop.utils;

public class TextUtils {
    public static String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }
}
