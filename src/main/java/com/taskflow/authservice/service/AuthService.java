package com.taskflow.authservice.service;

import com.taskflow.authservice.dto.LoginRequest;
import com.taskflow.authservice.dto.LoginResponse;
import com.taskflow.authservice.dto.UserCredentials;
import com.taskflow.authservice.exception.InvalidCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserServiceClient userServiceClient;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserServiceClient userServiceClient,
                       TokenService tokenService,
                       PasswordEncoder passwordEncoder) {
        this.userServiceClient = userServiceClient;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse login(LoginRequest request) {
        UserCredentials user;
        try{
            user = userServiceClient.findByEmail(request.email());
        }catch (Exception e){
            throw new InvalidCredentialsException();
        }

        if(user == null || !passwordEncoder.matches(request.password(), user.password())) {
            throw new InvalidCredentialsException();
        }

        String token = tokenService.generateToken(user.id(), user.email(), user.name());

        return new LoginResponse(token, "Bearer");
    }
}
