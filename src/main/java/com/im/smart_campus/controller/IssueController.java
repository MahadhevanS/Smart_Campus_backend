package com.im.smart_campus.controller;

import com.im.smart_campus.entity.Enums;
import com.im.smart_campus.entity.Issue;
import com.im.smart_campus.dto.IssueDTO;
import com.im.smart_campus.service.IssueService;
import com.im.smart_campus.entity.Profile;
import com.im.smart_campus.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private ProfileRepository profileRepository;

    @GetMapping
    public List<Issue> getIssues(@RequestHeader(value = "User-ID", required = false) UUID userId) {
        // Log the incoming ID to your terminal for debugging
        System.out.println("Fetching issues for User-ID: " + userId);

        if (userId != null) {
            return profileRepository.findById(userId)
                .map(profile -> {
                    // If they are a technician, show their assigned tasks
                    if (Enums.UserRole.TECHNICIAN.equals(profile.getRole())) {
                        return issueService.getIssuesByTechnician(userId);
                    }
                    // Otherwise (Raiser/Student), show their reported issues
                    return issueService.getIssuesByStudent(userId);
                })
                // If the profile doesn't exist in the DB yet, return student view (empty list usually)
                .orElseGet(() -> issueService.getIssuesByStudent(userId));
        }
        
        return issueService.getAllIssues();
    }
    
    @PostMapping
    public ResponseEntity<Issue> createIssue(
        @RequestBody IssueDTO issueDto, 
        @RequestHeader(value = "User-ID", required = false) UUID userId 
    ) {
        // If no header is provided, we can't link it, so handle accordingly
        if (userId == null) {
            // For now, let's use a dummy ID or return an error
            return ResponseEntity.badRequest().build();        
        }
        return ResponseEntity.ok(issueService.reportIssue(issueDto, userId));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Issue> updateStatus(
        @PathVariable Long id, 
        @RequestParam Enums.IssueStatus status
    ) {
        // We reuse our service logic to update the status
        return ResponseEntity.ok(issueService.updateStatus(id, status));
    }

    @GetMapping("/technicians")
    public List<Profile> getTechnicians() {
        return issueService.getTechnicians();
    }

    @PatchMapping("/{id}/assign")
    public ResponseEntity<Issue> assignIssue(
        @PathVariable Long id, 
        @RequestParam UUID technicianId
    ) {
        return ResponseEntity.ok(issueService.assignIssue(id, technicianId));
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<Issue> closeIssue(@PathVariable Long id) {
        return ResponseEntity.ok(issueService.closeIssue(id));
    }
}