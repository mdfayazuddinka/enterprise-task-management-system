package com.fayaz.taskmanagement.auth.dto;

import com.fayaz.taskmanagement.auth.RoleEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SignUpResponseDto {
    private String userName;
    private String email;
    private RoleEnum role;
    private String userId;
}

