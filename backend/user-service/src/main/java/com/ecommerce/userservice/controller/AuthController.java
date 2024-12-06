package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.dto.ForgotPasswordRequest;
import com.ecommerce.userservice.dto.PasswordResetRequest;
import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.response.ResponseWrapper;
import com.ecommerce.userservice.service.AuthService;
import com.ecommerce.userservice.util.LoginRequest;
import com.ecommerce.userservice.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


import java.util.Map;

/**
 * AuthController: 认证控制器，处理用户登录和注册。
 *
 * @Author Shane Liu
 * @Create 2024/12/04 16:10
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 忘记密码：发送重置令牌
     *
     * @param request 用户邮箱
     * @return 成功响应
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseWrapper<Void>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        authService.forgotPassword(request.getEmail());
        return ResponseEntity.ok(ResponseUtil.success("Password reset email sent", null));
    }


    /**
     * 重置密码：设置新密码
     *
     * @param resetRequest 包含重置令牌和新密码
     * @return 成功响应
     */
    @PostMapping("/reset-password")
    public ResponseEntity<ResponseWrapper<Void>> resetPassword(@Valid @RequestBody PasswordResetRequest resetRequest) {
        // 调用 AuthService 的 resetPassword 方法，使用 resetRequest 的字段值
        authService.resetPassword(resetRequest.getToken(), resetRequest.getNewPassword());
        return ResponseEntity.ok(ResponseUtil.success("Password reset successfully", null));
    }


    /**
     * 用户注销
     *
     * @return 注销成功响应
     */
    @PostMapping("/logout")
    public ResponseEntity<ResponseWrapper<Void>> logout() {
        // 从 SecurityContext 获取当前用户 ID
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 调用服务层逻辑清除用户的 Refresh Token
        authService.logout(userId);

        return ResponseEntity.ok(ResponseUtil.success("User logged out successfully", null));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseWrapper<String>> refresh(@RequestHeader("Authorization") String oldRefreshToken) {
        if (oldRefreshToken.startsWith("Bearer ")) {
            oldRefreshToken = oldRefreshToken.substring(7);
        }
        String newAccessToken = authService.refreshToken(oldRefreshToken);
        return ResponseEntity.ok(ResponseUtil.success("Token refreshed successfully", newAccessToken));
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseWrapper<String>> register(@RequestBody User user) {
        authService.registerUser(user);
        return ResponseEntity.ok(ResponseUtil.success("User registered successfully", null));
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseWrapper<Map<String, String>>> login(@RequestBody LoginRequest loginRequest) {
        Map<String, String> tokens = authService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(ResponseUtil.success("Login successful", tokens));
    }

}
