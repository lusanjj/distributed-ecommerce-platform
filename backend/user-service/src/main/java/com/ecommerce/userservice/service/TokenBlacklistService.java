package com.ecommerce.userservice.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * TokenBlacklistService: 处理 Token 黑名单相关的业务逻辑。
 *
 * @Author Shane Liu
 * @Create 2024/12/06 13:00
 * @Version 1.1
 */
@Service
public class TokenBlacklistService {

    private final StringRedisTemplate redisTemplate;

    public TokenBlacklistService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 添加 Token 到黑名单
     *  description:设置 Redis Key 的自动过期时间： 确保黑名单中的 Token 在其到期后自动删除，减少 Redis 存储的压力。
     * @param token 需要加入黑名单的 Token
     * @param expirationSeconds Token 剩余的有效时间（秒）
     */
    public void addToBlacklist(String token, long expirationSeconds) {
        String key = "blacklist:" + token;
        redisTemplate.opsForValue().set(key, "blacklisted", expirationSeconds, TimeUnit.SECONDS);
    }

    /**
     * 检查 Token 是否在黑名单中
     *
     * @param token 需要检查的 Token
     * @return 如果 Token 在黑名单中返回 true，否则返回 false
     */
    public boolean isTokenBlacklisted(String token) {
        String key = "blacklist:" + token;
        return redisTemplate.hasKey(key);
    }
}
