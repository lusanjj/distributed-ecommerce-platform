package com.ecommerce.userservice.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * ForgotPasswordRequest: 用于封装忘记密码的请求
 */
public class ForgotPasswordRequest {

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Invalid email format")
    private String email;

    // Getter 和 Setter
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
