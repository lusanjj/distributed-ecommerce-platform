package com.ecommerce.user_service.controller;

import com.ecommerce.user_service.service.AuthService;
import com.ecommerce.user_service.entity.User;
import com.ecommerce.user_service.util.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName: AuthController
 * Package: com.ecommerce.userservice.controller
 * Description: 用户认证控制器，专注于注册和登录等认证功能。
 *
 * @Author Shane Liu
 * @Create 2024/12/02 14:30
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        return ResponseEntity.ok(authService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(token);
    }
}
