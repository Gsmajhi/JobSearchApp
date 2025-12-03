# Build stage
FROM maven:3.8.5-eclipse-temurin-17 AS build

WORKDIR /app

# Copy project inside the Docker image
COPY JobSearchWebsite/pom.xml ./pom.xml
COPY JobSearchWebsite/src ./src

# Build the Spring Boot JAR
RUN mvn -q -DskipTests package

# Runtime stage
FROM eclipse-temurin:17-jdk

WORKDIR /app

# Copy jar from the build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
