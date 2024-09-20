package core.constants;

import core.good;

import java.util.HashMap;

public class goodAcronyms {
    private static HashMap<String, String> acronyms;
    public static void initialize() {
        acronyms = new HashMap<>();
        for (String x: good.getGoodArray()) {
            String shortKey = shortenString(x);
            acronyms.put(x, shortKey);
        }
    }

    public static String shortenString(String str) {
        StringBuilder acronym = new StringBuilder();

        // Loop through the string and get the first two letters after each capital letter
        for (int i = 0; i < str.length(); i++) {
            if (Character.isUpperCase(str.charAt(i))) {
                acronym.append(str.charAt(i));

                // Add the next character if it exists
                if (i + 1 < str.length()) {
                    acronym.append(str.charAt(i + 1));
                }
            }
        }

        return acronym.toString();
    }

    public static String getAcronym(String goodName) {
        return acronyms.get(goodName);
    }
}
