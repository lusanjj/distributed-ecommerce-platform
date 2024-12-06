package com.ecommerce.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * ChangePasswordRequest: 修改密码请求的 DTO
 *
 * @Author Shane Liu
 * @Create 2024/12/06
 */
@Setter
@Getter
public class ChangePasswordRequest {

    // Getters and Setters
    @NotBlank(message = "Old password must not be blank")
    private String oldPassword;

    @NotBlank(message = "New password must not be blank")
    private String newPassword;

    @NotBlank(message = "Confirm password must not be blank")
    private String confirmPassword;

}
