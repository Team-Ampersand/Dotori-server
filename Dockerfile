FROM openjdk:11-jre-slim-buster
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} /var/lib/jenkins/workspace/Dotori-test-server/
ENTRYPOINT ["java","-jar","*.jar"]