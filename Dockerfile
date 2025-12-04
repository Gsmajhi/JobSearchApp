# Build stage
FROM maven:3.8.5-eclipse-temurin-17 AS build

WORKDIR /app

COPY JobSearchWebsite/pom.xml .
RUN mvn dependency:go-offline -B

COPY JobSearchWebsite/src ./src

RUN mvn -q -DskipTests package && \
    mv target/*.jar app.jar

# Runtime stage
FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY --from=build /app/app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
