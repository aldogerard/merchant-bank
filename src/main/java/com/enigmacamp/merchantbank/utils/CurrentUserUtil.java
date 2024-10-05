package com.enigmacamp.merchantbank.utils;

public class CurrentUserUtil {
    private static final ThreadLocal<String> currentEmail = new ThreadLocal<>();

    public static void setEmail(String email) {
        currentEmail.set(email);
    }

    public static String getEmail() {
        return currentEmail.get();
    }

    public static void clear() {
        currentEmail.remove();
    }
}
