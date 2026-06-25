package com.timetable.timetable_backend.controller;

import com.timetable.timetable_backend.repository.*;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DepartmentRepository departmentRepo;
    private final TeacherRepository teacherRepo;
    private final AcademicClassRepository classRepo;
    private final SubjectRepository subjectRepo;

    public DashboardController(
            DepartmentRepository departmentRepo,
            TeacherRepository teacherRepo,
            AcademicClassRepository classRepo,
            SubjectRepository subjectRepo) {

        this.departmentRepo = departmentRepo;
        this.teacherRepo = teacherRepo;
        this.classRepo = classRepo;
        this.subjectRepo = subjectRepo;
    }

    @GetMapping("/stats")
    public Map<String, Long> getStats() {
        Map<String, Long> stats = new HashMap<>();

        stats.put("departments", departmentRepo.count());
        stats.put("teachers", teacherRepo.count());
        stats.put("classes", classRepo.count());
        stats.put("subjects", subjectRepo.count());

        return stats;
    }
}
