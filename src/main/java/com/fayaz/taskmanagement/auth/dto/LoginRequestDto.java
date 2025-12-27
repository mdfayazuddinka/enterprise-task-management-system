package com.fayaz.taskmanagement.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDto {
    @NotBlank
    private String identifier;

    @NotBlank
    @Size(min = 8)
    private String password;
}
