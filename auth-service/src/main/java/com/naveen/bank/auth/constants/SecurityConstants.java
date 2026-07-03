package com.naveen.bank.auth.constants;

public final class SecurityConstants {

    private SecurityConstants() {
    }

    public static final String AUTHORIZATION = "Authorization";

    public static final String BEARER = "Bearer ";

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER = "Authorization";

    public static final String JWT = "JWT";

    public static final long ACCESS_TOKEN_VALIDITY = 1000 * 60 * 30;

    public static final long REFRESH_TOKEN_VALIDITY = 1000L * 60 * 60 * 24 * 7;

}