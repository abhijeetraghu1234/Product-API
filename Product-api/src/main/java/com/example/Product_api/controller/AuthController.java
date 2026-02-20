package com.example.Product_api.controller;

import com.example.Product_api.entity.User;
import com.example.Product_api.entity.RefreshToken;
import com.example.Product_api.repository.UserRepository;
import com.example.Product_api.security.JwtUtil;
import com.example.Product_api.service.RefreshTokenService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil,
                          RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.refreshTokenService = refreshTokenService;
    }

    // LOGIN endpoint
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        if (username == null || password == null) {
            throw new RuntimeException("Username and password required");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // Generate tokens
        String accessToken = jwtUtil.generateToken(user.getUsername(), user.getRole());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        // Prepare response
        Map<String, String> response = new HashMap<>();
        response.put("accessToken", accessToken);
        response.put("refreshToken", refreshToken.getToken());

        return ResponseEntity.ok(response);
    }

    // REFRESH TOKEN endpoint
    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody Map<String, String> request) {
        String requestToken = request.get("refreshToken");

        if (requestToken == null || requestToken.trim().isEmpty()) {
            throw new RuntimeException("Refresh token required");
        }


        RefreshToken validRefreshToken = refreshTokenService.verifyRefreshToken(requestToken.trim())
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

    
        refreshTokenService.deleteToken(validRefreshToken);

    
        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(validRefreshToken.getUser());

        
        User user = validRefreshToken.getUser();
        String newAccessToken = jwtUtil.generateToken(user.getUsername(), user.getRole());

        
        Map<String, String> response = new HashMap<>();
        response.put("accessToken", newAccessToken);
        response.put("refreshToken", newRefreshToken.getToken());

        return ResponseEntity.ok(response);
    }
}
