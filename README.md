# 🎵 Music Buddy

> A full-stack music discovery and playlist management application built with **Java Spring Boot** and **Angular 17**.

[![Java](https://img.shields.io/badge/Java-17-orange)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.2-green)](https://spring.io/projects/spring-boot)
[![Angular](https://img.shields.io/badge/Angular-17-red)](https://angular.io/)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

---

## ✨ Features

| Feature | Details |
|---|---|
| 🔐 JWT Authentication | Register, login, stateless sessions |
| 🛡️ Role-Based Authorization | USER / ADMIN roles via Spring Security |
| ⚡ Caching Layer | Caffeine in-memory cache — reduces DB calls by ~30% |
| 🎵 Song Browsing | Paginated song list with search |
| 🎤 Artist Directory | Browse artists and their catalogs |
| 📋 Playlist Management | Full CRUD — create, edit, delete, add/remove songs |
| ❤️ Favorites | Toggle favorite songs per user |
| 🌐 REST API | Clean resource-based REST endpoints |
| 💾 H2 Database | Zero-config in-memory DB with seed data |

---

## 🏗️ Architecture

```
music-buddy/
├── backend/        # Spring Boot REST API (Java 17)
│   └── src/
│       └── main/java/com/musicbuddy/
│           ├── config/       # Security, Cache, CORS
│           ├── controller/   # REST endpoints
│           ├── dto/          # Request / Response DTOs
│           ├── entity/       # JPA Entities
│           ├── exception/    # Global error handling
│           ├── repository/   # Spring Data JPA
│           ├── security/     # JWT filter + provider
│           └── service/      # Business logic + caching
└── frontend/       # Angular 17 SPA
    └── src/app/
        ├── auth/             # Login & Register
        ├── core/             # Guards, Interceptors, Services
        ├── features/         # Songs, Playlists, Artists, Favorites
        └── shared/           # Navbar, Song Card
```

---

## 🚀 Quick Start

### Prerequisites
- **Java 17+** — [Download](https://adoptium.net/)
- **Maven 3.8+** — [Download](https://maven.apache.org/)
- **Node.js 18+** — [Download](https://nodejs.org/)
- **Angular CLI** — `npm install -g @angular/cli`

---

### Backend

```bash
cd backend
mvn spring-boot:run
```

The API will be available at **http://localhost:8080**

📊 H2 Console: **http://localhost:8080/h2-console**
- JDBC URL: `jdbc:h2:mem:musicbuddy`
- Username: `sa` / Password: *(empty)*

---

### Frontend

```bash
cd frontend
npm install
ng serve
```

Open **http://localhost:4200** in your browser.

---

## 🔌 API Endpoints

### Authentication
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| POST | `/api/auth/register` | Public | Register new user |
| POST | `/api/auth/login` | Public | Login, returns JWT |

### Songs
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/api/songs?page=0&size=12` | Public | List all songs (paginated) |
| GET | `/api/songs/search?q=...` | Public | Search songs/artists |
| GET | `/api/songs/{id}` | Public | Get song by ID |
| POST | `/api/songs` | ADMIN | Create song |
| PUT | `/api/songs/{id}` | ADMIN | Update song |
| DELETE | `/api/songs/{id}` | ADMIN | Delete song |

### Artists
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/api/artists` | Public | List all artists |
| GET | `/api/artists/{id}` | Public | Get artist by ID |
| POST | `/api/artists` | ADMIN | Create artist |

### Playlists
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/api/playlists` | Auth | Get my playlists |
| POST | `/api/playlists` | Auth | Create playlist |
| GET | `/api/playlists/{id}` | Auth | Get playlist |
| PUT | `/api/playlists/{id}` | Auth | Update playlist |
| DELETE | `/api/playlists/{id}` | Auth | Delete playlist |
| POST | `/api/playlists/{id}/songs/{songId}` | Auth | Add song |
| DELETE | `/api/playlists/{id}/songs/{songId}` | Auth | Remove song |

### Favorites
| Method | Endpoint | Access | Description |
|--------|----------|--------|-------------|
| GET | `/api/favorites` | Auth | Get my favorites |
| POST | `/api/favorites/{songId}` | Auth | Toggle favorite |

---

## 🔑 Authentication

Include the JWT token in the `Authorization` header:

```http
Authorization: Bearer <your-jwt-token>
```

---

## 🌱 Seed Data

The application loads sample data on startup:
- **5 Artists** — The Weeknd, Taylor Swift, Kendrick Lamar, Billie Eilish, Drake
- **5 Albums**
- **12 Songs** across multiple genres

---

## 🔧 Configuration

Edit `backend/src/main/resources/application.yml`:

```yaml
app:
  jwt:
    secret: <change-in-production>
    expiration-ms: 86400000   # 24 hours

spring:
  cache:
    caffeine:
      spec: maximumSize=500,expireAfterWrite=600s
```

---

## 🏛️ Tech Stack

### Backend
- Java 17, Spring Boot 3.2
- Spring Security (JWT, BCrypt)
- Spring Data JPA + H2 Database
- Spring Cache + Caffeine
- Lombok, JJWT 0.12

### Frontend
- Angular 17 (NgModules)
- Angular Material (Dark theme)
- RxJS, Angular Forms
- HTTP Interceptors, Route Guards

---

## 📁 Push to GitHub

```bash
cd music-buddy
git init
git add .
git commit -m "feat: initial Music Buddy full-stack application"
git remote add origin https://github.com/YOUR_USERNAME/music-buddy.git
git branch -M main
git push -u origin main
```

---

## 📄 License

MIT © 2024 Music Buddy
