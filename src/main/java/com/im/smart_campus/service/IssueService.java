package com.im.smart_campus.service;

import com.im.smart_campus.dto.IssueDTO;
import com.im.smart_campus.entity.Enums;
import com.im.smart_campus.entity.Issue;
import com.im.smart_campus.entity.Profile;
import com.im.smart_campus.repository.IssueRepository;
import com.im.smart_campus.repository.ProfileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private ProfileRepository profileRepository;

    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    public List<Issue> getIssuesByStudent(UUID userId) {
        return issueRepository.findByReportedBy(userId);
    }

    public List<Issue> getIssuesByTechnician(UUID techId) {
        return issueRepository.findByAssignedTo(techId);
    }
    public Issue createIssue(Issue issue) {
        return issueRepository.save(issue);
    }

    public Issue reportIssue(IssueDTO dto, UUID userId) {
        Issue issue = new Issue();
        issue.setTitle(dto.getTitle());
        issue.setDescription(dto.getDescription());
        issue.setLocation(dto.getLocation());
        issue.setCategory(dto.getCategory());
        issue.setImageUrl(dto.getImageUrl());
        issue.setReportedBy(userId); 
        
        // Initialize timestamps
        issue.setCreatedAt(LocalDateTime.now());
        issue.setUpdatedAt(LocalDateTime.now());
        
        return issueRepository.save(issue);
    }
    
    public Issue updateStatus(Long id, Enums.IssueStatus status) {
        Issue issue = issueRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Issue not found"));
        
        issue.setStatus(status);
        issue.setUpdatedAt(LocalDateTime.now());
        return issueRepository.save(issue);
    }

    // 1. Get all technicians (for the Admin's dropdown)
    public List<Profile> getTechnicians() {
        return profileRepository.findByRole(Enums.UserRole.TECHNICIAN);
    }

    // 2. Assign issue to technician
    @Transactional
    public Issue assignIssue(Long issueId, UUID technicianId) {
        Issue issue = issueRepository.findById(issueId)
            .orElseThrow(() -> new RuntimeException("Issue not found"));
        
        issue.setAssignedTo(technicianId);
        issue.setStatus(Enums.IssueStatus.ASSIGNED);
        issue.setAssignedAt(LocalDateTime.now());
        return issueRepository.save(issue);
    }

    // 3. Raiser acknowledges (Close Issue)
    @Transactional
    public Issue closeIssue(Long issueId) {
        Issue issue = issueRepository.findById(issueId)
            .orElseThrow(() -> new RuntimeException("Issue not found"));
        
        issue.setStatus(Enums.IssueStatus.CLOSED);
        issue.setClosedAt(LocalDateTime.now());
        return issueRepository.save(issue);
    }
}