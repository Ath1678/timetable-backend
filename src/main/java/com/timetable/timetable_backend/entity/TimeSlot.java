package com.timetable.timetable_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "time_slots")
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String startTime; // 09:00

    @Column(nullable = false)
    private String endTime;   // 10:00

    // ===== Getters & Setters =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }
 
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
 
    public String getEndTime() {
        return endTime;
    }
 
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}