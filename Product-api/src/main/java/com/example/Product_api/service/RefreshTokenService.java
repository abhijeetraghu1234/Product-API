package com.example.Product_api.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Product_api.entity.RefreshToken;
import com.example.Product_api.entity.User;
import com.example.Product_api.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private final int refreshTokenDurationDays = 7; // Token valid for 7 days

    // Create a new refresh token
    public RefreshToken createRefreshToken(User user) {
        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plus(refreshTokenDurationDays, ChronoUnit.DAYS));
        return refreshTokenRepository.save(token);
    }

    // Verify token validity
    public Optional<RefreshToken> verifyRefreshToken(String token) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByToken(token);
        if (refreshToken.isPresent() && refreshToken.get().getExpiryDate().isAfter(Instant.now())) {
            return refreshToken;
        } else {
            refreshToken.ifPresent(refreshTokenRepository::delete);
            return Optional.empty();
        }
    }

    // Delete token explicitly
    public void deleteToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

    public void deleteToken(RefreshToken validRefreshToken) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteToken'");
    }

}