package com.timetable.timetable_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.timetable.timetable_backend.entity.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    java.util.List<Subject> findByDepartment_Name(String name);
}
