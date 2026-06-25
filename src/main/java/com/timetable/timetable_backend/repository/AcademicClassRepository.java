package com.timetable.timetable_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.timetable.timetable_backend.entity.AcademicClass;

public interface AcademicClassRepository extends JpaRepository<AcademicClass, Long> {
}