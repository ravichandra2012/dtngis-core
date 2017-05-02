package util;

/**
 * Few useful methods
 * Created by arka on 2/5/17.
 */
public class Util {

    /**
     * Remove specified characters from beginning and end of a string
     * @param str
     * @param ch
     * @return The trimmed string
     */
    public static String  trimString(String str, char ch) {
        if(str == null) return null;
        if(str == "") return "";

        String newStr = "";

        newStr = str.replaceAll("^\\" + ch + "+", "");
        newStr = newStr.replaceAll("\\" + ch + "+$", "");

        return newStr;
    }
}
