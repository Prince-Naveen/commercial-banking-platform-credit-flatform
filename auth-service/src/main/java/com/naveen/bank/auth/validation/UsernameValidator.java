package com.naveen.bank.auth.validation;

import java.util.regex.Pattern;

public final class UsernameValidator {

    private UsernameValidator() {
    }

    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._]{4,20}$");

    public static boolean isValid(String username) {

        return username != null
                && USERNAME_PATTERN.matcher(username).matches();
    }

}