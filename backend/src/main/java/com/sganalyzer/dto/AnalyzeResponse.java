package com.sganalyzer.dto;

import java.util.List;
import java.util.Map;

public class AnalyzeResponse {
    private String message;

    private List<String> resumeSkills;
    private List<String> requiredSkills;
    private List<String> missingSkills;
    private List<String> matchedSkills;

    // now: list of {skill, url}
    private List<Map<String, String>> suggestedCourses;

    private String aiSummary;

    // ✅ Full constructor for convenience
    public AnalyzeResponse(String message,
                           List<String> resumeSkills,
                           List<String> requiredSkills,
                           List<String> matchedSkills,
                           List<String> missingSkills,
                           List<Map<String, String>> suggestedCourses,
                           String aiSummary) {
        this.message = message;
        this.resumeSkills = resumeSkills;
        this.requiredSkills = requiredSkills;
        this.matchedSkills = matchedSkills;
        this.missingSkills = missingSkills;
        this.suggestedCourses = suggestedCourses;
        this.aiSummary = aiSummary;
    }

    // ✅ Empty constructor for flexibility
    public AnalyzeResponse() {}

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public List<String> getResumeSkills() { return resumeSkills; }
    public void setResumeSkills(List<String> resumeSkills) { this.resumeSkills = resumeSkills; }

    public List<String> getRequiredSkills() { return requiredSkills; }
    public void setRequiredSkills(List<String> requiredSkills) { this.requiredSkills = requiredSkills; }

    public List<String> getMissingSkills() { return missingSkills; }
    public void setMissingSkills(List<String> missingSkills) { this.missingSkills = missingSkills; }

    public List<String> getMatchedSkills() { return matchedSkills; }
    public void setMatchedSkills(List<String> matchedSkills) { this.matchedSkills = matchedSkills; }

    public List<Map<String, String>> getSuggestedCourses() { return suggestedCourses; }
    public void setSuggestedCourses(List<Map<String, String>> suggestedCourses) { this.suggestedCourses = suggestedCourses; }

    public String getAiSummary() { return aiSummary; }
    public void setAiSummary(String aiSummary) { this.aiSummary = aiSummary; }
}
