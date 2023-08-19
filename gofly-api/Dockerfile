FROM adoptopenjdk:17-jdk-temurin-alpine

WORKDIR /app

COPY target/gofly-api-0.0.1-SNAPSHOT.jar gofly-api.jar

ENTRYPOINT ["java", "-jar", "gofly-api.jar"]

