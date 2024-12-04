package com.ecommerce.user_service.repository;

import com.ecommerce.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * ClassName: UserRepository
 * Package: com.ecommerce.userservice.repository
 * Description: 用户数据访问层
 *
 * @Author Shane Liu
 * @Create 2024/12/02 12:20
 * @Version 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
