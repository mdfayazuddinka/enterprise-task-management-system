package com.fayaz.taskmanagement.auth.entity;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fayaz.taskmanagement.auth.RoleEnum;

import lombok.Builder;
import lombok.Data;

@Document(collection = "user")
@Data
@Builder
public class UserEntity {
    @Id
    private String id;

    @Indexed(unique = true)
    private String userId;

    @Indexed(unique = true)
    private String email;

    private String password;

    @Indexed(unique = true)
    private String userName;

    private RoleEnum role;
    private Instant createdAt;
}