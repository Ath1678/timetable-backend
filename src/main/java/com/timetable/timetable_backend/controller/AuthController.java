package com.timetable.timetable_backend.controller;

import com.timetable.timetable_backend.dto.LoginDTO;
import com.timetable.timetable_backend.dto.RegisterDTO;
import com.timetable.timetable_backend.entity.Institute;
import com.timetable.timetable_backend.entity.Role;
import com.timetable.timetable_backend.entity.User;
import com.timetable.timetable_backend.repository.InstituteRepository;
import com.timetable.timetable_backend.security.JwtUtil;
import com.timetable.timetable_backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private final InstituteRepository instituteRepo;
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    // ✅ Constructor Injection (REQUIRED)
    public AuthController(UserService userService,
            JwtUtil jwtUtil,
            PasswordEncoder passwordEncoder,
            InstituteRepository instituteRepo) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.instituteRepo = instituteRepo;
    }

    @PostMapping("/register")
    @org.springframework.transaction.annotation.Transactional
    public ResponseEntity<?> register(@RequestBody RegisterDTO request) {
        // 1. Check if user already exists
        if (userService.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        Institute institute;

        // 2. Multi-Tenancy Logic
        if ("ADMIN".equalsIgnoreCase(request.getRole())) {
            // ADMIN creates a new Institute
            if (request.getInstituteName() == null || request.getInstituteName().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("Institute Name is required for Admins");
            }

            // Check if Institute Name already exists
            if (instituteRepo.existsByName(request.getInstituteName())) {
                return ResponseEntity.badRequest().body("Institute Name already exists");
            }

            institute = new Institute();
            institute.setName(request.getInstituteName());

            // Generate unique 6-char code
            String code;
            do {
                code = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
            } while (instituteRepo.existsByCode(code));

            institute.setCode(code);
            instituteRepo.save(institute);
        } else {
            // OTHERS join an existing Institute via code
            if (request.getInstituteCode() == null || request.getInstituteCode().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Institute Code is required to join");
            }
            institute = instituteRepo.findByCode(request.getInstituteCode())
                    .orElseThrow(() -> new RuntimeException("Invalid Institute Code"));
        }

        // 3. Create User
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        try {
            if (request.getRole() == null) {
                return ResponseEntity.badRequest().body("Role is required");
            }
            String roleName = request.getRole().toUpperCase();
            if (!roleName.startsWith("ROLE_")) {
                roleName = "ROLE_" + roleName;
            }
            user.setRole(Role.valueOf(roleName));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid Role");
        }
        user.setInstitute(institute);

        // Account Approval Logic
        if (user.getRole() == Role.ROLE_ADMIN) {
            user.setActive(true);
        } else {
            user.setActive(false);
        }

        userService.save(user);

        String responseMessage = user.isActive()
                ? "User registered successfully"
                : "User registered successfully. Please wait for Admin approval.";

        return ResponseEntity.ok(Map.of(
                "message", responseMessage,
                "instituteCode", institute.getCode()));
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginDTO request) {

        User user = userService.getByUsername(request.getUsername());

        // Note: userService.getByUsername might need better error handling if it
        // assumes user exists
        // but here it throws RuntimeException which is caught by global handler
        // hopefully?
        // Ideally we check if user exists first or handle the exception.
        // Based on service impl, it throws "User not found".

        // Double check null just in case service changes
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        if (!user.isActive()) {
            throw new RuntimeException("Account is pending approval");
        }

        String token = jwtUtil.generateToken(
                user.getUsername(),
                user.getRole().name());

        return Map.of(
                "token", token,
                "role", user.getRole().name(),
                "instituteId", String.valueOf(user.getInstitute().getId()),
                "instituteName", user.getInstitute().getName());
    }
}