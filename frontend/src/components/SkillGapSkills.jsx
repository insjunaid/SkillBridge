import React from 'react'
import '../styles/SkillGap.css'

export default function SkillGapSkills({ matched = [], missing = [] }) {
  const formatSkill = (skill) => skill.toUpperCase()

  const renderSkills = (skills, title, colorClass) => (
    <div className="skill-block">
      <h3 className={`skill-title ${colorClass}`}>{title}</h3>
      <div className="skill-grid">
        {skills.map((skill, index) => (
          <div key={index} className="skill-pill">
            {formatSkill(skill)}
          </div>
        ))}
      </div>
    </div>
  )

  return (
    <div className="skill-section">
      {renderSkills(matched, 'Skills Matched', 'matched')}
      {renderSkills(missing, 'Skills Missing', 'missing')}
    </div>
  )
}
