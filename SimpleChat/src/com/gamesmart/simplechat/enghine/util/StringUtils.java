package com.gamesmart.simplechat.enghine.util;

public class StringUtils {
    public static boolean isNullOrEmpty(String value){
        return value == null?true:((value.replace(" ","")).isEmpty()?true:false);
    }
}
