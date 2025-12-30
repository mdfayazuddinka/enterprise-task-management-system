package com.fayaz.taskmanagement.auth.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.fayaz.taskmanagement.auth.entity.UserEntity;;;

public interface UserRepository extends MongoRepository<UserEntity, String> {
    Optional<UserEntity> findByUserName(String userName);
    boolean existsByUserName(String userName);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUserNameOrEmail(String userName, String email);
    boolean existsByEmail(String email);
    boolean existsByUserId(String userId);
    Optional<UserEntity> findByUserId(String userId);
}