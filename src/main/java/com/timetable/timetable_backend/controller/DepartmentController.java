package com.timetable.timetable_backend.controller;

import com.timetable.timetable_backend.dto.DepartmentDTO;
import com.timetable.timetable_backend.entity.Department;
import com.timetable.timetable_backend.repository.DepartmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentRepository departmentRepository;

    public DepartmentController(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAll() {
        return ResponseEntity.ok(departmentRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody DepartmentDTO departmentDTO) {
        try {
            // Validate required fields
            if (departmentDTO.getName() == null || departmentDTO.getName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Department name is required");
            }

            if (departmentDTO.getSemester() == null || departmentDTO.getSemester().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Semester is required");
            }

            // Create department entity
            Department department = new Department();
            department.setName(departmentDTO.getName().trim());
            department.setSemester(departmentDTO.getSemester());

            // Save department
            Department savedDepartment = departmentRepository.save(department);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDepartment);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating department: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            if (!departmentRepository.existsById(id)) {
                return ResponseEntity.notFound().build();
            }
            departmentRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting department: " + e.getMessage());
        }
    }
}
