package com.sganalyzer.service;

import com.sganalyzer.ai.GeminiAiClient;
import com.sganalyzer.dto.AnalyzeRequest;
import com.sganalyzer.dto.AnalyzeResponse;
import com.sganalyzer.model.Resume;
import com.sganalyzer.repository.ResumeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AnalysisService {

    private static final Logger log = LoggerFactory.getLogger(AnalysisService.class);

    private final ResumeRepository resumeRepository;
    private final GeminiAiClient aiClient;
    private final CourseSuggestionService courseSuggestionService;

    public AnalysisService(ResumeRepository resumeRepository,
                           GeminiAiClient aiClient,
                           CourseSuggestionService courseSuggestionService) {
        this.resumeRepository = resumeRepository;
        this.aiClient = aiClient;
        this.courseSuggestionService = courseSuggestionService;
    }

    @Transactional(readOnly = true)
    public AnalyzeResponse analyze(AnalyzeRequest req) {
        if (req.getJobRoleTitle() == null || req.getJobRoleTitle().isBlank()) {
            throw new RuntimeException("Job role title is required");
        }

        String jobRoleTitle = req.getJobRoleTitle().trim();
        List<String> requiredSkills = aiClient.getRequiredSkillsForRole(jobRoleTitle);
        log.info("Required skills for '{}': {}", jobRoleTitle, requiredSkills);

        if (requiredSkills == null || requiredSkills.isEmpty()) {
            log.warn("No required skills returned for '{}'. Aborting analysis.", jobRoleTitle);
            return new AnalyzeResponse(
                "Analysis failed",
                List.of(),              // resumeSkills
                List.of(),              // requiredSkills
                List.of(),              // matchedSkills
                List.of(),              // missingSkills
                List.of(),              // suggestedCourses
                ""                      // aiSummary
            );
        }

        String resumeText;
        if (req.getResumeId() != null) {
            Resume r = resumeRepository.findById(req.getResumeId())
                    .orElseThrow(() -> new RuntimeException("Resume not found"));
            resumeText = r.getText();
        } else if (req.getResumeText() != null && !req.getResumeText().isBlank()) {
            resumeText = req.getResumeText();
        } else {
            throw new RuntimeException("Provide resumeId or resumeText");
        }

        List<String> resumeSkills = aiClient.extractSkills(resumeText);
        log.info("Extracted resume skills: {}", resumeSkills);

        if (resumeSkills == null || resumeSkills.isEmpty()) {
            log.warn("No resume skills extracted. Aborting analysis.");
            return new AnalyzeResponse(
                "Analysis failed",
                List.of(),              // resumeSkills
                requiredSkills,         // requiredSkills
                List.of(),              // matchedSkills
                requiredSkills,         // missingSkills
                List.of(),              // suggestedCourses
                ""                      // aiSummary
            );
        }

        Set<String> have = new HashSet<>(resumeSkills);
        Set<String> need = new HashSet<>(requiredSkills);

        Set<String> matched = new HashSet<>(have);
        matched.retainAll(need);

        Set<String> missing = new HashSet<>(need);
        missing.removeAll(have);

        AnalyzeResponse resp = new AnalyzeResponse(
            "Analysis complete",
            new ArrayList<>(have).stream().sorted().toList(),
            new ArrayList<>(need).stream().sorted().toList(),
            matched.stream().sorted().toList(),
            missing.stream().sorted().toList(),
            courseSuggestionService.suggestForMissingSkills(new ArrayList<>(missing)),
            aiClient.analyzeGapSummary(new ArrayList<>(have), new ArrayList<>(need))
        );

        log.info("Analysis complete. Matched: {}, Missing: {}", matched, missing);
        return resp;
    }
}
