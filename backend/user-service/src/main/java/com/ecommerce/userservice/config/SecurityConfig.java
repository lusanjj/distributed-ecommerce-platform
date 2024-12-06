package com.ecommerce.userservice.config;

import com.ecommerce.userservice.filter.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig: 配置 Spring Security 的安全规则
 *
 * @Author Shane Liu
 * @Create 2024/12/02 16:50
 * @Update 2024/12/05 01:50
 * @Version 1.6
 */
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // 开放认证端点，允许所有用户访问
                        .requestMatchers("/api/auth/**").permitAll()
                        // 普通用户和管理员都可以访问自己的信息
                        .requestMatchers("/api/users/**").hasAnyRole("USER","ADMIN")
                        // 管理员可以访问所有用户信息和管理员信息
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        // 其他所有请求需要认证
                        .anyRequest().authenticated()
                )

                // 添加 JWT 过滤器
                .addFilterBefore(jwtAuthenticationFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
