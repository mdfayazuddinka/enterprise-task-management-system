package com.fayaz.auth.repository;

import java.util.Optional;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.fayaz.auth.entity.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity, String> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
}