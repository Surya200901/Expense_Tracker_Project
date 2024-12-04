package com.expensetracker.security;

import com.expensetracker.entity.AppUser;  // Your custom AppUser entity
import com.expensetracker.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;  // Import from Spring Security
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        // Fetch user from the database using the username
        AppUser appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Return a Spring Security UserDetails object with the user data
        return new User(appUser.getUsername(), appUser.getPassword(), Collections.emptyList()); // empty authorities list
    }
}
