package com.jojotjo.ticketbooking.controller;

import com.jojotjo.ticketbooking.dto.LoginRequest;
import com.jojotjo.ticketbooking.dto.SignupRequest;
import com.jojotjo.ticketbooking.model.User;
import com.jojotjo.ticketbooking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ===== Register =====
    @PostMapping("/register")
    public ResponseEntity<User> register(@Valid @RequestBody SignupRequest request) {
        User user = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    // ===== Login =====
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        userService.login(request);
        return ResponseEntity.ok("Login successful");
    }

    // ===== Get user =====
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable String userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }
}
