# ==========================================
# Multi-Stage Build: Angular Frontend + Spring Boot Backend
# ==========================================

# --- Stage 1: Build Angular Frontend ---
FROM node:18-alpine AS frontend-build
WORKDIR /app/frontend

COPY frontend/package*.json ./
RUN npm ci

COPY frontend/ ./
RUN npm run build -- --configuration production

# --- Stage 2: Build Spring Boot Backend ---
FROM maven:3.9-eclipse-temurin-17-alpine AS backend-build
WORKDIR /app/backend

COPY backend/pom.xml ./
# Cache dependencies
RUN mvn dependency:go-offline -B

COPY backend/src ./src

# Copy built Angular static assets directly into Spring Boot's public static folder
COPY --from=frontend-build /app/frontend/dist/music-buddy-frontend/browser ./src/main/resources/static/

RUN mvn clean package -DskipTests

# --- Stage 3: Lightweight Production JRE Image ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Run as non-root user for production security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=backend-build /app/backend/target/*.jar app.jar

ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
