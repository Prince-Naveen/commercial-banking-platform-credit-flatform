package com.naveen.bank.auth.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ValidationUtil {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private static final Pattern MOBILE_PATTERN =
            Pattern.compile("^[6-9]\\d{9}$");

    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._]{4,20}$");

    /**
     * Validate Email
     */
    public static boolean isValidEmail(String email) {

        return email != null
                && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Validate Mobile Number
     */
    public static boolean isValidMobile(String mobile) {

        return mobile != null
                && MOBILE_PATTERN.matcher(mobile).matches();
    }

    /**
     * Validate Username
     */
    public static boolean isValidUsername(String username) {

        return username != null
                && USERNAME_PATTERN.matcher(username).matches();
    }

    /**
     * Check Null
     */
    public static boolean isNull(Object object) {

        return object == null;
    }

    /**
     * Check Not Null
     */
    public static boolean isNotNull(Object object) {

        return object != null;
    }

    /**
     * Check Empty String
     */
    public static boolean isEmpty(String value) {

        return value == null || value.trim().isEmpty();
    }

    /**
     * Check Not Empty String
     */
    public static boolean isNotEmpty(String value) {

        return !isEmpty(value);
    }

    /**
     * Check Empty Collection
     */
    public static boolean isEmpty(Collection<?> collection) {

        return collection == null || collection.isEmpty();
    }

    /**
     * Check Not Empty Collection
     */
    public static boolean isNotEmpty(Collection<?> collection) {

        return !isEmpty(collection);
    }

}