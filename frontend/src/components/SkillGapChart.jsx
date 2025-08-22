import React from 'react'
import { Bar } from 'react-chartjs-2'
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Tooltip,
  Legend
} from 'chart.js'
import '../styles/SkillGap.css'

ChartJS.register(CategoryScale, LinearScale, BarElement, Tooltip, Legend)

export default function SkillGapChart({ matched = [], missing = [] }) {
  const data = {
    labels: ['Skills Matched', 'Skills Missing'],
    datasets: [
      {
        label: 'Skill Count',
        data: [matched.length, missing.length],
        backgroundColor: ['#10b981', '#f97316'],
        borderRadius: 10,
        borderSkipped: false
      }
    ]
  }

  const options = {
    indexAxis: 'y', // ✅ Horizontal bars
    responsive: true,
    maintainAspectRatio: false,
    plugins: {
      legend: { display: false },
      tooltip: {
        callbacks: {
          label: (context) => `${context.dataset.label}: ${context.raw}`
        }
      }
    },
    scales: {
      x: {
        beginAtZero: true,
        ticks: { stepSize: 1 },
        grid: { color: '#e5e7eb' }
      },
      y: {
        grid: { display: false },
        ticks: {
          font: { weight: 'bold' }
        }
      }
    }
  }

  return (
    <div className="chart-wrapper">
      <h2 className="visualization-title">Skill Gap Overview</h2>
      <div className="chart-box compact-chart">
        <Bar data={data} options={options} />
      </div>
    </div>
  )
}
