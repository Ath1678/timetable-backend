package com.timetable.timetable_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.timetable.timetable_backend.dto.SubjectDTO;
import com.timetable.timetable_backend.entity.Department;
import com.timetable.timetable_backend.entity.Subject;
import com.timetable.timetable_backend.entity.Teacher;
import com.timetable.timetable_backend.repository.DepartmentRepository;
import com.timetable.timetable_backend.repository.TeacherRepository;
import com.timetable.timetable_backend.service.SubjectService;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;
    private final DepartmentRepository departmentRepository;
    private final TeacherRepository teacherRepository;

    public SubjectController(SubjectService subjectService,
            DepartmentRepository departmentRepository,
            TeacherRepository teacherRepository) {
        this.subjectService = subjectService;
        this.departmentRepository = departmentRepository;
        this.teacherRepository = teacherRepository;
    }

    @PostMapping
    public ResponseEntity<?> createSubject(@RequestBody SubjectDTO subjectDTO) {
        try {
            System.out.println(
                    "Adding Subject: Name=" + subjectDTO.getName() + ", TeacherId=" + subjectDTO.getTeacherId());

            // Validate required fields
            if (subjectDTO.getName() == null || subjectDTO.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Subject name is required");
            }

            if (subjectDTO.getTeacherId() == null) {
                return ResponseEntity.badRequest().body("Teacher is required");
            }

            // Create subject entity
            Subject subject = new Subject();
            subject.setName(subjectDTO.getName().trim());

            // Set code - if not provided, generate from name
            if (subjectDTO.getCode() != null && !subjectDTO.getCode().trim().isEmpty()) {
                subject.setCode(subjectDTO.getCode());
            } else {
                // Auto-generate code from subject name if not provided
                String autoCode = subjectDTO.getName().toUpperCase().replaceAll("\\s+", "").substring(0,
                        Math.min(6, subjectDTO.getName().length()));
                subject.setCode(autoCode);
            }

            // Set department if provided (optional)
            if (subjectDTO.getDepartmentId() != null) {
                Department department = departmentRepository.findById(subjectDTO.getDepartmentId())
                        .orElseThrow(() -> new RuntimeException(
                                "Department not found with id: " + subjectDTO.getDepartmentId()));
                subject.setDepartment(department);
            }

            // Fetch and set teacher (required)
            Teacher teacher = teacherRepository.findById(subjectDTO.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found with id: " + subjectDTO.getTeacherId()));
            subject.setTeacher(teacher);

            // Save subject
            Subject savedSubject = subjectService.createSubject(subject);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSubject);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating subject: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Subject>> getAllSubjects() {
        return ResponseEntity.ok(subjectService.getAllSubjects());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubject(@PathVariable Long id) {
        try {
            subjectService.deleteSubject(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting subject: " + e.getMessage());
        }
    }
}
