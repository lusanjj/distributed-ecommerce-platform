package com.ecommerce.user_service.entity;

import jakarta.persistence.*;
import lombok.Data;

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

    private String role; // 用户角色：USER / ADMIN
}
