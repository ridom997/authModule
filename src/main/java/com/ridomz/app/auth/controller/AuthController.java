package com.ridomz.app.auth.controller;

import com.ridomz.app.auth.dto.request.AuthRequest;
import com.ridomz.app.auth.service.JwtService;
import com.ridomz.app.auth.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {
        return userService.login(request);
    }

    @PostMapping("/register")
    public String register(@RequestBody AuthRequest request) {
        return userService.create(request).toString();
    }
}
