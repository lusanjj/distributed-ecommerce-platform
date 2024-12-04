package com.ecommerce.userservice.repository;

import com.ecommerce.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * UserRepository: 用户数据访问层。
 *
 * @Author Shane Liu
 * @Create 2024/12/04 15:20
 * @Version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email); // 检查邮箱是否存在
}
