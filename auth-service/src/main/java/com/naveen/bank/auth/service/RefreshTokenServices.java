package com.naveen.bank.auth.service;

import com.naveen.bank.auth.dto.response.RefreshTokenResponse;
import com.naveen.bank.auth.entity.RefreshToken;

import java.util.UUID;

public interface RefreshTokenServices {

    /**
     * Create Refresh Token
     */
    RefreshTokenResponse createRefreshToken(UUID userId);

    /**
     * Find Refresh Token by Token
     */
    RefreshToken findByToken(String token);

    /**
     * Verify Refresh Token Expiration
     */
    RefreshToken verifyExpiration(RefreshToken refreshToken);

    /**
     * Delete Refresh Token by User
     */
    void deleteByUser(UUID userId);

    /**
     * Revoke Refresh Token
     */
    void revokeRefreshToken(String token);

}