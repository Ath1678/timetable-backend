package com.timetable.timetable_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.timetable.timetable_backend.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
