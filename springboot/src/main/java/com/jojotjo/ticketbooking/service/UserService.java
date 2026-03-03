package com.jojotjo.ticketbooking.service;

import com.jojotjo.ticketbooking.dto.LoginRequest;
import com.jojotjo.ticketbooking.dto.SignupRequest;
import com.jojotjo.ticketbooking.exception.UserNotFoundException;
import com.jojotjo.ticketbooking.model.User;
import com.jojotjo.ticketbooking.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ===== Register =====
    public User register(SignupRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setHashedPassword(passwordEncoder.encode(request.getPassword()));

        return userRepository.save(user);
    }

    // ===== Login =====
    public User login(LoginRequest request) {
        return userRepository
                .findByEmail(request.getEmail())
                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getHashedPassword()))
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
    }

    // ===== Fetch user =====
    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }
}