package com.naveen.bank.auth.service;

import com.naveen.bank.auth.dto.request.LoginRequest;
import com.naveen.bank.auth.dto.request.RegisterRequest;
import com.naveen.bank.auth.dto.response.JwtResponse;
import com.naveen.bank.auth.dto.response.UserResponse;

import java.util.UUID;

public interface AuthService {

    /**
     * Register New User
     */
    UserResponse register(RegisterRequest request);

    /**
     * Login User
     */
    JwtResponse login(LoginRequest request);

    /**
     * Logout User
     */
    void logout(String refreshToken);

    /**
     * Generate New Access Token
     */
    JwtResponse refreshToken(String refreshToken);

    /**
     * Validate JWT Token
     */
    boolean validateToken(String token);

    /**
     * Forgot Password
     */
    void forgotPassword(String username);

    /**
     * Reset Password
     */
    void resetPassword(String username,
                       String newPassword);

    /**
     * Change Password
     */
    void changePassword(UUID userId,
                        String oldPassword,
                        String newPassword);

    /**
     * Verify OTP
     */
    boolean verifyOtp(String username,
                      String otp);

}