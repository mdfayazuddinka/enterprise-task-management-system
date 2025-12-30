package com.fayaz.taskmanagement.auth.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

import com.fayaz.taskmanagement.auth.RoleEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class SignUpRequestDto {

   @NotBlank
   @Size(min = 4, message = "User name must be at least 4 characters")
   private String userName;

   @Email
   @NotBlank
   private String email;

   @NotBlank
   @Size(min = 8)
   private String password;

   private List<RoleEnum> role;
}
