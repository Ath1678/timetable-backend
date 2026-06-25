package com.timetable.timetable_backend.service;

import java.util.List;
import com.timetable.timetable_backend.entity.AcademicClass;

public interface AcademicClassService {

    AcademicClass createClass(AcademicClass academicClass);

    List<AcademicClass> getAllClasses();
}