// frontend/src/services/api.js
const API_BASE = import.meta.env.VITE_API_URL || "http://backend:8080";


export async function fetchJobRoles() {
  const res = await fetch(`${API_BASE}/api/job-roles`);
  if (!res.ok) throw new Error("Failed to fetch job roles");
  return res.json();
}

export async function analyze(payload) {
  const res = await fetch(`${API_BASE}/api/analyze`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(payload),
  });
  if (!res.ok) {
    const err = await res.json().catch(() => ({}));
    throw new Error(err.error || "Failed to analyze resume");
  }
  return res.json();
}

export async function uploadResume(file) {
  const formData = new FormData();
  formData.append("file", file);

  const res = await fetch(`${API_BASE}/api/resumes/upload`, {
    method: "POST",
    body: formData,
  });

  if (!res.ok) {
    const err = await res.json().catch(() => ({}));
    throw new Error(err.error || "Failed to upload resume");
  }

  return res.json(); // { id, resumeText }
}

export async function fetchSkillsForRole(roleTitle) {
  const res = await fetch(`${API_BASE}/api/job-roles/skills?role=${encodeURIComponent(roleTitle)}`);
  if (!res.ok) throw new Error("Failed to fetch skills for role");
  return res.json();
}
