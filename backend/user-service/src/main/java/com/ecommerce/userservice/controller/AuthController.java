package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.dto.ForgotPasswordRequest;
import com.ecommerce.userservice.dto.PasswordResetRequest;
import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.response.ResponseWrapper;
import com.ecommerce.userservice.service.AuthService;
import com.ecommerce.userservice.service.TokenBlacklistService;
import com.ecommerce.userservice.util.JwtUtil;
import com.ecommerce.userservice.util.LoginRequest;
import com.ecommerce.userservice.util.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * AuthController: 认证控制器，处理用户登录和注册。
 *
 * @Author Shane Liu
 * @Create 2024/12/04 16:10
 * @Version 1.1
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Operations related to user authentication")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@Validated
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    /**
     * 忘记密码：发送重置令牌
     */
    @PostMapping("/forgot-password")
    @Operation(summary = "forgot-password", description = "Send an reset token via email to reset password.")
    public ResponseEntity<ResponseWrapper<Void>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        authService.forgotPassword(request.getEmail());
        return ResponseEntity.ok(ResponseUtil.success("Password reset email sent", null));
    }

    /**
     * 重置密码：设置新密码
     */
    @PostMapping("/reset-password")
    @Operation(summary = "Password Reset Request", description = "Initiate password reset by sending an email.")
    public ResponseEntity<ResponseWrapper<Void>> resetPassword(@Valid @RequestBody PasswordResetRequest resetRequest) {
        authService.resetPassword(resetRequest.getToken(), resetRequest.getNewPassword());
        return ResponseEntity.ok(ResponseUtil.success("Password reset successfully", null));
    }

    /**
     * 用户注销
     */
    @PostMapping("/logout")
    @Operation(summary = "logout", description = "Logout is to put the token into blacklist.")
    public ResponseEntity<ResponseWrapper<Void>> logout(@RequestHeader("Authorization") String token) {
        String tokenValue = token.startsWith("Bearer ") ? token.substring(7) : token;

        long expiration = jwtUtil.getTokenExpirationInSeconds(tokenValue);
        tokenBlacklistService.addToBlacklist(tokenValue, expiration);

        logger.info("Token added to blacklist: {}", tokenValue);
        return ResponseEntity.ok(ResponseUtil.success("Logout successful", null));
    }

    /**
     * 刷新 Token
     */
    @PostMapping("/refresh-token")
    @Operation(summary = "Generate token with refresh-token", description = "Generate token with refresh-token.")
    public ResponseEntity<ResponseWrapper<String>> refresh(@RequestHeader("Authorization") String oldRefreshToken) {
        if (oldRefreshToken.startsWith("Bearer ")) {
            oldRefreshToken = oldRefreshToken.substring(7);
        }
        String newAccessToken = authService.refreshToken(oldRefreshToken);
        return ResponseEntity.ok(ResponseUtil.success("Token refreshed successfully", newAccessToken));
    }

    /**
     * 用户注册
     */

    @PostMapping("/register")
    @Operation(summary = "User Registration", description = "Register a new user in the system.")
    public ResponseEntity<ResponseWrapper<String>> register(@RequestBody User user) {
        authService.registerUser(user);

        return ResponseEntity.ok(ResponseUtil.success("User registered successfully", null));
    }


    /**
     * 用户登录
     */
    @CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
    @PostMapping("/login")
    @Operation(summary = "User Login", description = "Authenticate user and return JWT token.")
    public ResponseEntity<ResponseWrapper<Map<String, String>>> login(@Valid @RequestBody LoginRequest loginRequest) {
        Map<String, String> tokens = authService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
        logger.info("User logged in: {}", loginRequest.getUsername());
        return ResponseEntity.ok(ResponseUtil.success("Login successful", tokens));
    }
}
