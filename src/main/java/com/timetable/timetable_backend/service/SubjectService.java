package com.timetable.timetable_backend.service;

import java.util.List;
import com.timetable.timetable_backend.entity.Subject;

public interface SubjectService {

    Subject createSubject(Subject subject);

    List<Subject> getAllSubjects();

    void deleteSubject(Long id);
}
