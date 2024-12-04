package com.ecommerce.userservice.util;

import lombok.Data;

/**
 * ClassName: LoginRequest
 * Package: com.ecommerce.userservice.util
 * Description: 登录请求对象
 *
 * @Author Shane Liu
 * @Create 2024/12/02 12:35
 * @Version 1.0
 */
@Data
public class LoginRequest {
    private String username;
    private String password;
}
