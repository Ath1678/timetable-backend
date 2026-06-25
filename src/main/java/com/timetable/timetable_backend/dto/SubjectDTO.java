package com.timetable.timetable_backend.dto;

public class SubjectDTO {
    private String name;
    private String code;
    private Long departmentId;
    private Long teacherId;

    public SubjectDTO() {}

    public SubjectDTO(String name, String code, Long departmentId, Long teacherId) {
        this.name = name;
        this.code = code;
        this.departmentId = departmentId;
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
}
