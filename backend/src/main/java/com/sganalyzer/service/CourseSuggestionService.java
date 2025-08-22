package com.sganalyzer.service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CourseSuggestionService {

    // Return skill + url so UI can label nicely
    public List<Map<String, String>> suggestForMissingSkills(List<String> missingSkills) {
        List<Map<String, String>> suggestions = new ArrayList<>();
        for (String s : missingSkills) {
            String q = s.trim().replaceAll("\\s+", "+");
            String url = "https://www.youtube.com/results?search_query=" + q + "+tutorial";
            Map<String, String> m = new HashMap<>();
            m.put("skill", s);
            m.put("url", url);
            suggestions.add(m);
        }
        return suggestions;
    }
}
