package com.timetable.timetable_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.timetable.timetable_backend.entity.TimeSlot;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
}
