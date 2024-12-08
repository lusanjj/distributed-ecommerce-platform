package com.ecommerce.userservice.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JwtUtil: JWT 工具类，负责生成和验证 Token
 *
 * @Author Shane Liu
 * @Create 2024/12/02 12:50
 * @Update 2024/12/04 22:30
 * @Version 1.2
 */
@Component
public class JwtUtil {

    private final SecretKey secretKey;

    // Access Token 有效期
    @Value("${jwt.expiration}")
    private long expiration;

    // Refresh Token 有效期
    @Value("${jwt.refreshExpiration}")
    private long refreshExpiration;

    // Reset Token 有效期
    @Value("${jwt.resetTokenExpiration}")
    private long resetTokenExpiration;


    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 获取 Token 的剩余过期时间（以秒为单位）
     *
     * @param token JWT Token
     * @return 剩余的过期时间（秒）
     * @throws IllegalArgumentException 如果 Token 无效或过期
     */
    public long getTokenExpirationInSeconds(String token) {
        try {
            // 使用解析器验证并解析 Token 的 Claims（有效负载）
            Claims claims = Jwts.parserBuilder()
                    // 设置签名密钥
                    .setSigningKey(secretKey)
                    .build()
                    // 解析 Token
                    .parseClaimsJws(token)
                    .getBody();

            // 获取 Token 的过期时间
            Date expirationDate = claims.getExpiration();
            // 当前时间
            long currentTimeMillis = System.currentTimeMillis();

            // 计算剩余时间（过期时间 - 当前时间），单位为秒
            return (expirationDate.getTime() - currentTimeMillis) / 1000;
        } catch (JwtException | IllegalArgumentException e) {
            // 捕获解析异常并抛出明确的错误信息
            throw new IllegalArgumentException("Invalid Token! Unable to get expiration time.");
        }
    }

    /**
     * 生成重置密码的 Token
     * @param userId 用户的 ID
     * @return 重置密码 Token
     */
    public String generateResetToken(String userId) {
        return Jwts.builder()
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + resetTokenExpiration))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * 验证 Reset Token 并返回用户 ID
     *
     * @param resetToken 重置 Token
     * @return 用户 ID
     */
    public String validateResetToken(String resetToken) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(resetToken)
                    .getBody();
            return claims.getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Reset Token!");
        }
    }

    /**
     * 生成 Access Token
     *
     * @param userId 用户唯一id
     * @param role     用户角色
     * @return Access Token
     */
    public String generateToken(Long userId, String role) {
        return Jwts.builder()
                // 使用用户 ID 作为主体
                .setSubject(String.valueOf(userId))
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * 验证 Access Token 并返回用户 ID
     *
     * @param token Access Token
     * @return 用户 ID
     */
    public Long validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return Long.valueOf(claims.getSubject());
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Access Token!");
        }
    }

    /**
     * 生成 Refresh Token
     *
     * @param userId 用户 ID
     * @return Refresh Token
     */
    public String generateRefreshToken(Long userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpiration))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * 验证 Refresh Token 并返回用户 ID
     *
     * @param token Refresh Token
     * @return 用户 ID
     */
    public Long validateRefreshToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return Long.valueOf(claims.getSubject());
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Refresh Token!");
        }
    }


    /**
     * 获取 Token 的 Claims
     *
     * @param token JWT
     * @return Token 的 Claims
     */
    public Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException | IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Token!");
        }
    }


}
