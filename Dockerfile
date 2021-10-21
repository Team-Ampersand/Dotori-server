FROM openjdk:11-jre-slim-buster
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} ./
ENTRYPOINT ["java","-jar","Dotori-0.0.1-SNAPSHOT.jar"]