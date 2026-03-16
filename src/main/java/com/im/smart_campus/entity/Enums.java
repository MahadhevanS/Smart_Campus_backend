package com.im.smart_campus.entity;

public class Enums {
    public enum UserRole {
        RAISER,      // Can be Student, Staff, or Guest
        ADMIN,       // Assigns the work
        TECHNICIAN   // Performs the work
    }

    public enum IssueStatus {
        REPORTED,        // Initial state
        ASSIGNED,        // Admin has assigned to a Tech
        WORK_IN_PROGRESS, // Tech has started
        RESOLVED,        // Tech has finished
        CLOSED           // Raiser has acknowledged
    }

    public enum IssueCategory {
        IT_INFRA, ELECTRICAL, PLUMBING, CIVIL, OTHER
    }
}