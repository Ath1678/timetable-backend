package com.timetable.timetable_backend.dto;

public class RegisterDTO {
    private String username;
    private String password;
    private String role; // "ADMIN", "TEACHER", "STUDENT"
    private String instituteName; // Required if creating a new institute (ADMIN)
    private String instituteCode; // Required if joining an existing institute

    // Constructors
    public RegisterDTO() {
    }

    public RegisterDTO(String username, String password, String role, String instituteName, String instituteCode) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.instituteName = instituteName;
        this.instituteCode = instituteCode;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getInstituteCode() {
        return instituteCode;
    }

    public void setInstituteCode(String instituteCode) {
        this.instituteCode = instituteCode;
    }

    @Override
    public String toString() {
        return "RegisterDTO{" +
                "username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", instituteName='" + instituteName + '\'' +
                ", instituteCode='" + instituteCode + '\'' +
                '}';
    }
}
