package com.homura.common.util;

public class StringUtils {
    public static boolean isNull(String str){
        return str == null || str.equals("") ? true:false;
    }
}
