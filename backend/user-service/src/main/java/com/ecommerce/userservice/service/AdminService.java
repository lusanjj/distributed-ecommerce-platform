package com.ecommerce.userservice.service;

import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: AdminService
 * Package:com.ecommerce.userservice.service
 * Description:
 *
 * @Author Shane Liu
 * @Create 2024/12/5 12:59
 * @Version 1.0
 */
@Service
public class AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 创建管理员用户
     *
     * @param adminUser 注册的管理员用户信息
     * @return 创建成功的管理员账户信息
     */
    public User createAdmin(User adminUser) {
        // 验证用户名是否已存在
        if (userRepository.findByUsername(adminUser.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists!");
        }
        // 验证邮箱是否已存在
        if (userRepository.findByEmail(adminUser.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists!");
        }
        // 对密码进行加密存储
        adminUser.setPassword(passwordEncoder.encode(adminUser.getPassword()));
        // 设置管理员角色
        adminUser.setRole("ADMIN");
        return userRepository.save(adminUser);
    }
    /**
     * 获取所有用户
     *
     * @return 用户列表
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * 根据 ID 获取用户
     *
     * @param id 用户 ID
     * @return 用户信息
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
    }

    /**
     * 更新用户信息
     *
     * @param userId 用户 ID
     * @param updatedUser 更新后的用户信息
     * @return 更新后的用户信息
     */
    public User updateUser(Long userId, User updatedUser) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // 更新字段，保留旧值如果未提供新值
        user.setUsername(updatedUser.getUsername() != null && !updatedUser.getUsername().isBlank() ? updatedUser.getUsername() : user.getUsername());
        user.setEmail(updatedUser.getEmail() != null && !updatedUser.getEmail().isBlank() ? updatedUser.getEmail() : user.getEmail());
        user.setRole(updatedUser.getRole() != null && !updatedUser.getRole().isBlank() ? updatedUser.getRole() : user.getRole());
        user.setPassword(updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank() ? passwordEncoder.encode(updatedUser.getPassword()) : user.getPassword());

        // 保存并返回更新后的用户信息
        return userRepository.save(user);
    }

    /**
     * 删除用户
     *
     * @param userId 用户 ID
     */
    public void deleteUser(Long userId) {
        // 获取当前登录用户的 ID
        Long currentUserId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 防止管理员删除自己
        if (userId.equals(currentUserId)) {
            throw new IllegalArgumentException("You cannot delete your own account!");
        }

        // 检查用户是否存在
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        // 清除用户的 Refresh Token（防止用户刷新 Token）
        user.setRefreshToken(null);
        userRepository.save(user);

        // 删除用户
        userRepository.deleteById(userId);
    }

}
