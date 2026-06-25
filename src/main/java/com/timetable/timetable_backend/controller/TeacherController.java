package com.timetable.timetable_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.timetable.timetable_backend.dto.TeacherDTO;
import com.timetable.timetable_backend.entity.Department;
import com.timetable.timetable_backend.entity.Teacher;
import com.timetable.timetable_backend.repository.DepartmentRepository;
import com.timetable.timetable_backend.service.TeacherService;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;
    private final DepartmentRepository departmentRepository;

    public TeacherController(TeacherService teacherService, DepartmentRepository departmentRepository) {
        this.teacherService = teacherService;
        this.departmentRepository = departmentRepository;
    }

    @PostMapping
    public ResponseEntity<?> createTeacher(@RequestBody TeacherDTO teacherDTO) {
        try {
            System.out.println(
                    "Adding Teacher: Name=" + teacherDTO.getName() + ", DeptId=" + teacherDTO.getDepartmentId());

            // Validate required fields
            if (teacherDTO.getName() == null || teacherDTO.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Name is required");
            }

            if (teacherDTO.getDepartmentId() == null) {
                return ResponseEntity.badRequest().body("Department is required");
            }

            // Create teacher entity
            Teacher teacher = new Teacher();
            teacher.setName(teacherDTO.getName().trim());

            // Set optional fields
            if (teacherDTO.getEmail() != null && !teacherDTO.getEmail().trim().isEmpty()) {
                teacher.setEmail(teacherDTO.getEmail());
            }

            if (teacherDTO.getMaxLoad() != null) {
                teacher.setMaxLoad(teacherDTO.getMaxLoad());
            }

            if (teacherDTO.getSemester() != null && !teacherDTO.getSemester().trim().isEmpty()) {
                teacher.setSemester(teacherDTO.getSemester());
            }

            // Fetch and set department
            Department department = departmentRepository.findById(teacherDTO.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException(
                            "Department not found with id: " + teacherDTO.getDepartmentId()));
            teacher.setDepartment(department);

            // Save teacher
            Teacher savedTeacher = teacherService.createTeacher(teacher);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTeacher);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating teacher: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        return ResponseEntity.ok(teacherService.getAllTeachers());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTeacher(@PathVariable Long id) {
        try {
            teacherService.deleteTeacher(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting teacher: " + e.getMessage());
        }
    }
}
