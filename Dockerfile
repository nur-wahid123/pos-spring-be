# Build Stage
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests


# Runtime Stage (Alpine)
FROM openjdk:25-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
