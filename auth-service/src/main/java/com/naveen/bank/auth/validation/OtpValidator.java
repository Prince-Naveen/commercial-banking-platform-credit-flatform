package com.naveen.bank.auth.validation;

public final class OtpValidator {

    private OtpValidator() {
    }

    public static boolean isValid(String otp) {

        return otp != null
                && otp.matches("\\d{6}");
    }

}