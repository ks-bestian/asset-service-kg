package kr.co.bestiansoft.ebillservicekg.common.utils;

import java.util.UUID;

/*
StringUtil use example
String[] words = {"hello", "world"};
String joinedString = StringUtil.join(words, ", ");

 * */

public class StringUtil {

    // Checks if a string is null or empty
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    // Reverses a given string
    public static String reverse(String str) {
        if (str == null) {
            return null;
        }
        return new StringBuilder(str).reverse().toString();
    }

    // Joins an array of strings with a given delimiter
    public static String join(String[] array, String delimiter) {
        return String.join(delimiter, array);
    }

    // Converts the first character of each word to uppercase
    public static String capitalizeWords(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        String[] words = str.split("\\s");
        StringBuilder capitalizedStr = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                capitalizedStr.append(Character.toUpperCase(word.charAt(0)))
                              .append(word.substring(1).toLowerCase())
                              .append(" ");
            }
        }
        return capitalizedStr.toString().trim();
    }

    //bill id
    public static String getEbillId() {
        return "EB_"+UUID.randomUUID().toString();
    }

    //bill proc id
    public static String getEbillProcId() {
        return "PC_"+UUID.randomUUID().toString();
    }

    public static String getUUUID() {
        return UUID.randomUUID().toString();
    }

    public static String getNameUUUID() {
    	String namespace = "bestiansoftco.kr";
        return UUID.nameUUIDFromBytes(namespace.getBytes()).toString();
    }

}

