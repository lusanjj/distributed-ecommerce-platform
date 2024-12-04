package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.entity.User;
import com.ecommerce.userservice.response.ResponseWrapper;
import com.ecommerce.userservice.service.AuthService;
import com.ecommerce.userservice.util.LoginRequest;
import com.ecommerce.userservice.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/register")
    public ResponseEntity<ResponseWrapper<String>> register(@RequestBody User user) {
        authService.registerUser(user);
        return ResponseEntity.ok(ResponseUtil.success("User registered successfully", null));
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseWrapper<String>> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(ResponseUtil.success("Login successful", token));
    }
}
