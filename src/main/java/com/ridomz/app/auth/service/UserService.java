package com.ridomz.app.auth.service;

import com.ridomz.app.auth.dto.entity.UserEntity;
import com.ridomz.app.auth.dto.request.AuthRequest;
import com.ridomz.app.auth.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity create(AuthRequest authRequest) {
        return userRepository.save(UserEntity.builder()
                .username(authRequest.username())
                .password(passwordEncoder.encode(authRequest.password()))
                .build());
    }
}
