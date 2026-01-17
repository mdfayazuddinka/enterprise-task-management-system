package com.fayaz.taskmanagement.auth.entity;

import java.util.List;

import com.fayaz.taskmanagement.auth.RoleEnum;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OwnerEntity {
    private String userId;
    private String userName;
    private String email;
    private List<RoleEnum> roles;
}
