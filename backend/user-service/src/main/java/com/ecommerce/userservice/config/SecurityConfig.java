package com.ecommerce.userservice.config;

import com.ecommerce.userservice.filter.JwtAuthenticationFilter;
import com.ecommerce.userservice.exception.CustomAuthenticationEntryPoint;
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
 * @Update 2024/12/06 12:30
 * @Version 1.7
 */
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          CustomAuthenticationEntryPoint customAuthenticationEntryPoint) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
    }

    /**
     * 配置密码加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 配置安全过滤链
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // 开放认证端点，允许所有用户访问
                        .requestMatchers("/api/auth/**").permitAll()
                        // 普通用户和管理员都可以访问自己的信息
                        .requestMatchers("/api/users/**", "/api/users/me", "/api/users/updateMe").hasAnyRole("USER", "ADMIN")
                        // 管理员可以访问所有用户数据和管理员相关的端点
                        .requestMatchers("/api/users/**", "/api/admin/**").hasRole("ADMIN")
                        // 其他所有请求需要认证
                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex
                        // 配置自定义认证入口点，返回更详细的错误信息
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                )
                // 添加 JWT 过滤器
                .addFilterBefore(jwtAuthenticationFilter, org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
