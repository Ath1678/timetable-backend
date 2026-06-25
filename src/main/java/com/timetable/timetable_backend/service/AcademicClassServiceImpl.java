package com.timetable.timetable_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.timetable.timetable_backend.entity.AcademicClass;
import com.timetable.timetable_backend.repository.AcademicClassRepository;

@Service
public class AcademicClassServiceImpl implements AcademicClassService {

    private final AcademicClassRepository academicClassRepository;

    public AcademicClassServiceImpl(AcademicClassRepository academicClassRepository) {
        this.academicClassRepository = academicClassRepository;
    }

    @Override
    public AcademicClass createClass(AcademicClass academicClass) {
        return academicClassRepository.save(academicClass);
    }

    @Override
    public List<AcademicClass> getAllClasses() {
        return academicClassRepository.findAll();
    }
}
