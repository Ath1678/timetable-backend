package com.timetable.timetable_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.timetable.timetable_backend.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
