package com.timetable.timetable_backend.dto;

public class DepartmentDTO {
    private String name;
    private String semester;

    public DepartmentDTO() {}

    public DepartmentDTO(String name, String semester) {
        this.name = name;
        this.semester = semester;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
