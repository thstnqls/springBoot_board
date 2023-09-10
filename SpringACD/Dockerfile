FROM openjdk:11-jdk
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
LABEL authors="user"
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "app.jar"]