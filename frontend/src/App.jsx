import React, { useState } from 'react'
import Header from './components/Header.jsx'
import Footer from './components/Footer.jsx'
import JobRoleSelector from './components/JobRoleSelector.jsx'
import ResumeUpload from './components/ResumeUpload.jsx'
import SkillGapChart from './components/SkillGapChart.jsx'
import SuggestedCourses from './components/SuggestedCourses.jsx'
import AnalyzeButton from './components/AnalyzeButton.jsx'
import SkillGapSkills from './components/SkillGapSkills.jsx'
import { analyze } from './services/api.js'
import './styles/SkillGap.css'
import './styles/Sections.css' // Add this for About & Contact styling

export default function App() {
  const [jobRoleTitle, setJobRoleTitle] = useState("")
  const [resumeData, setResumeData] = useState({ resumeId: null, resumeText: "" })
  const [result, setResult] = useState(null)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  const onAnalyze = async () => {
    setError('')
    setResult(null)

    if (!jobRoleTitle.trim()) {
      setError('Please enter a job role')
      return
    }

    if (!resumeData.resumeId && !resumeData.resumeText.trim()) {
      setError('Please upload a resume or paste resume text')
      return
    }

    try {
      setLoading(true)
      const res = await analyze({
        jobRoleTitle,
        resumeId: resumeData.resumeId,
        resumeText: resumeData.resumeId ? null : resumeData.resumeText
      })
      setResult(res)
    } catch (e) {
      setError('Analysis failed')
    } finally {
      setLoading(false)
    }
  }

  return (
    <>
      <Header />
        <div className="container">
  <h1 className="tool-title">SkillBridge</h1>
  <p className="tool-subtitle">
    Enter job role, upload/paste resume, and analyze with AI.
  </p>

        {error && <div className="error">{error}</div>}

        <JobRoleSelector onSelect={setJobRoleTitle} />
        <ResumeUpload value={resumeData} onChange={setResumeData} />

        <AnalyzeButton className="analyze-btn" onClick={onAnalyze} disabled={loading}>
          {loading ? 'Analyzing...' : 'Analyze Skill Gap'}
        </AnalyzeButton>

        <div className={`results-section ${result ? 'visible' : 'hidden'}`}>
          {result && (
            <>
              <h2>Results</h2>

              <SkillGapSkills
                matched={result.matchedSkills}
                missing={result.missingSkills}
              />

              <SkillGapChart
                matched={result.matchedSkills}
                missing={result.missingSkills}
              />

              <SuggestedCourses courses={result.suggestedCourses} />
            </>
          )}
        </div>
      </div>

     <div id="about" className="container about-section">
  <h2>About SkillBridge</h2>
  <p>
    SkillBridge is an AI-powered career assistant that helps job seekers identify skill gaps
    based on their resume and target job role. It analyzes your strengths, highlights missing
    skills, and recommends curated courses to bridge the gap — making your career transition
    smarter and more confident.
  </p>
</div>

<div id="contact" className="container contact-section">
  <h2>Contact</h2>
  <p>Created by <strong>Junaid</strong></p>
  <p>GitHub: <a href="https://github.com/insjunaid" target="_blank">Junaid Shariff</a></p>
  <p>Email: <a href="mailto:shariffjunaid2004@gmail.com">shariffjunaid2004@gmail.com</a></p>
</div>


      <Footer />
    </>
  )
}
