package com.ecommerce.userservice.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

/**
 * ForgotPasswordRequest: 用于封装忘记密码的请求
 * @author Shane
 */
@Setter
@Getter
public class ForgotPasswordRequest {

    // Getter 和 Setter
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Invalid email format")
    private String email;

}
