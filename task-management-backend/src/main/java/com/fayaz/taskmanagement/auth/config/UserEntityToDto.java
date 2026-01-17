package com.fayaz.taskmanagement.auth.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.fayaz.taskmanagement.auth.entity.UserEntity;

@Component
public class UserEntityToDto implements AuditorAware<UserEntity> {

    @Override
    public Optional<UserEntity> getCurrentAuditor() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof UserEntity) {
            UserEntity user = (UserEntity) auth.getPrincipal();
            return Optional.of(user);
        }

        return Optional.empty();    
    }
    
}
