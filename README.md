# Student Management System - Spring Boot

This repository contains a robust Student Management System built with Spring Boot, focusing on secure authentication, role-based authorization, and comprehensive CRUD operations. It serves as a practical example of developing a secure and scalable backend application.

## Project Overview

This project showcases a comprehensive Student Management System developed using Spring Boot, designed with a strong emphasis on secure authentication, role-based authorization, and efficient data management. The system provides full CRUD (Create, Read, Update, Delete) capabilities for various user roles, including Managers, Teachers, Students, Subjects, and Exams.

## Key Features and Technical Highlights:

*   **Role-Based Access Control (RBAC):** Implemented granular access control to ensure that different user types (SUPER_MANAGER, MANAGER, TEACHER, STUDENT) have appropriate permissions, preventing unauthorized data access and operations.
*   **JWT Authentication:** Utilizes JSON Web Tokens (JWT) for stateless authentication, enhancing security and scalability. The system correctly generates and validates tokens, embedding user roles directly within the token for efficient authorization checks.
*   **Secure Password Management:** Incorporates robust password encoding using BCrypt to protect sensitive user credentials, both during initial registration and subsequent updates.
*   **Modular Service Architecture:** Organized into distinct service layers for Student, Teacher, Manager, and Exam management, promoting code reusability, maintainability, and clear separation of concerns.
*   **Data Validation:** Employs DTO-level validation to ensure data integrity and prevent invalid or malicious data from entering the system.
*   **Repository Pattern:** Leverages Spring Data JPA repositories for streamlined data access and persistence, abstracting database interactions.

## Challenges Addressed and Solutions Implemented:

During the development and review process, several critical issues were identified and subsequently resolved, demonstrating a strong commitment to best practices in software engineering and security:

*   **Resolved Inconsistent Role Handling:** Initially, the system suffered from duplicate and overriding role matchers in `SecurityConfig`, leading to incorrect access permissions. This was rectified by refining the security configuration to ensure precise role-based access for all API endpoints.
*   **Fixed Hardcoded User Registration:** The initial implementation hardcoded the `MANAGER` role during user signup, limiting flexibility. The updated system now allows dynamic role assignment during registration, supporting `STUDENT`, `TEACHER`, `MANAGER`, and `SUPER_MANAGER` roles.
*   **Enhanced Password Security:** Addressed a critical vulnerability where password updates were not being encoded. The revised solution ensures that all password modifications are securely encoded using BCrypt, preventing plain-text storage.
*   **Improved JWT Role Integration:** The original system generated JWTs with embedded roles but did not fully utilize them for authentication, relying instead on database lookups. The updated `JwtAuthFilter` now correctly extracts and uses roles directly from the JWT, aligning with stateless authentication principles and improving performance.
*   **Refactored Repository Duplication:** Identified and consolidated redundant repository interfaces (`AppUserRepository` and `UserRepository`) to improve code consistency and reduce unnecessary duplication.
*   **Addressed Potential Infinite Recursion:** Mitigated a potential infinite recursion issue in the `Manager` entity's `@OneToMany` relationship by ensuring proper JSON serialization handling.

## Technical Stack:

*   **Backend:** Spring Boot, Spring Security, Spring Data JPA
*   **Authentication:** JWT (JSON Web Tokens)
*   **Database:** (Implicitly, any relational database supported by Spring Data JPA)
*   **Build Tool:** (Implicitly, Maven or Gradle)

## Installation

To set up and run this Spring Boot Student Management System locally, follow these steps:

### Prerequisites

Ensure you have the following installed on your system:

