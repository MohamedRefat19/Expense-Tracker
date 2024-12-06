package com.example.spring.boot.project.controller;

import com.example.spring.boot.project.DTO.LoginRequest;
import com.example.spring.boot.project.DTO.RegisterRequest;
import com.example.spring.boot.project.model.User;
import com.example.spring.boot.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        String response = userService.registerUser(registerRequest);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest loginRequest) {
        String token = userService.loginUser(loginRequest);
        return ResponseEntity.ok(token);
    }
    @PutMapping("/update/{username}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> updateUser(@PathVariable String username, @RequestBody User updatedUser) {
        User user = userService.updateUser(username, updatedUser);
        return ResponseEntity.ok(user);
    }
    @DeleteMapping("/delete/{username}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok("User deleted successfully");
    }


}
