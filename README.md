# 🧠 SkillBridge: AI-Powered Skill Gap Analyzer

SkillBridge is a full-stack web application designed to intelligently extract skills from resumes and match them against job requirements, helping users identify gaps and upskill effectively. Built with a focus on elegant UI, scalable backend architecture, and AI-driven insights.

---

## 🚀 Features

- 🔍 **Resume Upload & Skill Extraction** using Gemini API
- 📊 **Gap Analysis** between candidate skills and job descriptions
- 🧠 **AI-Powered Recommendations** for upskilling
- 🧪 **Compact, Screenshot-Friendly UI** for sharing insights
- 🐳 **Dockerized Architecture** for seamless deployment

---

## 🛠️ Tech Stack

| Layer        | Technology                          |
|-------------|--------------------------------------|
| Frontend     | React, Vite, CSS Modules             |
| Backend      | Spring Boot, Hibernate ORM           |
| Database     | MySQL                                |
| AI Integration | Gemini API, Prompt Engineering     |
| DevOps       | Docker, Docker Compose               |

---

## 📦 Project Structure
skillbridge/
├── backend/ # Spring Boot API
│ └── src/main/java/ # Backend source code
│
├── frontend/ # React + Vite UI
│ └── src/ # Frontend source code
│
├── mysql/ # MySQL Docker volume
│
├── docker-compose.yml # Multi-container orchestration
├── .env # Environment variables (not committed)
└── README.md # Project documentation


---

## ⚙️ Setup Instructions

### 1. Clone the Repo
```bash
git clone https://github.com/insjunaid/SkillBridge.git
cd SkillBridge
```
### 2. Add Your .env File
```bash
MYSQL_ROOT_PASSWORD=yourRootPassword
MYSQL_DATABASE=skillbridge
MYSQL_USER=junaid
MYSQL_PASSWORD=yourUserPassword
```
### 3. Start the App with Docker
```bash
docker-compose up --build
```

