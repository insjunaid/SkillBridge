package com.sganalyzer.ai;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.*;
import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Primary
@Component
public class GeminiAiClient implements AiClient {

    private static final Logger log = LoggerFactory.getLogger(GeminiAiClient.class);

    @Value("${ai.api.key}")
    private String apiKey;

    @Value("${ai.api.url}")
    private String apiUrl;

    private final ObjectMapper mapper = new ObjectMapper();

    private static final Set<String> BANNED = Set.of(
        "communication", "teamwork", "problem solving", "leadership", "creativity"
    );

    private static final Map<String, List<String>> ROLE_FALLBACK = Map.ofEntries(
        Map.entry("java developer", List.of("java", "spring boot", "hibernate", "jpa", "maven", "junit", "rest api", "git", "sql")),
        Map.entry("python developer", List.of("python", "flask", "django", "pandas", "numpy", "sql", "git", "rest api")),
        Map.entry("machine learning engineer", List.of("python", "scikit-learn", "tensorflow", "pytorch", "mlops", "data preprocessing", "model deployment")),
        Map.entry("gen ai engineer", List.of("prompt engineering", "llm tuning", "langchain", "openai api", "gemini api", "vector databases", "rag", "embedding models")),
        Map.entry("software engineer", List.of("data structures", "algorithms", "git", "oop", "sql", "rest api", "testing", "design patterns")),
        Map.entry("product manager", List.of("roadmapping", "user research", "agile", "jira", "analytics", "stakeholder management", "market research")),
        Map.entry("android developer", List.of("java", "kotlin", "android studio", "xml", "firebase", "mvvm", "jetpack compose")),
        Map.entry("frontend developer", List.of("html", "css", "javascript", "react", "redux", "typescript", "git", "webpack")),
        Map.entry("fullstack developer", List.of("html", "css", "javascript", "react", "node.js", "java", "spring boot", "sql", "git", "docker")),
        Map.entry("data scientist", List.of("python", "pandas", "numpy", "matplotlib", "scikit-learn", "sql", "machine learning", "statistics")),
        Map.entry("ui ux designer", List.of("figma", "adobe xd", "wireframing", "prototyping", "user research", "interaction design", "accessibility")),
        Map.entry("qa engineer", List.of("selenium", "junit", "testng", "postman", "cypress", "manual testing", "automation testing")),
        Map.entry("cybersecurity analyst", List.of("network security", "firewalls", "penetration testing", "siem", "incident response", "encryption")),
        Map.entry("database administrator", List.of("sql", "mysql", "postgresql", "oracle", "backup and recovery", "performance tuning")),
        Map.entry("system administrator", List.of("linux", "windows server", "bash", "powershell", "networking", "monitoring", "virtualization")),
        Map.entry("cloud engineer", List.of("aws", "azure", "docker", "kubernetes", "linux", "terraform", "ci/cd", "networking")),
        Map.entry("devops engineer", List.of("linux", "docker", "kubernetes", "terraform", "aws", "git", "ci/cd", "jenkins", "monitoring")),
        Map.entry("accountant", List.of("tally", "excel", "quickbooks", "taxation", "audit", "financial reporting", "sap", "ms office")),
        Map.entry("business analyst", List.of("sql", "excel", "tableau", "power bi", "requirement gathering", "data analysis")),
        Map.entry("ai prompt engineer", List.of("prompt engineering", "llm tuning", "langchain", "openai api", "gemini api", "vector databases", "embedding models", "retrieval augmented generation", "json schema", "python", "pydantic"))
    );

    @Override
    public List<String> extractSkills(String resumeText) {
        String prompt = """
            Extract only core technical/professional skills from this resume.
            Return strictly in JSON:
            { "skills": ["java", "spring boot", "react", "mysql"] }

            Resume:
            """ + resumeText;

        String response = callGemini(prompt);
        List<String> parsed = parseSkillsFromResponse(response);

        if (parsed.isEmpty()) {
            parsed = Arrays.stream(resumeText.split("[,\\n;:.]"))
                    .map(String::trim)
                    .filter(s -> s.length() > 1)
                    .filter(s -> s.split("\\s+").length <= 3)
                    .map(this::canonicalize)
                    .filter(s -> !s.isBlank())
                    .distinct()
                    .sorted()
                    .toList();
        }
        return parsed;
    }

