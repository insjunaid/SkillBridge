import React, { useRef, useState } from "react";
import { uploadResume } from "../services/api";

export default function ResumeUpload({ value, onChange }) {
  const fileInputRef = useRef(null);
  const [fileName, setFileName] = useState("");

  const handleFileUpload = async (e) => {
    const file = e.target.files[0];
    if (!file) return;

    setFileName(file.name);

    try {
      const data = await uploadResume(file);
      onChange({ resumeId: data.id, resumeText: null });
    } catch (err) {
      alert(err.message || "Failed to extract resume text. Try again.");
    }
  };

  const handleTextChange = (e) => {
    onChange({ resumeId: null, resumeText: e.target.value });
  };

  const triggerFileInput = () => {
    if (fileInputRef.current) fileInputRef.current.click();
  };

  return (
    <div className="resume-section">
      <div className="upload-wrapper">
        <div className="upload-box" onClick={triggerFileInput}>
          <span className="plus-icon">+</span>
          <p>Upload Resume</p>
        </div>
        {fileName && <p className="file-name-inline">Selected: {fileName}</p>}
      </div>

      <input
        type="file"
        accept=".pdf,.doc,.docx"
        ref={fileInputRef}
        onChange={handleFileUpload}
        style={{ display: "none" }}
      />

      <div className="paste-box">
        <h3>Not built a resume yet? Don’t worry. Just paste the skills you know</h3>
        <textarea
          placeholder="List your skills line by line"
          value={value?.resumeText || ""}
          onChange={handleTextChange}
          rows={6}
        />
      </div>
    </div>
  );
}
