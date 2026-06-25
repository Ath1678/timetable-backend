package com.timetable.timetable_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
    scanBasePackages = "com.timetable.timetable_backend"
)
public class TimetableBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimetableBackendApplication.class, args);
    }
}
