-- Seed core skills
INSERT INTO skills (name) VALUES
  ('java'), ('spring'), ('spring boot'), ('hibernate'), ('jpa'),
  ('mysql'), ('react'), ('javascript'), ('rest api'), ('docker'),
  ('git'), ('maven'), ('junit'),
  -- Expanded skills
  ('python'), ('pandas'), ('numpy'), ('excel'), ('power bi'), ('tableau'),
  ('linux'), ('aws'), ('azure'), ('kubernetes'), ('tensorflow'), ('scikit-learn'),
  ('c++'), ('typescript'), ('graphql'), ('selenium'), ('jira'), ('agile')
ON DUPLICATE KEY UPDATE name = name;

-- Create job roles
INSERT INTO job_roles (id, title) VALUES
  (1, 'Java Developer'),
  (2, 'Full-Stack Developer')
ON DUPLICATE KEY UPDATE title = title;

-- Expanded roles (auto-generated IDs)
INSERT INTO job_roles (title) VALUES
  ('Data Analyst'), ('Business Analyst'), ('DevOps Engineer'), ('Machine Learning Engineer'),
  ('Frontend Developer'), ('Backend Developer'), ('Cloud Architect'), ('Cybersecurity Specialist'),
  ('Product Manager'), ('QA Engineer'), ('AI Researcher')
ON DUPLICATE KEY UPDATE title = title;

-- Link Java Developer to skills
INSERT IGNORE INTO job_role_skills (job_role_id, skill_id)
SELECT 1, s.id FROM skills s WHERE s.name IN (
  'java','spring','spring boot','hibernate','jpa','maven','junit','git','rest api'
);

-- Link Full-Stack Developer to skills
INSERT IGNORE INTO job_role_skills (job_role_id, skill_id)
SELECT 2, s.id FROM skills s WHERE s.name IN (
  'java','spring boot','react','javascript','rest api','docker','git'
);

-- Link Data Analyst to skills
INSERT IGNORE INTO job_role_skills (job_role_id, skill_id)
SELECT jr.id, s.id FROM job_roles jr, skills s
WHERE jr.title = 'Data Analyst' AND s.name IN (
  'python','excel','power bi','pandas','tableau'
);

-- Link Business Analyst to skills
INSERT IGNORE INTO job_role_skills (job_role_id, skill_id)
SELECT jr.id, s.id FROM job_roles jr, skills s
WHERE jr.title = 'Business Analyst' AND s.name IN (
  'excel','power bi','jira','agile'
);

-- Link DevOps Engineer to skills
INSERT IGNORE INTO job_role_skills (job_role_id, skill_id)
SELECT jr.id, s.id FROM job_roles jr, skills s
WHERE jr.title = 'DevOps Engineer' AND s.name IN (
  'linux','docker','aws','azure','kubernetes','git','maven'
);

-- Link Machine Learning Engineer to skills
INSERT IGNORE INTO job_role_skills (job_role_id, skill_id)
SELECT jr.id, s.id FROM job_roles jr, skills s
WHERE jr.title = 'Machine Learning Engineer' AND s.name IN (
  'python','numpy','pandas','tensorflow','scikit-learn','git'
);

-- Link Frontend Developer to skills
INSERT IGNORE INTO job_role_skills (job_role_id, skill_id)
SELECT jr.id, s.id FROM job_roles jr, skills s
WHERE jr.title = 'Frontend Developer' AND s.name IN (
  'react','javascript','html','css','typescript','graphql'
);

-- Link Backend Developer to skills
INSERT IGNORE INTO job_role_skills (job_role_id, skill_id)
SELECT jr.id, s.id FROM job_roles jr, skills s
WHERE jr.title = 'Backend Developer' AND s.name IN (
  'java','spring','spring boot','hibernate','jpa','mysql','rest api','git'
);

-- Link Cloud Architect to skills
INSERT IGNORE INTO job_role_skills (job_role_id, skill_id)
SELECT jr.id, s.id FROM job_roles jr, skills s
WHERE jr.title = 'Cloud Architect' AND s.name IN (
  'aws','azure','kubernetes','docker','linux'
);

-- Link Cybersecurity Specialist to skills
INSERT IGNORE INTO job_role_skills (job_role_id, skill_id)
SELECT jr.id, s.id FROM job_roles jr, skills s
WHERE jr.title = 'Cybersecurity Specialist' AND s.name IN (
  'linux','aws','azure','git','docker'
);

-- Link Product Manager to skills
INSERT IGNORE INTO job_role_skills (job_role_id, skill_id)
SELECT jr.id, s.id FROM job_roles jr, skills s
WHERE jr.title = 'Product Manager' AND s.name IN (
  'jira','agile','excel','power bi'
);

-- Link QA Engineer to skills
INSERT IGNORE INTO job_role_skills (job_role_id, skill_id)
SELECT jr.id, s.id FROM job_roles jr, skills s
WHERE jr.title = 'QA Engineer' AND s.name IN (
  'java','junit','selenium','jira','agile'
);

-- Link AI Researcher to skills
INSERT IGNORE INTO job_role_skills (job_role_id, skill_id)
SELECT jr.id, s.id FROM job_roles jr, skills s
WHERE jr.title = 'AI Researcher' AND s.name IN (
  'python','tensorflow','scikit-learn','numpy','pandas','c++'
);
