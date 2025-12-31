package com.fayaz.taskmanagement.CustomException;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ErrorResponseDto {
    private String message;
    private int status;
    private LocalDateTime timestamp;

    public ErrorResponseDto(String message, int status) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    // getters and setters
}