   @Override
public List<String> getRequiredSkillsForRole(String roleTitle) {
    String prompt = """
    You are an expert job market analyst.
    Identify ONLY the top 15 most important technical and domain-specific skills required for the role: "%s".
    - Avoid soft skills like communication, teamwork, leadership, creativity.
    - Do not include meaningless terms like "JSON", "JS", "E", "G".
    - If the role is Java-related, focus on backend technologies (Spring Boot, Hibernate, REST API, JPA, SQL, Maven, JUnit, Git, Docker).
    Return strictly valid JSON in this format:
    { "skills": ["skill1", "skill2", ..., "skill15"] }
    """.formatted(roleTitle);

    String response = callGemini(prompt);
    log.debug("Gemini raw response for '{}': {}", roleTitle, response);

    List<String> originalParsed = parseSkillsFromResponse(response);
    List<String> parsed = filterSoftSkills(cleanSkills(originalParsed));

    log.debug("Original parsed skills: {}", originalParsed);
    log.debug("Filtered parsed skills: {}", parsed);

    if (parsed.size() > 15) {
        parsed = parsed.subList(0, 15);
    }
    if (originalParsed.isEmpty() && parsed.isEmpty()) {
        log.warn("Gemini returned empty or unusable skills for '{}'. Using fallback.", roleTitle);
        return getFallbackSkills(roleTitle);
    }

    return parsed;
}

/**
 * Cleans garbage entries like "G", "E", "JSON", "SKILLS"
 * Also lowercases everything for consistent comparison.
 */
private List<String> cleanSkills(List<String> skills) {
    return skills.stream()
        .map(s -> s.toLowerCase().trim())
        .filter(s -> s.length() > 2)             // removes junk like "g", "e"
        .filter(s -> !s.equals("json"))          // removes stray "json"
        .filter(s -> !s.equals("skills"))        // removes stray "skills"
        .distinct()
        .toList();
}

/**
 * Fallback skills for known roles (normalized).
 */
private List<String> getFallbackSkills(String roleTitle) {
    String role = normalizeRoleTitle(roleTitle);

    if (ROLE_FALLBACK.containsKey(role)) return ROLE_FALLBACK.get(role);

    if (role.contains("java")) {
        return List.of("java", "spring boot", "hibernate", "jpa", "rest api",
                       "maven", "junit", "sql", "git", "docker");
    }
    if (role.contains("fullstack")) {
        return List.of("html", "css", "javascript", "react", "node.js", "java",
                       "spring boot", "sql", "git", "docker");
    }
    if (role.contains("web")) {
        return List.of("html", "css", "javascript", "react", "node.js", "git", "rest api", "sql");
    }
    if (role.contains("frontend")) {
        return List.of("html", "css", "javascript", "react", "redux", "typescript", "git");
    }
    if (role.contains("data")) {
        return List.of("python", "sql", "pandas", "numpy", "machine learning", "statistics", "excel");
    }
    if (role.contains("mobile")) {
        return List.of("java", "kotlin", "swift", "flutter", "react native", "android sdk", "ios sdk");
    }

    // Generic fallback
    return List.of();
}

/**
 * Normalize skills list for matching.
 * Example: "PYTHON " → "python"
 */
private List<String> normalizeSkills(List<String> skills) {
    return skills.stream()
        .map(s -> s.toLowerCase().trim())
        .filter(s -> !s.isEmpty())
        .distinct()
        .toList();
}

    @Override
    public String analyzeGapSummary(List<String> resumeSkills, List<String> requiredSkills) {
        String prompt = """
            Resume skills: %s
            Required skills: %s
            Write a concise 2–3 sentence summary of matches and gaps, plus one actionable tip.
            """.formatted(resumeSkills, requiredSkills);
        return callGemini(prompt);
    }

