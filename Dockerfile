FROM openjdk:11-jre-slim-buster
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} dotori-server.jar
ENTRYPOINT ["java","-jar","*.jar"]