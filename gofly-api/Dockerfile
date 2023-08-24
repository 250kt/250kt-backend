FROM openjdk:17-alpine
RUN mkdir /app
COPY /target/gofly-api-0.0.1-SNAPSHOT.jar /app/gofly-api.jar
WORKDIR /app

ENTRYPOINT ["java", "-jar", "gofly-api.jar"]

