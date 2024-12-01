package com.drn.projectmanagementsystem_backend.controller;

import com.drn.projectmanagementsystem_backend.config.JwtProvider;
import com.drn.projectmanagementsystem_backend.exception.BadCredentialsException;
import com.drn.projectmanagementsystem_backend.exception.UserExistsException;
import com.drn.projectmanagementsystem_backend.model.User;
import com.drn.projectmanagementsystem_backend.repository.UserRepository;
import com.drn.projectmanagementsystem_backend.request.LoginRequest;
import com.drn.projectmanagementsystem_backend.response.AuthResponse;
import com.drn.projectmanagementsystem_backend.service.CustomUserDetailsImpl;
import com.drn.projectmanagementsystem_backend.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsImpl customUserDetails;
    private final SubscriptionService subscriptionService;


//    public AuthController(UserRepository userRepository,
//                          PasswordEncoder passwordEncoder,
//                          CustomUserDetailsImpl customUserDetails) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.customUserDetails = customUserDetails;
//    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) {
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

        subscriptionService.createSubscription(savedUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse response = AuthResponse.builder()
                .jwt(jwt)
                .message("Your account has been created successfully")
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> signing(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = JwtProvider.generateToken(authentication);

        AuthResponse response = AuthResponse.builder()
                .jwt(jwt)
                .message("You logged in successfully")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private Authentication authenticate(String username, String password) {

        UserDetails userDetails = customUserDetails.loadUserByUsername(username);

        if(Objects.isNull(userDetails) || !passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid email or password.");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    }


}
