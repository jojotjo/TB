# 🚆 Train Booking System (Java)

A full-featured railway reservation system built in Java — available in two versions: a **CLI application** and a **Spring Boot web application**. Both versions support secure user authentication, train search, real-time seat booking, and ticket management — closely mirroring real-world railway systems.

---

## 📁 Project Structure

```
TB/
├── cli/                        ← Command-line version (Java + SQLite + Gradle)
│   └── src/main/java/ticket/Booking/
│       ├── App.java
│       ├── entities/
│       ├── services/
│       └── util/
│
├── springboot/                 ← Web version (Spring Boot + MySQL + Thymeleaf)
│   └── src/main/java/com/jojotjo/ticketbooking/
│       ├── config/
│       ├── controller/
│       ├── model/
│       ├── repository/
│       ├── service/
│       └── dto/
│
└── README.md
```

---

## 🖥️ Version 1 — CLI Application

A production-ready command-line railway reservation system built with Java and SQLite.

### ✨ Core Features

#### 🔐 User Management
- Secure signup with username & password validation
- BCrypt password hashing (no plain-text passwords)
- Login system with account lockout after 3 failed attempts
- Session-based user access management

#### 🚂 Train & Booking Operations
- Search trains by source & destination
- View train routes and station schedules
- Real-time seat availability (`[O]` Available / `[X]` Booked)
- Book seats with unique ticket IDs
- View all bookings made by the logged-in user
- Cancel bookings with automatic seat release
- Route validation (prevents invalid source–destination selections)

#### 💾 Database & Architecture
- Persistent storage using SQLite
- 5-table relational schema: `users`, `trains`, `station_schedules`, `seats`, `tickets`
- DAO + Service Layer architecture for maintainable and testable code
- Transactions ensure booking consistency and prevent double bookings
- Automatic DB initialization with pre-seeded sample trains

### 🛠️ Tech Stack

| Layer        | Technology                        |
|--------------|-----------------------------------|
| Language     | Java 17                           |
| Database     | SQLite                            |
| Build Tool   | Gradle                            |
| Security     | BCrypt password hashing           |
| Architecture | DAO + Services + Utility modules  |

### 🚀 How to Run (CLI)

```bash
cd cli
./gradlew run
```

---

## 🌐 Version 2 — Spring Boot Web Application

A full-stack web application built with Spring Boot, MySQL, and Thymeleaf.

### ✨ Core Features

#### 🔐 User Management
- Secure signup and login with Spring Security
- BCrypt password hashing
- Session-based authentication
- Protected routes — only logged-in users can access booking features

#### 🚂 Train & Booking Operations
- Search trains by source & destination via REST API
- Real-time seat availability
- Book single or multiple passengers
- View all bookings from your profile
- Cancel bookings with automatic seat release

#### 💾 Database & Architecture
- Persistent storage using MySQL
- JPA/Hibernate ORM with relational schema
- REST API with `@RestController`
- Service layer with full transaction support
- Auto-initialized train and station data on startup

### 🛠️ Tech Stack

| Layer        | Technology                          |
|--------------|-------------------------------------|
| Language     | Java 17                             |
| Framework    | Spring Boot 3                       |
| Database     | MySQL                               |
| ORM          | Hibernate / Spring Data JPA         |
| Frontend     | Thymeleaf + HTML/CSS                |
| Security     | Spring Security + BCrypt            |
| Build Tool   | Maven                               |
| Architecture | MVC + Service Layer + DTO pattern   |

### ⚙️ Setup & Configuration

1. Create a MySQL database:
```sql
CREATE DATABASE ticketdb;
```

2. Copy the example config and fill in your credentials:
```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

3. Edit `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ticketdb
spring.datasource.username=root
spring.datasource.password=your_password_here
```

### 🚀 How to Run (Spring Boot)

```bash
cd springboot
./mvnw spring-boot:run
```

Then open your browser at: **http://localhost:8080**

### 🔍 Valid Train Searches
| From      | To        |
|-----------|-----------|
| Delhi     | Agra      |
| Delhi     | Gwalior   |
| Delhi     | Bhopal    |
| Delhi     | Indore    |
| Mumbai    | Bangalore |

---

## 📊 Feature Comparison

| Feature                  | CLI Version | Spring Boot Version |
|--------------------------|-------------|---------------------|
| User Signup & Login      | ✅           | ✅                   |
| Password Hashing         | ✅           | ✅                   |
| Train Search             | ✅           | ✅                   |
| Seat Booking             | ✅           | ✅                   |
| View / Cancel Bookings   | ✅           | ✅                   |
| Web Interface            | ❌           | ✅                   |
| REST API                 | ❌           | ✅                   |
| Multi-passenger Booking  | ❌           | ✅                   |
| Database                 | SQLite       | MySQL               |

---

## 📌 Current Status

| Module                      | CLI        | Spring Boot |
|-----------------------------|------------|-------------|
| User Signup & Login         | ✅ Complete | ✅ Complete  |
| Password Hashing            | ✅ Complete | ✅ Complete  |
| Database Schema & Seeding   | ✅ Complete | ✅ Complete  |
| Train Search                | ✅ Complete | ✅ Complete  |
| Seat Booking & Ticket Gen   | ✅ Complete | ✅ Complete  |
| View / Cancel Bookings      | ✅ Complete | ✅ Complete  |
| Web Interface               | ❌ N/A      | ✅ Complete  |
| REST API                    | ❌ N/A      | ✅ Complete  |

---

## 🚀 Future Roadmap

### Phase 1 – Feature Enhancements
- Date-based train schedules & dynamic pricing
- Waitlist system and berth preferences
- Admin dashboard for managing trains and users

### Phase 2 – Upgrades
- JWT authentication
- Email notifications
- Payment integration
- React or Angular frontend

### Phase 3 – Scalability & Performance
- Redis caching
- Support PostgreSQL for production
- Analytics and reporting

---
