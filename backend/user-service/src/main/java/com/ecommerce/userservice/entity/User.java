package com.ecommerce.userservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
 * @Version 1.1
 */
@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).*$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, and one number")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email must be a valid format")
    @Column(nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "^(USER|ADMIN)$", message = "Role must be either USER or ADMIN")
    @Column(nullable = false)
    private String role = "USER";

    @Size(max = 512, message = "Refresh Token cannot exceed 512 characters")
    @Column(length = 512)
    private String refreshToken;

    @Size(max = 512, message = "Reset Token cannot exceed 512 characters")
    @Column(length = 512)
    private String resetToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date resetTokenExpiry;

    @Column(nullable = false)
    private LocalDateTime passwordLastUpdated = LocalDateTime.now();
}
