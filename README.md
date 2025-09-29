                                
# Task Manager 

A simple **Spring Boot + Thymeleaf** task management web application.  
You can create, toggle (mark complete/incomplete), and delete tasks — all in one place.  
Deployed live on **Render** → [Try it here](https://task-manager-z81e.onrender.com)

---

## ✨ Features
- ➕ Add new tasks  
- ✅ Mark tasks as complete / incomplete  
- 🗑️ Delete tasks  
- 🖥️ Clean Thymeleaf-based UI (no separate frontend needed)  
- ☁️ Deployed as a single JAR on Render (24/7 accessible)  

---

## 🛠️ Tech Stack
- **Backend**: Java 22, Spring Boot  
- **Frontend**: Thymeleaf (server-side rendering), HTML, CSS, Bootstrap  
- **Build Tool**: Maven  
- **Database**: In-memory (no SQL database used; tasks are stored temporarily in runtime memory)  
- **Deployment**: Render (Java Web Service)  

---

## 🚀 Getting Started (Run Locally)

### Prerequisites
- Java 22+
- Maven

### 📸 Screenshots
<img width="1672" height="806" alt="image" src="https://github.com/user-attachments/assets/b5523f17-606f-4f3a-a932-353b867d8bec" />

## 🌐 Deployment
-The app is deployed on Render https://render.com/
-No SQL database is used → Tasks reset when the server restarts.

### Clone the repository
```bash
git clone https://github.com/saikamalesh415/task-manager.git
cd task-manager
```markdown
### Run the app
```bash
mvn spring-boot:run

App will be available at
```bash
👉 http://localhost:8080

## 📦 Build JAR
```bash
mvn clean package
java -jar target/task-manager-0.0.1-SNAPSHOT.jar

---

👉 This README is **clean and beginner-friendly**, explains your stack, makes it clear that no SQL DB is used, and also highlights the live Render deployment.  

Do you want me to also **add a "Future Improvements" section** (like adding SQL DB, authentication, etc.), so your project looks more professional to recruiters?
