package com.drn.projectmanagementsystem_backend.controller;

import com.drn.projectmanagementsystem_backend.exception.UserExistsException;
import com.drn.projectmanagementsystem_backend.model.User;
import com.drn.projectmanagementsystem_backend.repository.UserRepository;
import com.drn.projectmanagementsystem_backend.service.CustomUserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private CustomUserDetailsImpl customUserDetails;

    @PostMapping("/signup")
    public ResponseEntity<User> createUserHandler(@RequestBody User user) {
        User isUserExist = userRepository.findByEmail(user.getEmail());

        if(Objects.nonNull(isUserExist)) {
            throw new UserExistsException("User already exists with email: " + user.getEmail());
        }

        User savedUser = userRepository.save(
                User.builder()
                        .fullName(user.getFullName())
                        .email(user.getEmail())
                        .password(passwordEncoder.encode(user.getPassword()))
                        .build()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);

    }


}
