package com.korit.basic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.korit.basic.entity.UserEntity;

// User 테이블에 접근할 리포지토리
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    // SELECT * FROM user WHERE user_id = ?;
    UserEntity findByUserId(String userId);

    // SELECT * FROM user WHERE user_tel_number = ?;
    UserEntity findByUserTelNumber(String userTelNumber);

    boolean existsByUserId(String userId);
    boolean existsByUserTelNumber(String userTelNumber);
}
