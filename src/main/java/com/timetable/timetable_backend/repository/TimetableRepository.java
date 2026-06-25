package com.timetable.timetable_backend.repository;

import com.timetable.timetable_backend.entity.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimetableRepository extends JpaRepository<Timetable, Long> {

        List<Timetable> findByClassName(String className);

        Timetable getByClassNameAndDayAndPeriod(
                        String className,
                        String day,
                        int period);

        boolean existsByClassNameAndDayAndPeriod(
                        String className,
                        String day,
                        int period);

        boolean existsByTeacherAndDayAndPeriod(
                        String teacher,
                        String day,
                        int period);

        boolean existsByRoomAndDayAndPeriod(
                        String room,
                        String day,
                        int period);
}
