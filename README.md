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
 
---

### Clone the repository
```
git clone https://github.com/saikamalesh415/task-manager.git
cd task-manager
```
Run the app
```
mvn spring-boot:run
``` 
- App will be available at 👉 (http://localhost:8080)

---

## 📦 Build JAR
```
mvn clean package
java -jar target/task-manager-0.0.1-SNAPSHOT.jar
```

---

## 🌐 Deployment
- The app is deployed on Render
- No SQL database is used → Tasks reset when the server restarts

---

## 📸 Screenshots
<img width="1672" height="806" alt="Task Manager Screenshot" src="https://github.com/user-attachments/assets/b5523f17-606f-4f3a-a932-353b867d8bec" />

---

## 🔮 Future Improvements

- Add SQL database (MySQL/PostgreSQL) for persistence
- User authentication (login & registration)
- Task categories, deadlines & priorities
- Docker support for easy deployment

---

## 📜 License

This project is open source and available under the MIT License.

