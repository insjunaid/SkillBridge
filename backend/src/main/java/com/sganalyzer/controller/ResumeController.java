package com.sganalyzer.controller;

import com.sganalyzer.model.Resume;
import com.sganalyzer.repository.ResumeRepository;
import com.sganalyzer.util.PdfTextExtractor;
import com.sganalyzer.util.DocxTextExtractor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private final ResumeRepository repo;

    public ResumeController(ResumeRepository repo) {
        this.repo = repo;
    }

    // ✅ Text-based upload (MVP fallback)
    @PostMapping
    public Map<String, Object> create(@RequestBody Map<String, String> body) {
        String text = body.get("text");
        if (text == null || text.isBlank()) {
            throw new RuntimeException("Resume text is required (MVP)");
        }
        Resume r = repo.save(new Resume(text));
        return Map.of("id", r.getId());
    }

    // ✅ File-based upload (PDF/DOCX)
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String text = extractTextFromFile(file);
            Resume r = repo.save(new Resume(text));
            return ResponseEntity.ok(Map.of(
                "id", r.getId(),
                "resumeText", r.getText()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    private String extractTextFromFile(MultipartFile file) throws Exception {
        String filename = file.getOriginalFilename().toLowerCase();
        InputStream inputStream = file.getInputStream();

        if (filename.endsWith(".pdf")) {
            return PdfTextExtractor.extract(inputStream);
        } else if (filename.endsWith(".docx")) {
            return DocxTextExtractor.extract(inputStream);
        } else if (filename.endsWith(".doc")) {
            throw new IllegalArgumentException("Legacy .doc not supported. Please upload .docx or .pdf");
        } else {
            throw new IllegalArgumentException("Unsupported file type: " + filename);
        }
    }
}
