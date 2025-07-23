# Dockerfile

FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Copy jar file (has been built with mvn clean package)
COPY target/match-management-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
