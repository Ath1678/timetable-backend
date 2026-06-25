package com.timetable.timetable_backend.controller;

import com.timetable.timetable_backend.entity.Timetable;
import com.timetable.timetable_backend.service.GeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/generate")
public class GeneratorController {

    private final GeneratorService generatorService;

    public GeneratorController(GeneratorService generatorService) {
        this.generatorService = generatorService;
    }

    @PostMapping
    public ResponseEntity<?> generateTimetable(@RequestBody Map<String, Object> request) {
        try {
            String department = (String) request.get("department");
            List<String> rooms = (List<String>) request.get("rooms");

            if (department == null || rooms == null || rooms.isEmpty()) {
                return ResponseEntity.badRequest().body("Department and Rooms are required.");
            }

            List<Timetable> schedule = generatorService.generateForDepartment(department, rooms);
            return ResponseEntity.ok(schedule);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Generation failed: " + e.getMessage());
        }
    }
}
