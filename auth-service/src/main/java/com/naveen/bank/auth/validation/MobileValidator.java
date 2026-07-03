package com.naveen.bank.auth.validation;

import java.util.regex.Pattern;

public final class MobileValidator {

    private MobileValidator() {
    }

    private static final Pattern MOBILE_PATTERN =
            Pattern.compile("^[6-9]\\d{9}$");

    public static boolean isValid(String mobile) {

        return mobile != null
                && MOBILE_PATTERN.matcher(mobile).matches();
    }

}