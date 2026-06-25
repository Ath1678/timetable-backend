package com.timetable.timetable_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.timetable.timetable_backend.entity.Institute;
import java.util.Optional;

public interface InstituteRepository extends JpaRepository<Institute, Long> {
    Optional<Institute> findByCode(String code);

    boolean existsByCode(String code);

    boolean existsByName(String name);

}