*   **Java Development Kit (JDK) 17 or higher:** Spring Boot 3.x requires Java 17 or newer. You can download it from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.java.net/install/).
*   **Maven (recommended) or Gradle:** This project is typically built with Maven. Download Maven from [Apache Maven Project](https://maven.apache.org/download.cgi).
*   **A Database System:** The project uses Spring Data JPA, which supports various relational databases. For local development, H2 (in-memory), MySQL, or PostgreSQL are common choices. Ensure your database is running and you have the necessary credentials.
*   **An IDE (Integrated Development Environment):** IntelliJ IDEA, Eclipse, or VS Code with Spring Boot extensions are recommended.

### Steps to Run

1.  **Clone the Repository:**

    ```bash
    git clone <repository_url>
    cd <project_directory>
    ```

    *(Replace `<repository_url>` with the actual URL of your GitHub repository and `<project_directory>` with the name of the cloned directory.)*

2.  **Configure Database Connection:**

    Open the `src/main/resources/application.properties` (or `application.yml`) file and configure your database connection details. For example, for MySQL:

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/student_management_db
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true
    ```

    Adjust these properties based on your chosen database and credentials.

3.  **Build the Project:**

    Navigate to the project root directory in your terminal and build the project using Maven:

    ```bash
    mvn clean install
    ```

    This command compiles the code, runs tests, and packages the application into a JAR file.

4.  **Run the Application:**

    After a successful build, you can run the Spring Boot application from the terminal:

    ```bash
    java -jar target/<your-application-name>.jar
    ```

    *(Replace `<your-application-name>.jar` with the actual name of the generated JAR file, e.g., `student-management-system-0.0.1-SNAPSHOT.jar`)*

    Alternatively, you can run the application directly from your IDE.

5.  **Access the Application:**

    Once the application starts, it will typically be accessible at `http://localhost:8080` (or a different port if configured in `application.properties`).

    You can then use tools like Postman or cURL to interact with the API endpoints.

## API Endpoints

The Student Management System exposes a RESTful API for managing students, teachers, managers, subjects, and exams, with authentication and role-based authorization.

### Authentication Endpoints

*   **`POST /api/auth/signup`**
    *   **Description:** Registers a new user with a specified role. Supports `STUDENT`, `TEACHER`, `MANAGER`, and `SUPER_MANAGER` roles.
    *   **Request Body:**
        ```json
        {
            "name": "John Doe",
            "username": "johndoe",
            "password": "securepassword",
            "role": "STUDENT" // Optional: STUDENT, TEACHER, MANAGER, SUPER_MANAGER
        }
        ```
    *   **Response:** `200 OK` on success, `400 Bad Request` if username exists or invalid role.

*   **`POST /api/auth/login`**
    *   **Description:** Authenticates a user and returns a JWT token.
    *   **Request Body:**
        ```json
        {
            "username": "johndoe",
            "password": "securepassword"
        }
        ```
    *   **Response:** `200 OK` with JWT token, `401 Unauthorized` on invalid credentials.
        ```json
        {
            "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
        }
        ```

### Role-Based Endpoints (Examples)

Access to these endpoints is controlled by the user's role, as defined in `SecurityConfig.java`.

*   **`/api/admin/**`**
    *   **Access:** `SUPER_MANAGER` only.
    *   **Example:** `GET /api/admin/users` (to manage all users)

*   **`/api/managers/**`**
    *   **Access:** `SUPER_MANAGER`, `MANAGER`.
    *   **Example:** `GET /api/managers/{id}` (to retrieve manager details)

*   **`/api/teachers/**`**
    *   **Access:** `SUPER_MANAGER`, `MANAGER`, `TEACHER`.
    *   **Example:** `GET /api/teachers/{id}/students` (to view students assigned to a teacher)

*   **`/api/students/**`**
    *   **Access:** `SUPER_MANAGER`, `MANAGER`, `TEACHER`, `STUDENT`.
    *   **Example:** `GET /api/students/{id}/exams` (to view a student's exam results)

### General API Usage

All protected API endpoints require a valid JWT token in the `Authorization` header, prefixed with `Bearer`.

```
Authorization: Bearer <your_jwt_token>
```

For detailed API specifications, refer to the source code and consider generating OpenAPI/Swagger documentation from the project.

## Code Fixes and Improvements (from ChatGPT Conversation)

This section summarizes the key code fixes and improvements discussed in the ChatGPT conversation, demonstrating the project's commitment to best practices and robust development.

### 1) `Role.java` — add `SUPER_MANAGER`

```java
package com.example.student_management_system.Enum;

public enum Role {
    STUDENT,
    TEACHER,
    MANAGER,
    SUPER_MANAGER
}
```

### 2) `UserDto.java` — allow role in signup (validated string)

```java
package com.example.student_management_system.DTOS;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String name;
    private String username;
    private String password;
    private String role; // optional: "STUDENT", "TEACHER", "MANAGER", "SUPER_MANAGER"
}
```

### 3) `JwtUtil.java` — expose roles extraction

```java
package com.example.student_management_system.Util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    private final Key key;
    private final int jwtExpirationMs;

    public JwtUtil(@Value("${jwt.secret}") String jwtSecret,
                   @Value("${jwt.expiration}") int jwtExpirationMs) {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        this.jwtExpirationMs = jwtExpirationMs;
    }

    public String generateToken(String username, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<String> getRolesFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
        Object rolesObj = claims.get("roles");
        if (rolesObj instanceof List) {
            return (List<String>) rolesObj;
        }
        return List.of();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // token invalid or expired
        }
        return false;
    }
}
```

### 4) `JwtAuthFilter.java` — use roles from token (not only DB)

```java
package com.example.student_management_system.Filter;

import com.example.student_management_system.Util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.matches("^/api/auth/(signup|login)$");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = authHeader.substring(7);
        if (!jwtUtil.validateToken(jwtToken)) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtUtil.getUsernameFromToken(jwtToken);

        // If already authenticated, continue
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Prefer roles from token (single source of truth for stateless auth)
            List<String> roles = jwtUtil.getRolesFromToken(jwtToken);
            var authorities = roles.stream()
                    .map(r -> new SimpleGrantedAuthority("ROLE_" + r))
                    .collect(Collectors.toList());

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}
```

### 5) `AuthController.java` — signup accepts role, encodes password, generates token on login including roles

```java
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
    public ResponseEntity<?> signup(@RequestBody UserDto request) {
        if (request.getUsername() == null || request.getPassword() == null) {
            return ResponseEntity.badRequest().body("username and password required");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        AppUser user = new AppUser();
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // parse role if provided; default to STUDENT
        Role role = Role.STUDENT;
        if (request.getRole() != null) {
            try {
                role = Role.valueOf(request.getRole().trim().toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Invalid role. Allowed: STUDENT, TEACHER, MANAGER, SUPER_MANAGER");
            }
        }
        user.setRole(role);
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
                    .map(a -> a.getAuthority().replace("ROLE_", ""))
                    .collect(Collectors.toList());

            String token = jwtUtil.generateToken(userDetails.getUsername(), roles);

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

}
```

### 6) `SecurityConfig.java` — consistent matchers and filter registration

```java
package com.example.student_management_system.Config;

import com.example.student_management_system.Filter.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(UserDetailsService userDetailsService, JwtAuthFilter jwtAuthFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("SUPER_MANAGER")
                        .requestMatchers("/api/managers/**").hasAnyRole("SUPER_MANAGER", "MANAGER")
                        .requestMatchers("/api/teachers/**").hasAnyRole("SUPER_MANAGER", "MANAGER", "TEACHER")
                        .requestMatchers("/api/students/**").hasAnyRole("SUPER_MANAGER", "MANAGER", "TEACHER", "STUDENT")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
```

## Conclusion

This project demonstrates a robust understanding of Spring Boot development, secure application design, and effective problem-solving in a real-world application context. The implemented fixes highlight a proactive approach to identifying and resolving complex technical and security challenges, resulting in a more reliable and maintainable system.


