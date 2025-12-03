# ---------- Stage 1: Build the application ----------
FROM maven:3.8.5-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn -q dependency:go-offline

# Copy the entire project and build
COPY . .
RUN mvn -q -DskipTests clean package

# ---------- Stage 2: Run the application ----------
FROM eclipse-temurin:17-jdk
WORKDIR /app

# Copy jar from first stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
