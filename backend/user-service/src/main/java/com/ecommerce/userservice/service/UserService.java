package com.ecommerce.userservice.service;

import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    /**
     * 获取用户信息，通过 JWT 中的用户 ID
     *
     * @param userId JWT 中的用户 ID
     * @return 用户信息
     */
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
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
