package com.example.spring.boot.project.service;

import com.example.spring.boot.project.DTO.LoginRequest;
import com.example.spring.boot.project.DTO.RegisterRequest;
import com.example.spring.boot.project.Repository.UserRepository;
import com.example.spring.boot.project.model.User;
import com.example.spring.boot.project.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public User findUserBYUsername(String username){
     Optional<User>user =userRepository.findByUsername(username);
        return user.orElseThrow(() -> new RuntimeException("User not found with UserName: " + username));
    }

    public String registerUser(RegisterRequest request) {
        validateRegisterRequest(request);
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already taken");
        }
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setName(request.getName());
        userRepository.save(user);
        return "User registered successfully";
    }
    public String loginUser(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        return jwtUtil.generateToken(user.getUsername());
    }
    public User updateUser(String username, User updatedUser) {
        User existingUser = findUserBYUsername(username);
        if (updatedUser.getName() != null) {
            existingUser.setName(updatedUser.getName());
        }
        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        return userRepository.save(existingUser);
    }
    public void deleteUser(String username) {
        User user = findUserBYUsername(username);
        userRepository.delete(user);
    }
    private void validateRegisterRequest(RegisterRequest request) {
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            throw new RuntimeException("Username cannot be empty");
        }
        if (request.getUsername().length() < 3 || request.getUsername().length() > 20) {
            throw new RuntimeException("Username must be between 3 and 20 characters");
        }
        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            throw new RuntimeException("Password cannot be empty");
        }
        if (!request.getPassword().matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$")) {
            throw new RuntimeException("Password must be at least 8 characters long and include a mix of upper/lowercase letters, numbers, and special characters");
        }
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new RuntimeException("Name cannot be empty");
        }
    }


}
