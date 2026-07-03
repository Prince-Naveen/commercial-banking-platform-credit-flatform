package com.naveen.bank.auth.service.impl;

import com.naveen.bank.auth.dto.response.RefreshTokenResponse;
import com.naveen.bank.auth.entity.RefreshToken;
import com.naveen.bank.auth.entity.User;
import com.naveen.bank.auth.exception.RefreshTokenException;
import com.naveen.bank.auth.mapper.RefreshTokenMapper;
import com.naveen.bank.auth.repository.RefreshTokenRepository;
import com.naveen.bank.auth.repository.UserRepository;
import com.naveen.bank.auth.service.RefreshTokenServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenServicesImpl implements RefreshTokenServices {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final RefreshTokenMapper refreshTokenMapper;

    @Value("${jwt.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    @Override
    public RefreshTokenResponse createRefreshToken(UUID userId) {

        log.info("Creating Refresh Token for User : {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RefreshTokenException("User Not Found"));

        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expiryDate(LocalDateTime.now()
                        .plusSeconds(refreshTokenExpiration / 1000))
                .user(user)
                .build();

        RefreshToken savedToken =
                refreshTokenRepository.save(refreshToken);

        log.info("Refresh Token Created Successfully");

        return refreshTokenMapper.toResponse(savedToken);
    }

    @Override
    @Transactional(readOnly = true)
    public RefreshToken findByToken(String token) {

        log.info("Fetching Refresh Token");

        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() ->
                        new RefreshTokenException("Refresh Token Not Found"));
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {

        if (token.getExpiryDate().isBefore(LocalDateTime.now())) {

            refreshTokenRepository.delete(token);

            throw new RefreshTokenException("Refresh Token Expired");
        }

        return token;
    }

    @Override
    public void deleteByUser(UUID userId) {

        log.info("Deleting Refresh Token for User : {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RefreshTokenException("User Not Found"));

        refreshTokenRepository.deleteByUser(user);

        log.info("Refresh Token Deleted Successfully");
    }

    @Override
    public void revokeRefreshToken(String token) {

        log.info("Revoking Refresh Token");

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() ->
                        new RefreshTokenException("Refresh Token Not Found"));

        refreshTokenRepository.delete(refreshToken);

        log.info("Refresh Token Revoked Successfully");
    }

}