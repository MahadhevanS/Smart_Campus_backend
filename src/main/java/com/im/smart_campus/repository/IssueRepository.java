package com.im.smart_campus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.im.smart_campus.entity.Enums;
import com.im.smart_campus.entity.Issue;

import java.util.List;
import java.util.UUID;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    
    // 1. Existing methods (Spring generates these automatically)
    List<Issue> findByReportedBy(UUID studentId);
    List<Issue> findByCategory(Enums.IssueCategory category);
    List<Issue> findByStatus(Enums.IssueStatus status);
    List<Issue> findByAssignedTo(UUID technicianId);
    
    // 2. Get all issues sorted by date (Newest first)
    List<Issue> findAllByOrderByCreatedAtDesc();

    // 3. Find issues assigned to a technician that are still OPEN
    List<Issue> findByAssignedToAndStatusNot(UUID technicianId, Enums.IssueStatus status);

    // 4. Custom query to update status (Very useful for the Technician Dashboard)
    @Transactional
    @Modifying
    @Query("UPDATE Issue i SET i.status = :status, i.updatedAt = CURRENT_TIMESTAMP WHERE i.id = :id")
    int updateIssueStatus(Long id, Enums.IssueStatus status);
}