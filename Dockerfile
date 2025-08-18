# --- Stage 1: Build the Spring Boot app using Gradle and JDK 21 ---
FROM gradle:8.5-jdk21 AS builder

WORKDIR /app

# Copy project files
COPY . .

# Build the app without running tests (optional: remove `-x test` to include them)
RUN gradle clean build -x test

# --- Stage 2: Create a slim runtime image ---
FROM eclipse-temurin:21-jdk AS runner

WORKDIR /app

# Copy the built JAR from previous stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose Spring Boot's default port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
