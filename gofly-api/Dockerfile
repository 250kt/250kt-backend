FROM openjdk:17-alpine
RUN echo $(pwd)
RUN echo $(ls)
RUN mkdir /app
COPY /target/gofly-api-0.0.1-SNAPSHOT.jar /app/gofly-api.jar
WORKDIR /app

ENTRYPOINT ["java", "-jar", "gofly-api.jar"]

