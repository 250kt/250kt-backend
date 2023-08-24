FROM openjdk:17-alpine
RUN mkdir /app
COPY /target/gofly-export-data-0.0.1-SNAPSHOT.jar /app/gofly-export-data.jar
WORKDIR /app

ENTRYPOINT ["java", "-jar", "gofly-export-data.jar"]

