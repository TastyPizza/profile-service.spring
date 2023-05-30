FROM openjdk:19-slim

ARG JAR_FILE=target/*.jar

COPY $JAR_FILE /profile-service.jar

ENTRYPOINT exec java -jar /profile-service.jar