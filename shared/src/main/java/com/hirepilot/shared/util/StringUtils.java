package com.hirepilot.shared.util;

public final class StringUtils {
    private StringUtils() {
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static String normalize(String value) {
        return isBlank(value) ? "" : value.trim();
    }
}
