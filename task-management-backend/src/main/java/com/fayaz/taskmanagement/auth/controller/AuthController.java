package com.fayaz.taskmanagement.auth.controller;

import com.fayaz.taskmanagement.auth.dto.SignUpRequestDto;
import com.fayaz.taskmanagement.auth.dto.SignUpResponseDto;
import com.fayaz.taskmanagement.auth.dto.LoginRequestDto;
import com.fayaz.taskmanagement.auth.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("${endpoint.auth}")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signup(
            @Valid @RequestBody SignUpRequestDto request) {

        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@Valid @RequestBody LoginRequestDto request, HttpServletResponse response) {
        return ResponseEntity.ok(authService.login(request, response));
    }

    @GetMapping("getAllUserNames")
    public Optional<List<String>> getAllUserNames() {
        return authService.getAllUsersNames();
    }
    
}
