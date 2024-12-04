package com.expensetracker.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.expensetracker.dto.AuthRequest;
import com.expensetracker.dto.AuthResponse;
import com.expensetracker.entity.AppUser;
import com.expensetracker.repository.UserRepository;
import com.expensetracker.security.JwtService;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(AuthRequest authRequest) {
        AppUser user = new AppUser();
        user.setUsername(authRequest.getUsername());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        user.setEmail(authRequest.getUsername() + "@example.com"); // Temporary email logic
        userRepository.save(user);
        return new AuthResponse(jwtService.generateToken(authRequest.getUsername()));
    }

    public AuthResponse login(AuthRequest authRequest) {
        AppUser user = userRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            return new AuthResponse(jwtService.generateToken(authRequest.getUsername()));
        } else {
            throw new RuntimeException("Invalid password");
        }
    }
}
