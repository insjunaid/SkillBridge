import React, { useState } from 'react'
import '../styles/SkillGap.css' // Make sure this includes the styles below

export default function JobRoleSelector({ onSelect }) {
  const [role, setRole] = useState("")

  return (
    <div className="jobrole-section">
      <h3 className="jobrole-title">Enter Target Job Role</h3>
      <input
        type="text"
        value={role}
        onChange={e => {
          setRole(e.target.value)
          onSelect(e.target.value)
        }}
        placeholder="e.g. Java Developer, Cloud Engineer"
        className="jobrole-input"
      />
    </div>
  )
}
