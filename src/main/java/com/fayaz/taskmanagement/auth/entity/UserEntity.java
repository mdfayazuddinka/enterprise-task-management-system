package com.fayaz.taskmanagement.auth.entity;

import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fayaz.taskmanagement.auth.RoleEnum;
import com.fayaz.taskmanagement.utils.AuditDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class UserEntity {

    @Indexed(unique = true)
    private String userId;

    @Indexed(unique = true)
    private String email;

    private String password;

    @Indexed(unique = true)
    private String userName;

    private List<RoleEnum> role;

    private AuditDto audit;
}