package com.sganalyzer.dto;

public class AnalyzeRequest {

    // Instead of jobRoleId from DB, now we pass the job role title as plain text
    private String jobRoleTitle;

    // Either use resumeId (already uploaded) or inline text for quick test
    private Long resumeId;
    private String resumeText;

    public String getJobRoleTitle() {
        return jobRoleTitle;
    }
    public void setJobRoleTitle(String jobRoleTitle) {
        this.jobRoleTitle = jobRoleTitle;
    }

    public Long getResumeId() {
        return resumeId;
    }
    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }

    public String getResumeText() {
        return resumeText;
    }
    public void setResumeText(String resumeText) {
        this.resumeText = resumeText;
    }
}
