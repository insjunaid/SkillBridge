package com.sganalyzer.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "job_roles")
public class JobRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true, nullable=false)
    private String title; // e.g., "Java Developer"

    @ManyToMany
    @JoinTable(
            name = "job_role_skills",
            joinColumns = @JoinColumn(name="job_role_id"),
            inverseJoinColumns = @JoinColumn(name="skill_id")
    )
    private Set<Skill> requiredSkills = new HashSet<>();

    public JobRole() {}
    public JobRole(String title) { this.title = title; }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public Set<Skill> getRequiredSkills() { return requiredSkills; }

    public void setId(Long id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setRequiredSkills(Set<Skill> requiredSkills) { this.requiredSkills = requiredSkills; }
}
