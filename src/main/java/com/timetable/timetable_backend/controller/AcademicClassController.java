package com.timetable.timetable_backend.controller;

import com.timetable.timetable_backend.dto.AcademicClassDTO;
import com.timetable.timetable_backend.entity.AcademicClass;
import com.timetable.timetable_backend.entity.Department;
import com.timetable.timetable_backend.repository.AcademicClassRepository;
import com.timetable.timetable_backend.repository.DepartmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/classes")
public class AcademicClassController {

    private final AcademicClassRepository classRepository;
    private final DepartmentRepository departmentRepository;

    public AcademicClassController(AcademicClassRepository classRepository,
            DepartmentRepository departmentRepository) {
        this.classRepository = classRepository;
        this.departmentRepository = departmentRepository;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<AcademicClass> classes = classRepository.findAll();

            // Filter out classes with invalid department references
            List<AcademicClass> validClasses = classes.stream()
                    .filter(c -> {
                        try {
                            return c.getDepartment() != null && c.getDepartment().getId() != null;
                        } catch (Exception e) {
                            return false;
                        }
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(validClasses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching classes: " + e.getMessage() +
                            ". Please clean your database by deleting invalid records.");
        }
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody AcademicClassDTO classDTO) {
        try {
            System.out.println("Adding Class: Name=" + classDTO.getName() + ", DeptId=" + classDTO.getDepartmentId());

            // Validate required fields
            if (classDTO.getName() == null || classDTO.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Class name is required");
            }

            if (classDTO.getDepartmentId() == null) {
                return ResponseEntity.badRequest().body("Department is required");
            }

            if (classDTO.getSemester() == null || classDTO.getSemester().trim().isEmpty()) {
                // Default to "1" if not provided, instead of erroring
                classDTO.setSemester("1");
            }

            // Create academic class entity
            AcademicClass academicClass = new AcademicClass();
            academicClass.setName(classDTO.getName());
            academicClass.setSemester(classDTO.getSemester());

            // Fetch and set department
            Department department = departmentRepository.findById(classDTO.getDepartmentId())
                    .orElseThrow(
                            () -> new RuntimeException("Department not found with id: " + classDTO.getDepartmentId()
                                    + ". Please ensure the department exists first."));
            academicClass.setDepartment(department);

            // Save class
            AcademicClass savedClass = classRepository.save(academicClass);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedClass);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving class: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            if (!classRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            classRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting class: " + e.getMessage());
        }
    }

    // Add endpoint to clean invalid data
    @DeleteMapping("/clean-invalid")
    public ResponseEntity<?> cleanInvalidData() {
        try {
            List<AcademicClass> allClasses = classRepository.findAll();
            int deleted = 0;

            for (AcademicClass c : allClasses) {
                try {
                    if (c.getDepartment() == null || c.getDepartment().getId() == null) {
                        classRepository.delete(c);
                        deleted++;
                    }
                } catch (Exception e) {
                    classRepository.delete(c);
                    deleted++;
                }
            }

            return ResponseEntity.ok("Cleaned " + deleted + " invalid records");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error cleaning data: " + e.getMessage());
        }
    }
}
