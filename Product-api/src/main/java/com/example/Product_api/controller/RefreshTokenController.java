package com.example.Product_api.controller;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.Product_api.entity.RefreshToken;
import com.example.Product_api.entity.User;
import com.example.Product_api.service.RefreshTokenService;

@RestController
@RequestMapping("/api/refresh-token")
public class RefreshTokenController {

    @Autowired
    private RefreshTokenService refreshTokenService;

    // Create a new refresh token for a user
    @PostMapping("/create")
    public ResponseEntity<RefreshToken> createToken(@RequestBody User user) {
        RefreshToken token = refreshTokenService.createRefreshToken(user);
        return ResponseEntity.ok(token);
    }

    // Verify a refresh token
    @GetMapping("/verify/{token}")
    public ResponseEntity<String> verifyToken(@PathVariable String token) {
        Optional<RefreshToken> refreshToken = refreshTokenService.verifyRefreshToken(token);
        if (refreshToken.isPresent()) {
            return ResponseEntity.ok("Token is valid!");
        } else {
            return ResponseEntity.badRequest().body("Token expired or invalid!");
        }
    }

    // Delete a refresh token
    @DeleteMapping("/delete/{token}")
    public ResponseEntity<String> deleteToken(@PathVariable String token) {
        refreshTokenService.deleteToken(token);
        return ResponseEntity.ok("Token deleted successfully!");
    }
}