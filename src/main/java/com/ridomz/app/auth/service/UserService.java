package com.ridomz.app.auth.service;

import com.ridomz.app.auth.dto.entity.UserEntity;
import com.ridomz.app.auth.dto.request.AuthRequest;
import com.ridomz.app.auth.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserEntity create(AuthRequest authRequest) {
        return userRepository.save(UserEntity.builder()
                .username(authRequest.username())
                .password(passwordEncoder.encode(authRequest.password()))
                .build());
    }

    public String login(AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
            );
            return jwtService.generateToken((UserDetails) authentication.getPrincipal());
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid credentials");
        }
    }
}
