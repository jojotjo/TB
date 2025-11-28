# 🚆 Train Booking System (Java CLI)

A simple **command-line Train Booking System** built in **Java**, designed to simulate real-world booking operations such as user signup, login, searching trains, booking seats, and managing reservations.  

Currently, the system supports **secure signup & login** with **password hashing** and **SQLite database integration**.  

---

## ✨ Features (Current & Upcoming)
- ✅ **User Authentication**
  - Secure **signup** with username & password validation
  - **Login** with hashed password verification
  - Session management (basic failed login attempt tracking)
- 📌 **Planned Features**
  - Fetch Bookings
  - Search Trains
  - Book a Seat
  - Cancel Booking
  - Full database-backed operations

---

## 🛠️ Tech Stack
- **Java 17**
- **SQLite** (for data persistence in CLI mode)
- **Gradle** (for build automation)

### 🔐 Security
- Passwords are stored **hashed** (no plain-text storage)  
- Input validation for both usernames and passwords  

---

## 🚀 Future Roadmap
- 🔄 Extend database support to **MySQL / PostgreSQL** for relational DB  
- 🌱 Add **NoSQL (MongoDB)** integration  
- 🌐 Convert CLI application into a **Spring Boot Web Application**  
  - REST APIs for user authentication and booking management  
  - Frontend integration (React/Angular)  

---

## 📌 Progress Updates

- ✅ Completed **Signup & Login with password hashing**  
- 🔜 Implementing **Train search & booking system**

