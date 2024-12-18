package com.ecommerce.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * PasswordResetRequest: 重置密码请求数据结构
 *
 * @Author Shane Liu
 * @Create 2024/12/05
 * @Version 1.0
 */
@Setter
@Getter
public class PasswordResetRequest {

    // Getters and Setters
    @NotBlank(message = "Reset token must not be blank")
    private String token;

    @NotBlank(message = "New password must not be blank")
    private String newPassword;

}
