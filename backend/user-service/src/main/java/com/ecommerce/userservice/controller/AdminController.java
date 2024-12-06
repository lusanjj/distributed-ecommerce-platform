package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.response.ResponseWrapper;
import com.ecommerce.userservice.service.AdminService;
import com.ecommerce.userservice.util.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName: AdminController
 * Package:com.ecommerce.userservice.controller
 * Description:
 *
 * @Author Shane Liu
 * @Create 2024/12/5 12:54
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 获取当前登录管理員用户信息
     *
     * @return 当前管理員信息
     */
    @GetMapping("/me")
    public ResponseEntity<ResponseWrapper<User>> getCurrentUser() {
        // 从 SecurityContext 获取当前用户的 ID
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = adminService.getUserById(userId);

        return ResponseEntity.ok(ResponseUtil.success("Current user retrieved successfully", user));
    }

    /**
     * 创建管理员
     *
     * @param adminUser 注册管理员的请求数据
     * @return 创建结果
     */
    @PostMapping("/create-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseWrapper<User>> createAdmin(@RequestBody @Valid User adminUser) {
        User newAdmin = adminService.createAdmin(adminUser);
        return ResponseEntity.ok(ResponseUtil.success("Admin created successfully", newAdmin));
    }
    /**
     * 获取所有用户（管理員和普通用戶）
     *
     * @return 所有用户列表
     */
    @GetMapping("/users")
    public ResponseEntity<ResponseWrapper<List<User>>> getAllUsers() {
        List<User> users = adminService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.ok(ResponseUtil.success("No users found", users));
        }
        return ResponseEntity.ok(ResponseUtil.success("Users retrieved successfully", users));
    }

    /**
     * 根据用户 ID 获取用户信息
     *
     * @param id 用户 ID
     * @return 用户信息
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<ResponseWrapper<User>> getUserById(@PathVariable Long id) {
        User user = adminService.getUserById(id);
        return ResponseEntity.ok(ResponseUtil.success("User retrieved successfully", user));
    }


    /**
     * 更新任何用户信息
     *
     * @param id 用户 ID
     * @param updatedUser 更新后的用户数据
     * @return 更新后的用户信息
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<ResponseWrapper<User>> updateUser(
            @PathVariable Long id, @Valid @RequestBody User updatedUser) {
        User user = adminService.updateUser(id, updatedUser);
        return ResponseEntity.ok(ResponseUtil.success("User updated successfully", user));
    }

    /**
     * 删除任意用户
     *
     * @param id 用户 ID
     * @return 删除结果
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.ok(ResponseUtil.success("User deleted successfully", null));
    }
}
