package com.ecommerce.userservice.service;

import com.ecommerce.userservice.dto.PasswordResetRequest;
import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.repository.UserRepository;
import com.ecommerce.userservice.util.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

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
     *
     * @param email 用户邮箱
     */
    public void forgotPassword(String email) {
        email = email.trim().toLowerCase();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email not found"));

        String resetToken = jwtUtil.generateResetToken(user.getId().toString());
        emailService.sendPasswordResetEmail(email, resetToken);

        logger.info("Password reset token sent to email: {}", email);
    }

    /**
     * 重置密码
     *
     * @param resetToken  重置 Token
     * @param newPassword 新密码
     */
    public void resetPassword(String resetToken, String newPassword) {
        // 验证重置 Token
        String userId = jwtUtil.validateResetToken(resetToken);
        User user = userRepository.findById(Long.parseLong(userId))
                .orElseThrow(() -> new IllegalArgumentException("Invalid Reset Token!"));

        // 校验新密码
        validatePasswordStrength(newPassword);

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setRefreshToken(null); // 使旧的 Refresh Token 无效
        userRepository.save(user);

        logger.info("Password successfully reset for user ID: {}", user.getId());
    }

    /**
     * 用户注册
     *
     * @param user 注册的用户信息
     */
    public void registerUser(User user) {
        // 1. 去除前后空格并标准化邮箱为小写
        user.setUsername(user.getUsername().trim());
        user.setEmail(user.getEmail().trim().toLowerCase());
        user.setPassword(user.getPassword().trim());

        // 2. 1 校验用户名长度和格式
        if (user.getUsername().length() < 3 || user.getUsername().length() > 20) {
            throw new IllegalArgumentException("Username must be between 3 and 20 characters");
        }
        if (!user.getUsername().matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException("Username can only contain letters, numbers, and underscores");
        }
        // 2. 2校验邮箱格式
        validateEmailFormat(user.getEmail());

        // 3. 校验用户名和邮箱的唯一性
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists!");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists!");
        }

        // 4. 校验密码强度
        validatePasswordStrength(user.getPassword());

        // 5. 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 6. 设置默认角色为 "USER"
        user.setRole("USER");

        // 7. 保存用户到数据库
        userRepository.save(user);

        // 8. 记录日志
        logger.info("New user registered successfully: {}", user.getUsername());
    }

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 生成的 Access Token 和 Refresh Token
     */
    public Map<String, String> loginUser(String username, String password) {
        username = username.trim();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password!"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password!");
        }

        // 生成 Access Token 和 Refresh Token
        String accessToken = jwtUtil.generateToken(user.getId(), user.getRole());
        String refreshToken = jwtUtil.generateRefreshToken(user.getId());

        // 保存 Refresh Token
        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        logger.info("User logged in successfully: {}", username);

        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);
        return tokens;
    }

    /**
     * 刷新 Access Token
     *
     * @param oldRefreshToken 用户提供的旧 Refresh Token
     * @return 新的 Access Token
     */
    public String refreshToken(String oldRefreshToken) {
        long userId = jwtUtil.validateRefreshToken(oldRefreshToken);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Refresh Token!"));

        if (!oldRefreshToken.equals(user.getRefreshToken())) {
            throw new IllegalArgumentException("Refresh Token mismatch!");
        }

        logger.info("Access token refreshed for user ID: {}", userId);

        return jwtUtil.generateToken(user.getId(), user.getRole());
    }

    /**
     * 校验密码强度
     */
    private void validatePasswordStrength(String password) {
        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        if (!password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!])\\S+$")) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter, one lowercase letter, one number, one special character（@#$%^&+=!）, and no spaces");
        }
    }
    /**
     * 校验邮箱强度
     */
    private void validateEmailFormat(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        if (!email.matches(emailRegex)) {
            throw new IllegalArgumentException("Email format is invalid. Please provide a valid email address.");
        }
    }
}
