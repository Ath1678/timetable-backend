package com.timetable.timetable_backend.dto;

public class TeacherDTO {
    private String name;
    private String email;
    private Long departmentId;
    private Integer maxLoad;
    private String semester;

    public TeacherDTO() {}

    public TeacherDTO(String name, String email, Long departmentId, Integer maxLoad, String semester) {
        this.name = name;
        this.email = email;
        this.departmentId = departmentId;
        this.maxLoad = maxLoad;
        this.semester = semester;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getMaxLoad() {
        return maxLoad;
    }

    public void setMaxLoad(Integer maxLoad) {
        this.maxLoad = maxLoad;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
