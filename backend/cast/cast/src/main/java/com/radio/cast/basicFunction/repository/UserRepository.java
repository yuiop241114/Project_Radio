package com.radio.cast.basicFunction.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.radio.cast.basicFunction.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findUserName(String name);
} 
