package com.healthcode.utils;

public class CheckValueUtil {
    public static Boolean checkStringHelper(String... values){
        for(String cur : values){
            if("".equals(cur)){
                return false;
            }
        }
        return true;
    }
}
