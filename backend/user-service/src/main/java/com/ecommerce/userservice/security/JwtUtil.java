package com.ecommerce.user_service.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

/**
 * JwtUtil: JWT 工具类，用于生成和验证令牌。
 *
 * @Author Shane Liu
 * @Create 2024/12/04 12:10
 * @Version 1.0
 */
@Component
public class JwtUtil {

    private final byte[] secret;

    @Value("${jwt.expiration}")
    private long expiration;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        if (Base64.getDecoder().decode(secret).length < 64) {
            throw new IllegalArgumentException("The secret key must be at least 64 bytes long for HS512.");
        }
        this.secret = Base64.getDecoder().decode(secret);
    }

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
