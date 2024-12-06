package com.ecommerce.userservice.service;

import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * ClassName: UserService
 * Package: com.ecommerce.userservice.service
 * Description: 用户管理服务
 *
 * @Author Shane Liu
 * @Create 2024/12/04 17:15
 * @Version 1.0
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 修改用户密码（需要验证旧密码）
     * 此api endpoint 可以修改admin 和 user的密碼
     *
     * @param userId 用户 ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        // 从数据库查找用户
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // 验证旧密码是否正确
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Incorrect old password");
        }

        // 验证新密码是否不同于旧密码
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new IllegalArgumentException("New password must be different from old password");
        }

        // 更新密码并记录密码更新时间
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setPasswordLastUpdated(LocalDateTime.now());

        // 保存用户信息
        userRepository.save(user);
    }

    /**
     * 获取用户信息，通过 JWT 中的用户 ID
     *
     * @param userId JWT 中的用户 ID
     * @return 用户信息
     */
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
    }


    /**
     * 更新当前用户信息
     *
     * @param userId 当前用户的 ID
     * @param updatedUser 更新后的用户数据
     * @return 更新后的用户信息
     */
    public User updateCurrentUser(Long userId, User updatedUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // 更新字段，仅允许修改 username 和 email
        user.setUsername(updatedUser.getUsername() != null && !updatedUser.getUsername().isBlank() ? updatedUser.getUsername() : user.getUsername());
        user.setEmail(updatedUser.getEmail() != null && !updatedUser.getEmail().isBlank() ? updatedUser.getEmail() : user.getEmail());

        // 保存并返回更新后的用户信息
        return userRepository.save(user);
    }



}
