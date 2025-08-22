# 🧠 SkillBridge: AI-Powered Skill Gap Analyzer

SkillBridge is a full-stack web application designed to intelligently extract skills from resumes and match them against job requirements, helping users identify gaps and upskill effectively. Built with a focus on elegant UI, scalable backend architecture, and AI-driven insights.

---
<img width="700" height="500" alt="Image" src="https://github.com/user-attachments/assets/acd3f6b1-2cec-4537-9fec-780c7b9cfc8e" />

<img width="861" height="631" alt="Image" src="https://github.com/user-attachments/assets/a9c06f31-924d-4edc-a314-3032b5a3511a" />

<img width="860" height="487" alt="Image" src="https://github.com/user-attachments/assets/128aac63-5799-4d8d-9c37-d820b4e8339c" />

<img width="865" height="561" alt="Image" src="https://github.com/user-attachments/assets/b43408c1-1fe0-43e3-95bc-9bb49ae4292b" />

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
    ├── backend/               # Spring Boot API
    │   └── src/main/java/     # Backend source code
    │
    ├── frontend/              # React + Vite UI
    │   └── src/               # Frontend source code
    │
    ├── mysql/                 # MySQL Docker volume
    │
    ├── docker-compose.yml     # Multi-container orchestration
    ├── .env                   # Environment variables (not committed)
    └── README.md              # Project documentation



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
MYSQL_USER=root
MYSQL_PASSWORD=yourUserPassword
```
### 3. Start the App with Docker
```bash
docker-compose up --build
```

## 🤝 Contributing
SkillBridge is built with a community-first mindset. Contributions, ideas, and feedback are welcome — especially those that align with ethical design and collective benefit.

## 🙏 Acknowledgments
Gemini API for intelligent skill extraction

Open-source communities for inspiration and tooling

---

## Build with ❤️ and 💻

Made with passion, purpose, and clean code.  
Crafted by **Junaid Shariff** 🚀



