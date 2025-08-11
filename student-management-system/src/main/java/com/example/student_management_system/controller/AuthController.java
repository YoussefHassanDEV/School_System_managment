package com.example.student_management_system.controller;

import com.example.student_management_system.DTOS.UserDto;
import com.example.student_management_system.DTOS.AuthResponse;
import com.example.student_management_system.Enum.Role;
import com.example.student_management_system.Util.JwtUtil;
import com.example.student_management_system.model.AppUser;
import com.example.student_management_system.repositiory.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                          UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserDto request) throws Exception {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        AppUser user = new AppUser();
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setRole(Role.MANAGER);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto request) {
        try {

            var auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            var userDetails = (org.springframework.security.core.userdetails.User) auth.getPrincipal();

            List<String> roles = userDetails.getAuthorities()
                    .stream()
                    .map(a -> a.getAuthority())
                    .collect(Collectors.toList());

            String token = jwtUtil.generateToken(userDetails.getUsername(), roles);

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

}
