package com.timetable.timetable_backend.controller;

import com.timetable.timetable_backend.entity.User;
import com.timetable.timetable_backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/pending-users")
    public ResponseEntity<List<User>> getPendingUsers(
            @RequestParam Long instituteId) {
        return ResponseEntity.ok(userService.getPendingUsers(instituteId)); // TODO: Secure this to ensure caller is
                                                                            // Admin of instituteId
    }

    @PostMapping("/approve/{userId}")
    public ResponseEntity<?> approveUser(@PathVariable Long userId) {
        userService.approveUser(userId);
        return ResponseEntity.ok("User approved successfully");
    }

    @PostMapping("/reject/{userId}")
    public ResponseEntity<?> rejectUser(@PathVariable Long userId) {
        userService.rejectUser(userId);
        return ResponseEntity.ok("User rejected successfully");
    }
}
