package com.naveen.bank.auth.validation;

public final class JwtValidator {

    private JwtValidator() {
    }

    public static boolean hasBearerPrefix(String token) {

        return token != null
                && token.startsWith("Bearer ");
    }

    public static String removeBearerPrefix(String token) {

        if (hasBearerPrefix(token)) {
            return token.substring(7);
        }

        return token;
    }

}