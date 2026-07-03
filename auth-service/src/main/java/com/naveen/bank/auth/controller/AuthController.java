package com.naveen.bank.auth.controller;

import com.naveen.bank.auth.dto.request.LoginRequest;
import com.naveen.bank.auth.dto.request.RegisterRequest;
import com.naveen.bank.auth.dto.response.JwtResponse;
import com.naveen.bank.auth.dto.response.UserResponse;
import com.naveen.bank.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Register User
     */
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        return ResponseEntity.ok(
                authService.register(request));
    }

    /**
     * Login
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(request));
    }

    /**
     * Logout
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestParam String refreshToken) {

        authService.logout(refreshToken);

        return ResponseEntity.ok("Logout Successful");
    }

    /**
     * Refresh Token
     */
    @PostMapping("/refresh-token")
    public ResponseEntity<JwtResponse> refreshToken(
            @RequestParam String refreshToken) {

        return ResponseEntity.ok(
                authService.refreshToken(refreshToken));
    }

    /**
     * Validate Token
     */
    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(
            @RequestParam String token) {

        return ResponseEntity.ok(
                authService.validateToken(token));
    }

    /**
     * Forgot Password
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @RequestParam String username) {

        authService.forgotPassword(username);

        return ResponseEntity.ok("OTP Sent Successfully");
    }

    /**
     * Reset Password
     */
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam String username,
            @RequestParam String newPassword) {

        authService.resetPassword(username, newPassword);

        return ResponseEntity.ok("Password Reset Successfully");
    }

    /**
     * Change Password
     */
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(
            @RequestParam UUID userId,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {

        authService.changePassword(
                userId,
                oldPassword,
                newPassword);

        return ResponseEntity.ok("Password Changed Successfully");
    }

    /**
     * Verify OTP
     */
    @PostMapping("/verify-otp")
    public ResponseEntity<Boolean> verifyOtp(
            @RequestParam String username,
            @RequestParam String otp) {

        return ResponseEntity.ok(
                authService.verifyOtp(username, otp));
    }

}