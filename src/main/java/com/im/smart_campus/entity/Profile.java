package com.im.smart_campus.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Entity
@Table(name = "profiles")
@Data
public class Profile {

    @Id
    private UUID id; // This matches the Supabase Auth User ID

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "roll_no", unique = true)
    private String rollNo;

    @Column(name = "specialization")
    private String specialization;
    
    @Enumerated(EnumType.STRING)
    private Enums.UserRole role;

    private String department;
}