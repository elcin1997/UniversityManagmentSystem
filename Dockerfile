FROM gradle:jdk17 as builder
COPY . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:17.0.1-jdk-slim

COPY --from=builder /home/gradle/src/build/libs/*SNAPSHOT.jar /app/app.jar
CMD ["java", "-jar", "/app/app.jar"]
EXPOSE 8080
