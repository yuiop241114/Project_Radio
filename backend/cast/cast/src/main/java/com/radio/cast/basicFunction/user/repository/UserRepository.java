package com.radio.cast.basicFunction.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.radio.cast.basicFunction.user.dto.SignUpResponse;
import com.radio.cast.basicFunction.user.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findByUsername(String name);

    Optional<User> findByEmail(String email);

    /**
     * 아이디 중복 확인 Repository
     * @param username
     * @return
     */
    Boolean existsByUsername(String username);

    /**
     * 이메일 중복 확인 Repository
     * @param email
     * @return
     */
    Boolean existsByEmail(String email);
} 
