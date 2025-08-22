import React from 'react'
import '../styles/SkillGap.css' // Make sure this includes the styles below

export default function SuggestedCourses({ courses }) {
  return (
    <div className="courses-section">
      <h3 className="courses-title">Courses Suggested</h3>
      {courses.length ? (
        <ul className="courses-list">
          {courses.map((item, i) => (
            <li key={i} className="course-item">
              <span className="course-name">{item.skill.toUpperCase()}</span>{' '}
              —{' '}
              <a
                href={item.url}
                className="course-link"
                target="_blank"
                rel="noreferrer"
              >
                Learn on YouTube
              </a>
            </li>
          ))}
        </ul>
      ) : (
        <p className="no-courses">No extra courses needed 🎉</p>
      )}
    </div>
  )
}
