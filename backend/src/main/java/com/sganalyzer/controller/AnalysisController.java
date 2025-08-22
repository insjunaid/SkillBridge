package com.sganalyzer.controller;

import com.sganalyzer.dto.AnalyzeRequest;
import com.sganalyzer.dto.AnalyzeResponse;
import com.sganalyzer.service.AnalysisService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/analyze")
public class AnalysisController {

    private final AnalysisService analysisService;

    public AnalysisController(AnalysisService analysisService) {
        this.analysisService = analysisService;
    }

    @PostMapping
    public AnalyzeResponse analyze(@Valid @RequestBody AnalyzeRequest req) {
        return analysisService.analyze(req);
    }
}
