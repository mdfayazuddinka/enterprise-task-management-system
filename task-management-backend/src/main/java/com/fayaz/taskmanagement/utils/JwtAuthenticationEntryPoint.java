package com.fayaz.taskmanagement.utils;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException {

        Throwable exception = (Throwable) request.getAttribute("jwt_exception");

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        if (exception instanceof ExpiredJwtException) {
            response.getWriter().write("""
                {
                    "status": 401,
                    "error": "TOKEN_EXPIRED",
                    "message": "JWT token has expired. Please login again."
                }
            """);
        } else {
            response.getWriter().write("""
                {
                    "status": 401,
                    "error": "UNAUTHORIZED",
                    "message": "Invalid or missing JWT token"
                }
            """);
        }
    }
}
