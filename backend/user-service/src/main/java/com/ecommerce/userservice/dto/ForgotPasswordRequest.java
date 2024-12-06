package com.ecommerce.userservice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
