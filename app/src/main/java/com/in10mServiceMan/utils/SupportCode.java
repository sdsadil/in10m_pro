package com.in10mServiceMan.utils;

public class SupportCode {

    public static String getNullStringEmpty(String text) {
        if (text != null) {
            if (!text.equals("")) {
                if (!text.equals("null")) {
                    return text;
                } else {
                    return "";
                }
            } else {
                return "";
            }
        } else {
            return "";
        }
    }
}
