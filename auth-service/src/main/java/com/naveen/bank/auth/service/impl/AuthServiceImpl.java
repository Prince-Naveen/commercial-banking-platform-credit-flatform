package com.naveen.bank.auth.service.impl;

import com.naveen.bank.auth.dto.request.LoginRequest;
import com.naveen.bank.auth.dto.request.RegisterRequest;
import com.naveen.bank.auth.dto.response.JwtResponse;
import com.naveen.bank.auth.dto.response.RefreshTokenResponse;
import com.naveen.bank.auth.dto.response.UserResponse;
import com.naveen.bank.auth.entity.RefreshToken;
import com.naveen.bank.auth.entity.User;
import com.naveen.bank.auth.event.*;
import com.naveen.bank.auth.exception.InvalidCredentialsException;
import com.naveen.bank.auth.exception.UserNotFoundException;
import com.naveen.bank.auth.mapper.UserMapper;
import com.naveen.bank.auth.producer.AuthenticationEventProducer;
import com.naveen.bank.auth.producer.OtpEventProducer;
import com.naveen.bank.auth.producer.UserEventProducer;
import com.naveen.bank.auth.repository.UserRepository;
import com.naveen.bank.auth.security.CustomUserDetails;
import com.naveen.bank.auth.security.JwtTokenProvider;
import com.naveen.bank.auth.service.AuthService;
import com.naveen.bank.auth.service.RefreshTokenServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.concurrent.ThreadLocalRandom;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    private final JwtTokenProvider jwtTokenProvider;

    private final RefreshTokenServices refreshTokenServices;

    private final AuthenticationManager authenticationManager;

    private final UserEventProducer userEventProducer;

    private final AuthenticationEventProducer authenticationEventProducer;

    private final PasswordEncoder passwordEncoder;

    private final OtpEventProducer otpEventProducer;

    @Override
    public UserResponse register(RegisterRequest request) {

        log.info("Registering User : {}", request.getUsername());

        UserResponse response = userService.createUser(request);

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();

        UserRegisteredEvent event = UserRegisteredEvent.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .mobile(user.getMobile())
                .registeredAt(LocalDateTime.now())
                .build();

        userEventProducer.publishUserRegistered(event);

        log.info("User Registered Successfully");

        return response;
    }

    @Override
    public JwtResponse login(LoginRequest request) {

        log.info("Authenticating User : {}", request.getUsername());

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()));

        if (!authentication.isAuthenticated()) {

            throw new InvalidCredentialsException(
                    "Invalid Username or Password");
        }

        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        String accessToken =
                jwtTokenProvider.generateAccessToken(userDetails);

        RefreshTokenResponse refreshToken =
                refreshTokenServices.createRefreshToken(
                        userRepository.findByUsername(
                                        request.getUsername())
                                .orElseThrow()
                                .getId());

        LoginSuccessEvent event =
                LoginSuccessEvent.builder()
                        .userId(userRepository.findByUsername(
                                        request.getUsername())
                                .orElseThrow()
                                .getId())
                        .username(request.getUsername())
                        .loginTime(LocalDateTime.now())
                        .build();

        authenticationEventProducer.publishLoginSuccess(event);

        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .tokenType("Bearer")
                .build();
    }

    @Override
    public void logout(String refreshToken) {

        log.info("Logout Request Received");

        RefreshToken token =
                refreshTokenServices.findByToken(refreshToken);

        refreshTokenServices.revokeRefreshToken(refreshToken);

        LogoutEvent event = LogoutEvent.builder()
                .userId(token.getUser().getId())
                .username(token.getUser().getUsername())
                .logoutAt(LocalDateTime.now())
                .build();

        authenticationEventProducer.publishLogout(event);

        log.info("User Logged Out Successfully");
    }

    @Override
    public JwtResponse refreshToken(String refreshToken) {

        log.info("Refreshing Access Token");

        RefreshToken token =
                refreshTokenServices.findByToken(refreshToken);

        refreshTokenServices.verifyExpiration(token);

        User user = token.getUser();

        UserDetails userDetails =
                new CustomUserDetails(user);

        String accessToken =
                jwtTokenProvider.generateAccessToken(userDetails);

        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public boolean validateToken(String token) {

        log.info("Validating JWT Token");

        try {

            String username =
                    jwtTokenProvider.extractUsername(token);

            User user =
                    userRepository.findByUsername(username)
                            .orElseThrow(() ->
                                    new UserNotFoundException(
                                            "User Not Found"));

            UserDetails userDetails =
                    new CustomUserDetails(user);

            return jwtTokenProvider
                    .validateToken(token, userDetails);

        } catch (Exception ex) {

            log.error("Invalid JWT Token");

            return false;
        }

    }

    @Override
    public void forgotPassword(String username) {

        log.info("Forgot Password Request : {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User Not Found"));

        String otp = String.valueOf(
                ThreadLocalRandom.current()
                        .nextInt(100000, 999999));

        OtpGeneratedEvent event =
                OtpGeneratedEvent.builder()
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .mobile(user.getMobile())
                        .otp(otp)
                        .generatedAt(LocalDateTime.now())
                        .build();

        otpEventProducer.publishOtpGenerated(event);

        log.info("OTP Generated Successfully");
    }

    @Override
    public void resetPassword(String username,
                              String newPassword) {

        log.info("Reset Password : {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User Not Found"));

        user.setPassword(
                passwordEncoder.encode(newPassword));

        userRepository.save(user);

        PasswordChangedEvent event =
                PasswordChangedEvent.builder()
                        .userId(user.getId())
                        .username(user.getUsername())
                        .changedAt(LocalDateTime.now())
                        .build();

        authenticationEventProducer
                .publishPasswordChanged(event);

        log.info("Password Reset Successfully");
    }

    @Override
    public void changePassword(UUID userId,
                               String oldPassword,
                               String newPassword) {

        log.info("Changing Password : {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User Not Found"));

        if (!passwordEncoder.matches(
                oldPassword,
                user.getPassword())) {

            throw new InvalidCredentialsException(
                    "Old Password Incorrect");
        }

        user.setPassword(
                passwordEncoder.encode(newPassword));

        userRepository.save(user);

        PasswordChangedEvent event =
                PasswordChangedEvent.builder()
                        .userId(user.getId())
                        .username(user.getUsername())
                        .changedAt(LocalDateTime.now())
                        .build();

        authenticationEventProducer
                .publishPasswordChanged(event);

        log.info("Password Changed Successfully");
    }

    @Override
    public boolean verifyOtp(String username,
                             String otp) {

        log.info("Verifying OTP : {}", username);

        // Redis Verification will be implemented later

        OtpVerifiedEvent event =
                OtpVerifiedEvent.builder()
                        .username(username)
                        .verifiedAt(LocalDateTime.now())
                        .build();

        otpEventProducer.publishOtpVerified(event);

        log.info("OTP Verified Successfully");

        return true;
    }

    /**
     * Find User By Username
     */
    private User getUserByUsername(String username) {

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User Not Found : " + username));
    }

    /**
     * Find User By Id
     */
    private User getUserById(UUID userId) {

        return userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User Not Found : " + userId));
    }

    /**
     * Authenticate User
     */
    private Authentication authenticate(String username,
                                        String password) {

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        username,
                        password));
    }

    /**
     * Generate Access Token
     */
    private String generateAccessToken(User user) {

        UserDetails userDetails =
                new CustomUserDetails(user);

        return jwtTokenProvider.generateAccessToken(userDetails);
    }
    /**
     * Generate Refresh Token
     */
    private RefreshTokenResponse generateRefreshToken(User user) {

        return refreshTokenServices.createRefreshToken(user.getId());
    }
    /**
     * Publish User Registered Event
     */
    private void publishUserRegisteredEvent(User user) {

        UserRegisteredEvent event =
                UserRegisteredEvent.builder()
                        .userId(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .mobile(user.getMobile())
                        .registeredAt(LocalDateTime.now())
                        .build();

        userEventProducer.publishUserRegistered(event);
    }

    /**
     * Publish Login Success Event
     */
    private void publishLoginSuccessEvent(User user) {

        LoginSuccessEvent event =
                LoginSuccessEvent.builder()
                        .userId(user.getId())
                        .username(user.getUsername())
                        .loginTime(LocalDateTime.now())
                        .build();

        authenticationEventProducer.publishLoginSuccess(event);
    }

    /**
     * Publish Logout Event
     */
    private void publishLogoutEvent(User user) {

        LogoutEvent event =
                LogoutEvent.builder()
                        .userId(user.getId())
                        .username(user.getUsername())
                        .logoutAt(LocalDateTime.now())
                        .build();

        authenticationEventProducer.publishLogout(event);
    }
    /**
     * Publish Password Changed Event
     */
    private void publishPasswordChangedEvent(User user) {

        PasswordChangedEvent event =
                PasswordChangedEvent.builder()
                        .userId(user.getId())
                        .username(user.getUsername())
                        .changedAt(LocalDateTime.now())
                        .build();

        authenticationEventProducer.publishPasswordChanged(event);
    }

    /**
     * Publish OTP Generated Event
     */
    private void publishOtpGeneratedEvent(User user,
                                          String otp) {

        OtpGeneratedEvent event =
                OtpGeneratedEvent.builder()
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .mobile(user.getMobile())
                        .otp(otp)
                        .generatedAt(LocalDateTime.now())
                        .build();

        otpEventProducer.publishOtpGenerated(event);
    }

    /**
     * Publish OTP Verified Event
     */
    private void publishOtpVerifiedEvent(String username) {

        OtpVerifiedEvent event =
                OtpVerifiedEvent.builder()
                        .username(username)
                        .verifiedAt(LocalDateTime.now())
                        .build();

        otpEventProducer.publishOtpVerified(event);
    }

    /**
     * Encode Password
     */
    private String encodePassword(String password) {

        return passwordEncoder.encode(password);
    }

    /**
     * Validate Password
     */
    private boolean isPasswordValid(String rawPassword,
                                    String encodedPassword) {

        return passwordEncoder.matches(
                rawPassword,
                encodedPassword);
    }
}