    private List<String> filterSoftSkills(List<String> skills) {
        return skills.stream()
                .filter(s -> !BANNED.contains(s.toLowerCase()))
                .toList();
    }

    private boolean isMostlySoftSkills(List<String> original, List<String> filtered) {
        return filtered.size() < original.size() / 2;
    }

    private String normalizeRoleTitle(String role) {
        if (role == null) return "";
        role = role.toLowerCase().trim();

        Map<String, String> aliases = Map.ofEntries(
            Map.entry("ml engineer", "machine learning engineer"),
            Map.entry("gen ai engineer", "gen ai engineer"),
                        Map.entry("ai engineer", "gen ai engineer"),
            Map.entry("python dev", "python developer"),
            Map.entry("android dev", "android developer"),
            Map.entry("pm", "product manager"),
            Map.entry("software dev", "software engineer"),
            Map.entry("frontend dev", "frontend developer"),
            Map.entry("backend dev", "backend developer"),
            Map.entry("full stack developer", "fullstack developer"),
            Map.entry("full-stack developer", "fullstack developer"),
            Map.entry("ui designer", "ui ux designer"),
            Map.entry("ux designer", "ui ux designer"),
            Map.entry("qa tester", "qa engineer"),
            Map.entry("security analyst", "cybersecurity analyst"),
            Map.entry("db admin", "database administrator"),
            Map.entry("sysadmin", "system administrator")
        );

        return aliases.getOrDefault(role, role);
    }

    private String canonicalize(String s) {
        String t = s.toLowerCase(Locale.ROOT);
        t = t.replaceAll("[^a-z0-9\\+\\# ]", " ");
        t = t.replaceAll("\\s+", " ").trim();
        t = t.replace("springboot", "spring boot").replace("restful api", "rest api");
        return t;
    }

    private List<String> parseSkillsFromResponse(String response) {
        try {
            if (response == null || response.isBlank()) return List.of();

            String cleaned = stripCodeFences(response.trim());

            JsonNode json = mapper.readTree(cleaned);
            if (json.has("skills") && json.get("skills").isArray()) {
                List<String> out = new ArrayList<>();
                for (JsonNode n : json.get("skills")) {
                    String v = canonicalize(n.asText());
                    if (!v.isBlank()) out.add(v);
                }
                return out.stream().distinct().sorted().toList();
            }
        } catch (Exception e) {
            log.error("Error parsing Gemini response", e);
        }

        return Arrays.stream(response.split("[,\\n;:.]"))
                .map(this::canonicalize)
                .filter(s -> !s.isBlank())
                .filter(s -> s.split("\\s+").length <= 3)
                .distinct()
                .sorted()
                .toList();
    }

    private String stripCodeFences(String s) {
        Pattern p = Pattern.compile("(?:json)?\\s*(.*?)", Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(s);
        if (m.find()) return m.group(1).trim();
        return s;
    }

    private String callGemini(String prompt) {
        try {
            String payload = mapper.writeValueAsString(
                Map.of("contents", List.of(Map.of("parts", List.of(Map.of("text", prompt)))))
            );

            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10))
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl + "?key=" + apiKey))
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofSeconds(30))
                    .POST(HttpRequest.BodyPublishers.ofString(payload))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() / 100 != 2) {
                log.error("Gemini HTTP {}: {}", response.statusCode(), response.body());
                return "";
            }

            JsonNode json = mapper.readTree(response.body());
            String text = json.at("/candidates/0/content/parts/0/text").asText("");
            if (text.isBlank()) {
                log.warn("Gemini returned no text. Raw body (truncated): {}",
                        response.body().length() > 800 ? response.body().substring(0, 800) + "…" : response.body());
            }
            return text;
        } catch (Exception e) {
            log.error("Error calling Gemini", e);
            return "";
        }
    }
}