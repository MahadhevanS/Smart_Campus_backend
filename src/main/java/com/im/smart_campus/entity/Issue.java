package com.im.smart_campus.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "issues")
@Data
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private String location;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Enums.IssueCategory category;

    @Enumerated(EnumType.STRING)
    private Enums.IssueStatus status = Enums.IssueStatus.REPORTED;

    private UUID reportedBy; // Links to Profile ID (Raiser)

    private UUID assignedTo; // Links to Technician Profile ID (Only one declaration needed)

    private String imageUrl; // Kept this just in case, can be null

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    // Workflow timestamps
    private LocalDateTime assignedAt;
    private LocalDateTime resolvedAt;
    private LocalDateTime closedAt;
}