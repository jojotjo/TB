# 🚆 Train Booking System (Java CLI)

A production-ready **command-line railway reservation system** built in **Java** with **SQLite**.  
It supports secure user authentication, train search, real-time seat booking, ticket management, and database-backed reservation workflows — closely mirroring real-world railway systems.

---

## ✨ Core Features

### 🔐 User Management
- Secure **signup** with username & password validation
- **BCrypt password hashing** (no plain-text passwords)
- **Login system** with account lockout after 3 failed attempts
- Session-based user access management

### 🚂 Train & Booking Operations
- **Search trains** by source & destination
- **View train routes** and station schedules
- **Real-time seat availability** ([O] Available / [X] Booked)
- **Book seats** with unique ticket IDs
- **View all bookings** made by the logged-in user
- **Cancel bookings** with automatic seat release
- **Route validation** (prevents invalid source–destination selections)

### 💾 Database & Architecture
- Persistent storage using **SQLite**
- **5-table relational schema**: `users`, `trains`, `station_schedules`, `seats`, `tickets`
- **DAO + Service Layer** architecture for maintainable and testable code
- **Transactions** ensure booking consistency and prevent double bookings
- Automatic DB initialization with pre-seeded sample trains

---

## 🛠️ Tech Stack

| Layer | Technology |
|------|------------|
| Language | **Java 17** |
| Database | **SQLite** |
| Build Tool | **Gradle** |
| Security | **BCrypt** password hashing |
| Architecture | DAO + Services + Utility modules |

---

## 🚀 Future Roadmap

### Phase 1 – Feature Enhancements
- Multi-passenger booking
- Date-based train schedules & dynamic pricing
- Waitlist system and berth preferences

### Phase 2 – Web Migration
- **Spring Boot REST APIs**
- UI using **React** or **Angular**
- **JWT authentication**, email notifications, payment integration

### Phase 3 – Scalability & Performance
- Support **MySQL / PostgreSQL** for production
- **Redis caching**, admin dashboard, analytics

---

## 📌 Current Status

| Module | Status |
|-------|--------|
| User Signup & Login | ✅ Completed |
| Password Hashing | ✅ Completed |
| Database Schema & Seeding | ✅ Completed |
| Train Search | ✅ Completed |
| Seat Booking & Ticket Generation | ✅ Completed |
| View / Cancel Bookings | ✅ Completed |
| Web Version | 🔜 Planned |

---

## 🎯 Summary

This system demonstrates practical backend engineering skills, including:

- Secure credential handling  
- Modular service-based architecture  
- Transactional operations and relational database design  
- Real-world business workflows beyond simple CRUD operations  

---

## 📞 Support

For issues, suggestions, or contributions, feel free to open an Issue or Pull Request.

---

⭐ If you find this project useful, consider giving it a **star**!

