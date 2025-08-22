package com.sganalyzer.service;

import com.sganalyzer.ai.GeminiAiClient;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SkillExtractorService {

    private final GeminiAiClient geminiAiClient;
    private final Map<String, List<String>> cache = new HashMap<>();

    public SkillExtractorService(GeminiAiClient geminiAiClient) {
        this.geminiAiClient = geminiAiClient;
    }

    public List<String> extractSkills(String roleTitle) {
        String normalizedRole = normalizeRoleTitle(roleTitle);

        if (cache.containsKey(normalizedRole)) {
            return cache.get(normalizedRole);
        }

        List<String> skills = geminiAiClient.getRequiredSkillsForRole(normalizedRole);
        cache.put(normalizedRole, skills);
        return skills;
    }

    private String normalizeRoleTitle(String role) {
        return role.trim().toLowerCase().replaceAll("[^a-z ]", "");
    }
}
