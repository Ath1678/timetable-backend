package com.timetable.timetable_backend.service;

import com.timetable.timetable_backend.entity.Timetable;
import com.timetable.timetable_backend.repository.TimetableRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimetableServiceImpl implements TimetableService {

    private final TimetableRepository repository;

    public TimetableServiceImpl(TimetableRepository repository) {
        this.repository = repository;
    }

    @Override
    public Timetable save(Timetable timetable) {

        // 1. Check if the class is already booked for this time
        boolean classConflict = repository.existsByClassNameAndDayAndPeriod(
                timetable.getClassName(),
                timetable.getDay(),
                timetable.getPeriod());

        if (classConflict) {
            throw new RuntimeException("Class " + timetable.getClassName() + " is already occupied at this time.");
        }

        // 2. Check if the Teacher is already booked
        if (timetable.getTeacher() != null && !timetable.getTeacher().isEmpty()) {
            boolean teacherConflict = repository.existsByTeacherAndDayAndPeriod(
                    timetable.getTeacher(),
                    timetable.getDay(),
                    timetable.getPeriod());
            if (teacherConflict) {
                throw new RuntimeException("Teacher " + timetable.getTeacher() + " is already busy at this time.");
            }
        }

        // 3. Check if the Room is already booked
        if (timetable.getRoom() != null && !timetable.getRoom().isEmpty()) {
            boolean roomConflict = repository.existsByRoomAndDayAndPeriod(
                    timetable.getRoom(),
                    timetable.getDay(),
                    timetable.getPeriod());
            if (roomConflict) {
                throw new RuntimeException("Room " + timetable.getRoom() + " is already occupied at this time.");
            }
        }

        return repository.save(timetable);
    }

    @Override
    public List<Timetable> getByClassName(String className) {
        return repository.findByClassName(className);
    }
}
