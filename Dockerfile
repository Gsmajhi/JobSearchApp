# ---------- Stage 1: Build ----------
FROM maven:3.8.5-eclipse-temurin-17 AS build
WORKDIR /app

# Copy project files
COPY JoSerachApp/pom.xml ./pom.xml
COPY JoSerachApp/src ./src

# Build the project
RUN mvn -q -DskipTests clean package


# ---------- Stage 2: Run ----------
FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
