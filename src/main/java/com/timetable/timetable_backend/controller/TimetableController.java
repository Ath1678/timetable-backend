package com.timetable.timetable_backend.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.timetable.timetable_backend.entity.Timetable;
import com.timetable.timetable_backend.repository.TimetableRepository;

@RestController
@RequestMapping("/api/timetable")
public class TimetableController {

    private final TimetableRepository repo;

    public TimetableController(TimetableRepository repo) {
        this.repo = repo;
    }

    // LOAD
    @GetMapping("/{className}")
    public List<Timetable> load(@PathVariable String className) {
        return repo.findByClassName(className);
    }

    // SAVE / UPDATE
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Timetable t) {
        try {
            // Normalize Data
            String className = t.getClassName() != null ? t.getClassName().trim() : "";
            String day = t.getDay() != null ? t.getDay().trim() : "";
            int period = t.getPeriod();
            String subject = t.getSubject() != null ? t.getSubject().trim() : "";
            String teacher = t.getTeacher() != null ? t.getTeacher().trim() : "";

            // LOGGING
            System.out.println("Saving Timetable: Class=" + className + ", Day=" + day + ", Period=" + period
                    + ", Subject=" + subject);

            // Check if slot already exists
            Timetable existing = repo.getByClassNameAndDayAndPeriod(
                    className, day, period);

            if (existing != null) {
                // Update existing record
                existing.setSubject(subject);
                existing.setTeacher(teacher);
                existing.setDepartment(t.getDepartment());
                existing.setSemester(t.getSemester());
                return ResponseEntity.ok(repo.save(existing));
            } else {
                // Create new record with normalized data
                t.setClassName(className);
                t.setDay(day);
                t.setSubject(subject);
                t.setTeacher(teacher);
                return ResponseEntity.ok(repo.save(t));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving timetable: " + e.getMessage());
        }
    }

    // DELETE CELL
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }
}
