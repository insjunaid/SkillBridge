package com.sganalyzer.model;

import jakarta.persistence.*;

@Entity
@Table(name = "resumes")
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Store very large text (resume content)
    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String text;

    public Resume() {}
    public Resume(String text) { this.text = text; }

    public Long getId() { return id; }
    public String getText() { return text; }
    public void setId(Long id) { this.id = id; }
    public void setText(String text) { this.text = text; }
}
