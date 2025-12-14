package com.radio.cast.basicFunction.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.radio.cast.basicFunction.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findUserName(String name);

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
    Boolean exexistsByEmail(String email);
} 
