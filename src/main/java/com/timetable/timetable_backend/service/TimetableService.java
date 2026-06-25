package com.timetable.timetable_backend.service;

import com.timetable.timetable_backend.entity.Timetable;
import java.util.List;

public interface TimetableService {

    Timetable save(Timetable timetable);

    List<Timetable> getByClassName(String className);
}
