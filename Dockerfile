FROM openjdk:11-jre-slim-buster
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} /home/ngj/DotoriApp/app.jar
ENTRYPOINT ["java","-jar","~/DotoriApp/app.jar"]