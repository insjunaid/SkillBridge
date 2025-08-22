package com.sganalyzer.dto;

public class JobRoleDTO {
    private Long id;
    private String title;

    public JobRoleDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
}
