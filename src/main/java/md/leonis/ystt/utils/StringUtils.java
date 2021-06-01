package md.leonis.ystt.utils;

import java.util.Collections;

public class StringUtils {

    public static String leftPad(String str, int size, String padStr) {

        if (str.length() >= size) {
            return str;
        } else {
            int toAdd = size - str.length();
            return String.join("", Collections.nCopies(toAdd, padStr)) + str;
        }
    }

    public static String splitCamelCase(String s) {
        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }
}
