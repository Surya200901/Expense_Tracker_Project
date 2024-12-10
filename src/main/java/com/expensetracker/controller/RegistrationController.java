package com.expensetracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.expensetracker.entity.AppUser;
import com.expensetracker.repository.UserRepository;

@Controller
@RequestMapping("/api")
public class RegistrationController {

    @Autowired
    private UserRepository userRepository; // Assuming you have a UserRepository

    @PostMapping("/register")
    public String registerUser(
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam("password") String password) {
        System.out.println("Register endpoint hit!");
     // Log data to verify
        System.out.println("Registering user: " + username + ", " + email);
        
        AppUser user = new AppUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password); // Consider encrypting this later
        
        userRepository.save(user);
        System.out.println("User saved!");
        
        return "redirect:/login.html";
    }

}
