
package com.fayaz.taskmanagement.auth.service;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fayaz.taskmanagement.auth.RoleEnum;
import com.fayaz.taskmanagement.auth.dto.AuthResponseDto;
import com.fayaz.taskmanagement.auth.dto.LoginRequestDto;
import com.fayaz.taskmanagement.auth.dto.SignUpRequestDto;
import com.fayaz.taskmanagement.auth.entity.UserEntity;
import com.fayaz.taskmanagement.auth.repository.UserRepository;
import com.fayaz.taskmanagement.utils.JwtUtility;
import com.fayaz.taskmanagement.auth.exceptions.InvalidCredentialsException;
import com.fayaz.taskmanagement.auth.exceptions.UserAlreadyExistsException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private JwtUtility jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void signup(SignUpRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email not available");
        }
        if (userRepository.existsByUserName(request.getUserName())) {
            throw new UserAlreadyExistsException("Username not available");
        }

        String password = request.getPassword();
        if (!isStrongPassword(password)) {
            throw new IllegalArgumentException("Password must be at least 8 characters long and contain at least one number, one uppercase letter, and one lowercase letter.");
        }

    String encodedPassword = passwordEncoder.encode(password);
    UserEntity user = UserEntity.builder()
        .userName(request.getUserName())
        .email(request.getEmail())
        .password(encodedPassword)
        .role(request.getRole() == null ? RoleEnum.ROLE_USER : request.getRole())
        .createdAt(Instant.now())
        .build();
    userRepository.save(user);
    }

    private boolean isStrongPassword(String password) {
        if (password == null) return false;
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[A-Za-z\\d!@#$%^&*]{8,}$")) {
            return false;
        }
        if (password.contains(" ")) {
            return false;
        }
        if (password.matches(".*(.)\\1{2,}.*")) {
            return false;
        }
        String lower = password.toLowerCase();
        for (int i = 0; i < lower.length() - 2; i++) {
            char c1 = lower.charAt(i);
            char c2 = lower.charAt(i + 1);
            char c3 = lower.charAt(i + 2);
            if ((c2 == c1 + 1 && c3 == c2 + 1) || (c2 == c1 - 1 && c3 == c2 - 1)) {
                return false;
            }
        }
        return true;
    }

    public AuthResponseDto login(LoginRequestDto request) {
        String identifier = request.getIdentifier();
        if (identifier == null || identifier.trim().isEmpty()) {
            throw new InvalidCredentialsException("Invalid username/email or password");
        }

        UserEntity user = userRepository
                .findByUserNameOrEmail(identifier, identifier)
                .orElseThrow(() -> new InvalidCredentialsException("User not found Please register before logging in: " + identifier));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username/email or password");
        }

        String token = jwtUtil.generateToken(user);

        return AuthResponseDto.builder()
                .token(token)
                .userName(user.getUserName())
                .build();
    }
}
