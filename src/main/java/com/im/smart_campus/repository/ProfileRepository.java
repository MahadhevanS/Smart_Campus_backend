package com.im.smart_campus.repository;

import com.im.smart_campus.entity.Enums;
import com.im.smart_campus.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
    
    Optional<Profile> findByRollNo(String rollNo);

    List<Profile> findByRole(Enums.UserRole role);
}