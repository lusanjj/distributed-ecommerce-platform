package com.ecommerce.userservice.service;

import com.ecommerce.userservice.dto.PasswordResetRequest;
import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.repository.UserRepository;
import com.ecommerce.userservice.util.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.util.UUID;


/**
 * ClassName: AuthService
 * Package: com.ecommerce.userservice.service
 * Description: 用户认证服务，负责注册、登录和刷新 Token
 *
 * @Author Shane Liu
 * @Create 2024/12/02 12:45
 * @Version 1.1
 */
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 忘记密码
     * @param email 用户邮箱
     */
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email not found"));

        String resetToken = jwtUtil.generateResetToken(user.getId().toString());

        emailService.sendPasswordResetEmail(email, resetToken);
    }


    /**
     * 重置密码
     * @param resetToken 重置 Token
     * @param newPassword 新密码
     */
    public void resetPassword(String resetToken, String newPassword) {
        String userId = jwtUtil.validateResetToken(resetToken);

        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new IllegalArgumentException("Invalid Reset Token!"));

        user.setPassword(passwordEncoder.encode(newPassword));
        // 使旧的 Refresh Token 无效
        user.setRefreshToken(null);
        userRepository.save(user);
    }

    /**
     * 用户注销
     *
     * @param userId 当前用户的 ID
     */
    public void logout(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // 清除用户的 Refresh Token
        user.setRefreshToken(null);
        userRepository.save(user);
    }

    /**
     * 刷新 Access Token
     *
     * @param oldRefreshToken 用户提供的旧 Refresh Token
     * @return 新的 Access Token
     */
    public String refreshToken(String oldRefreshToken) {
        // 验证并解析 Refresh Token

            // 使用用户 ID 验证 Refresh Token
        long userId = jwtUtil.validateRefreshToken(oldRefreshToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Refresh Token!"));

        // 检查数据库中是否存储了此 Refresh Token（可选：存储 Refresh Token 的机制）
        if (!oldRefreshToken.equals(user.getRefreshToken())) {
            throw new IllegalArgumentException("Invalid Refresh Token!");
        }

        // 返回新的 Access Token
            // 使用用户 ID 和角色生成新的 Token
        return jwtUtil.generateToken(user.getId(), user.getRole());
    }


    /**
     * 用户注册
     *
     * @param user 注册的用户信息
     */
    public void registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists!");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists!");
        }
        // 对密码进行加密存储
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 默认设置为普通用户
        user.setRole("USER");
        userRepository.save(user);
    }

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 生成的 Access Token 和 Refresh Token
     */
    public Map<String, String> loginUser(String username, String password) {
        // 查找用户
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password!"));

        // 验证密码
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password!");
        }

        // 生成 Access Token 和 Refresh Token
        String accessToken = jwtUtil.generateToken(user.getId(), user.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId());

        // 将 Refresh Token 保存到数据库
        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        // 返回 Access Token 和 Refresh Token
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        return tokens;
    }




}
