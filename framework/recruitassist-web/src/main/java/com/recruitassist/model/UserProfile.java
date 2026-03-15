package com.recruitassist.model;

import java.util.ArrayList;
import java.util.List;

public class UserProfile {
    private String userId;
    private String username;
    private String password;
    private UserRole role;
    private String name;
    private String studentId;
    private String programme;
    private List<String> skills = new ArrayList<>();
    private String availability;
    private String experience;
    private String cvText;

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username == null ? "" : username;
    }

    public String getPassword() {
        return password == null ? "" : password;
    }

    public UserRole getRole() {
        return role == null ? UserRole.TA : role;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public String getStudentId() {
        return studentId == null ? "" : studentId;
    }

    public String getProgramme() {
        return programme == null ? "" : programme;
    }

    public List<String> getSkills() {
        return skills == null ? List.of() : skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public String getAvailability() {
        return availability == null ? "" : availability;
    }

    public String getExperience() {
        return experience == null ? "" : experience;
    }

    public String getCvText() {
        return cvText == null ? "" : cvText;
    }

    public String getRoleLabel() {
        return getRole().getLabel();
    }

    public String getSkillsSummary() {
        return getSkills().isEmpty() ? "No skills listed yet" : String.join(", ", getSkills());
    }
}
