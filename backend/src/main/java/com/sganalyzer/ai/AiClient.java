package com.sganalyzer.ai;

import java.util.List;

public interface AiClient {
    /**
     * Extract skills from raw resume text.
     */
    List<String> extractSkills(String resumeText);
    List<String> getRequiredSkillsForRole(String roleTitle);

    /**
     * Given a resume's extracted skills and a job role's required skills,
     * return a human-readable summary and (optionally) improved list of skills.
     */
    String analyzeGapSummary(List<String> resumeSkills, List<String> requiredSkills);
}
