package com.timetable.timetable_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.timetable.timetable_backend.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
