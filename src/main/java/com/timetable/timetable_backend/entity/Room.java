package com.timetable.timetable_backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String roomNumber;

    // ===== Getters & Setters =====

    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public String getRoomNumber() {
        return roomNumber;
    }
 
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
