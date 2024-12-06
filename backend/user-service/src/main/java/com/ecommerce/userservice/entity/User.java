package com.ecommerce.userservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * ClassName: User
 * Package: com.ecommerce.userservice.entity
 * Description: 用户实体类，存储用户信息
 *
 * @Author Shane Liu
 * @Create 2024/12/02 12:15
 * @Version 1.0
 */
@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    // 用户角色：USER / ADMIN
    private String role;

    // 用于存储用户的 Refresh Token，长度限制为 512 字符
    @Column(length = 512)
    private String refreshToken;

    // 新增字段：重置令牌
    private String resetToken;

    // 新增字段：令牌过期时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date resetTokenExpiry;

    // 存储密码的最后更新时间，用于检查 Token 有效性
    private LocalDateTime passwordLastUpdated;


}
