package com.ecommerce.userservice.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * PasswordResetRequest: 重置密码请求数据结构
 *
 * @Author Shane Liu
 * @Create 2024/12/05
 * @Version 1.0
 */
public class PasswordResetRequest {

    @NotBlank(message = "Reset token must not be blank")
    private String token;

    @NotBlank(message = "New password must not be blank")
    private String newPassword;

    // Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
