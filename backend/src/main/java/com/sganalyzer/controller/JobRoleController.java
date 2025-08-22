package com.sganalyzer.controller;

import com.sganalyzer.dto.JobRoleDTO;
import com.sganalyzer.repository.JobRoleRepository;
import com.sganalyzer.service.SkillExtractorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*") // Enable CORS for frontend integration
@RestController
@RequestMapping("/api/job-roles")
public class JobRoleController {

    private final JobRoleRepository repo;
    private final SkillExtractorService skillExtractorService;

    public JobRoleController(JobRoleRepository repo, SkillExtractorService skillExtractorService) {
        this.repo = repo;
        this.skillExtractorService = skillExtractorService;
    }

    /**
     * Return all job roles (id + title only).
     */
    @GetMapping
    public List<JobRoleDTO> list() {
        return repo.findAll().stream()
            .map(role -> new JobRoleDTO(role.getId(), role.getTitle()))
            .toList();
    }

    /**
     * Search job roles by partial title match.
     * Example: /api/job-roles/search?query=developer
     */
    @GetMapping("/search")
    public List<JobRoleDTO> search(@RequestParam("query") String query) {
        if (query == null || query.trim().isEmpty()) {
            return List.of(); // Return empty list for blank query
        }
        return repo.findByTitleContainingIgnoreCase(query).stream()
            .map(role -> new JobRoleDTO(role.getId(), role.getTitle()))
            .toList();
    }

    /**
     * Get technical skills for a given job role using Gemini AI.
     * Example: /api/job-roles/skills?role=python developer
     */
    @GetMapping("/skills")
    public List<String> getSkills(@RequestParam("role") String roleTitle) {
        if (roleTitle == null || roleTitle.trim().isEmpty()) {
            return List.of(); // Return empty list for blank role
        }
        return skillExtractorService.extractSkills(roleTitle);
    }
}
