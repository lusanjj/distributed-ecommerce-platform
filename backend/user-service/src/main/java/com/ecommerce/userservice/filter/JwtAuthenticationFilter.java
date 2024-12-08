/**
 * JwtAuthenticationFilter: 用于验证 JWT Token 的过滤器
 *
 * @Author Shane Liu
 * @Create 2024/12/06 15:20
 * @Version 1.1
 */
package com.ecommerce.userservice.filter;

import com.ecommerce.userservice.service.TokenBlacklistService;
import com.ecommerce.userservice.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final TokenBlacklistService tokenBlacklistService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, TokenBlacklistService tokenBlacklistService) {
        this.jwtUtil = jwtUtil;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                // 检查 Token 是否在黑名单中
                if (tokenBlacklistService.isTokenBlacklisted(token)) {
                    throw new IllegalArgumentException("Token is blacklisted");
                }

                // 验证 Token 并获取用户 ID
                Long userId = jwtUtil.validateToken(token);
                String role = jwtUtil.getClaims(token).get("role", String.class);

                // 创建认证对象并设置 SecurityContext
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userId, null, Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role))
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // 如果 Token 无效或在黑名单中，返回 401
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or blacklisted JWT Token");
                return;
            }
        }

        // 继续过滤链
        filterChain.doFilter(request, response);
    }
}
