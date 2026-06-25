package com.timetable.timetable_backend.service;

import java.util.List;
import com.timetable.timetable_backend.entity.Teacher;

public interface TeacherService {

    Teacher createTeacher(Teacher teacher);

    List<Teacher> getAllTeachers();

	void deleteTeacher(Long id);
}
