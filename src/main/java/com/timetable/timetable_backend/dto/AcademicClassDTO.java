package com.timetable.timetable_backend.dto;

public class AcademicClassDTO {
    private String name;
    private Long departmentId;
    private String semester;

    public AcademicClassDTO() {}

    public AcademicClassDTO(String name, Long departmentId, String semester) {
        this.name = name;
        this.departmentId = departmentId;
        this.semester = semester;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
