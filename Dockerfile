FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY . .
RUN mvn -q -e -DskipTests clean package
EXPOSE 8080
CMD ["java", "-jar", "target/*.jar"]
