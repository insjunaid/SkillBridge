package com.sganalyzer.repository;

import com.sganalyzer.model.JobRole;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobRoleRepository extends JpaRepository<JobRole, Long> {

    /**
     * Search job roles by partial title match (case-insensitive).
     * Useful for autocomplete or keyword-based filtering.
     */
    List<JobRole> findByTitleContainingIgnoreCase(String query);

    /**
     * Exact match by title (optional, for validation or deduplication).
     */
    JobRole findByTitleIgnoreCase(String title);
}
