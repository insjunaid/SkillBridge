import React from 'react'

export default function AnalyzeButton({ onClick, disabled, children }) {
  return (
    <button
      className="analyze-btn"
      onClick={onClick}
      disabled={disabled}
    >
      {children}
    </button>
  )
}
