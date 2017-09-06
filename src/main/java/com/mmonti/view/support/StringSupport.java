package com.mmonti.view.support;

public class StringSupport {

    /**
     *
     * @param value
     * @return
     */
    public static String decapitalize(final String value) {
        if (value == null || value.length() == 0) {
            return value;
        }
        char c[] = value.toCharArray();
        c[0] = Character.toLowerCase(c[0]);
        return new String(c);
    }

}
