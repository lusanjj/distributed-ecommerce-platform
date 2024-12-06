package com.ecommerce.userservice.filter;

import com.ecommerce.userservice.util.JwtUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * JwtAuthenticationFilter: 自定义过滤器，用于验证 JWT
 *
 * @Author Shane Liu
 * @Create 2024/12/04 21:00
 * @Update 2024/12/05 00:50
 * @Version 1.5
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String requestUri = request.getRequestURI();

        // 如果请求路径属于公开端点，则跳过过滤
        if (requestUri.startsWith("/api/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                // 验证 Token 并获取用户 ID
                Long userId = jwtUtil.validateToken(token);

                // 从 Token 中获取角色
                String role = jwtUtil.getClaims(token).get("role", String.class);

                // 创建 Spring Security 的认证对象
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userId, null, Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role))
                );

                // 设置 SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // 处理无效 Token 的情况
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid JWT Token");
                return;
            }
        }

        // 继续过滤链
        filterChain.doFilter(request, response);
    }
}