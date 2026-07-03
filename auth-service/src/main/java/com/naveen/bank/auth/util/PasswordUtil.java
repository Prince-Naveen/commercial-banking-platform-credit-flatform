package com.naveen.bank.auth.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PasswordUtil {

    private static final PasswordEncoder PASSWORD_ENCODER =
            new BCryptPasswordEncoder();

    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%&*!";

    private static final SecureRandom RANDOM =
            new SecureRandom();

    /**
     * Encode Password
     */
    public static String encode(String password) {

        return PASSWORD_ENCODER.encode(password);
    }

    /**
     * Verify Password
     */
    public static boolean matches(String rawPassword,
                                  String encodedPassword) {

        return PASSWORD_ENCODER.matches(rawPassword, encodedPassword);
    }

    /**
     * Check Password Strength
     */
    public static boolean isStrongPassword(String password) {

        if (password == null || password.length() < 8) {
            return false;
        }

        boolean upperCase = false;
        boolean lowerCase = false;
        boolean digit = false;
        boolean special = false;

        for (char ch : password.toCharArray()) {

            if (Character.isUpperCase(ch)) {
                upperCase = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCase = true;
            } else if (Character.isDigit(ch)) {
                digit = true;
            } else {
                special = true;
            }

        }

        return upperCase && lowerCase && digit && special;
    }

    /**
     * Generate Random Password
     */
    public static String generateRandomPassword(int length) {

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {

            password.append(
                    CHARACTERS.charAt(
                            RANDOM.nextInt(CHARACTERS.length())
                    )
            );
        }

        return password.toString();
    }

}