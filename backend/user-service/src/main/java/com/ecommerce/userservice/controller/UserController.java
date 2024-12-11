package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.dto.ChangePasswordRequest;
import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.response.ResponseWrapper;
import com.ecommerce.userservice.service.UserService;
import com.ecommerce.userservice.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


/**
 * UserController: 用户管理端点
 *
 * @Author Shane Liu
 * @Create 2024/12/04 17:10
 * @Update 2024/12/05 01:00
 * @Version 1.1
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 修改当前用户的密码
     *
     * @param changePasswordRequest 包含旧密码和新密码的请求数据
     * @return 操作结果
     */
    @PostMapping("/change-password")
    public ResponseEntity<ResponseWrapper<Void>> changePassword(
            @RequestBody @Valid ChangePasswordRequest changePasswordRequest) {

        // 确保新密码和确认密码一致
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("New password and confirm password do not match");
        }

        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.changePassword(userId, changePasswordRequest.getOldPassword(), changePasswordRequest.getNewPassword());

        return ResponseEntity.ok(ResponseUtil.success("Password changed successfully", null));
    }


    /**
     * 获取当前用户信息
     *
     * @return 当前用户信息
     */
    @GetMapping("/me")
    public ResponseEntity<ResponseWrapper<User>> getCurrentUser() {
        // 从 SecurityContext 获取当前用户的 ID
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserById(userId);

        return ResponseEntity.ok(ResponseUtil.success("Current user retrieved successfully", user));
    }

    /**
     * 更新用户信息（仅限当前用户）
     *
     * @param updatedUser 更新后的用户数据（仅允许修改用户名和邮箱）
     * @return 更新后的用户信息
     */
    @PutMapping("/updateMe")
    public ResponseEntity<ResponseWrapper<User>> updateCurrentUser(@Valid @RequestBody User updatedUser) {
        // 从 SecurityContext 获取当前用户的 ID
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // 调用服务层更新当前用户的信息
        User user = userService.updateCurrentUser(userId, updatedUser);

        // 返回成功响应
        return ResponseEntity.ok(ResponseUtil.success("User updated successfully", user));
    }


}
