package com.ecommerce.userservice.service;

import org.springframework.stereotype.Service;

/**
 * EmailService: 邮件服务
 *
 * @Author Shane Liu
 * @Create 2024/12/05
 * @Version 1.0
 */
@Service
public class EmailService {
//    TODO: 需要使用郵件服務，把reset Token 發到請求找回密碼用戶的郵箱中。
    /**
     * 打印密码重置 Token 到控制台
     * @param email 用户的邮箱地址
     * @param resetToken 重置 Token
     */
    public void sendPasswordResetEmail(String email, String resetToken) {
        String resetLink = "https://example.com/reset-password?token=" + resetToken;

        // 输出重置链接到控制台
        System.out.println("----- Reset Password Token -----");
        System.out.println("Email: " + email);
        System.out.println("Reset Token: " + resetToken);
        System.out.println("Reset Link: " + resetLink);
        System.out.println("--------------------------------");
    }
}
