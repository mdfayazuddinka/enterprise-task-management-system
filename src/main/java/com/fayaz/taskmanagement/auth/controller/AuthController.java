package com.fayaz.taskmanagement.auth.controller;

import com.fayaz.taskmanagement.auth.dto.AuthResponseDto;
import com.fayaz.taskmanagement.auth.dto.SignUpRequestDto;
import com.fayaz.taskmanagement.auth.dto.LoginRequestDto;
import com.fayaz.taskmanagement.auth.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(
            @Valid @RequestBody SignUpRequestDto request) {

        authService.signup(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@Valid @RequestBody LoginRequestDto request, HttpServletResponse response) {
        return ResponseEntity.ok(authService.login(request, response));
    }
}
