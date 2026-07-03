package com.naveen.bank.auth.constants;

public final class ApiConstants {

    private ApiConstants() {
    }

    public static final String API_BASE_URL = "/api/v1/auth";

    public static final String REGISTER = "/register";
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";
    public static final String REFRESH_TOKEN = "/refresh-token";

    public static final String USERS = "/users";
    public static final String ROLES = "/roles";
    public static final String PERMISSIONS = "/permissions";

}