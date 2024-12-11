# Build Stage
FROM docker.io/library/maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
COPY .env ./app/
COPY .env .
# Build the application
RUN mvn clean package -DskipTests


# Runtime Stage (Alpine)
FROM docker.io/library/openjdk:25-jdk-slim
WORKDIR /app
COPY .env .
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
