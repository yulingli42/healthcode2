package com.healthcode.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author qianlei
 */
public class IntegerUtil{
    public static Integer parseInt(String s) {
        if (StringUtils.isNumeric(s)) {
            return Integer.parseInt(s);
        } else {
            return null;
        }
    }
}
