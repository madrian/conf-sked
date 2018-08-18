FROM openjdk:8-jdk-alpine
LABEL maintainer="github.com/madrian"
RUN mkdir -p /app
WORKDIR /app
ARG JAR_FILE
COPY ${JAR_FILE} conf-sked.jar
COPY talks.json talks.json
COPY scheduler.properties scheduler.properties
ENTRYPOINT ["java", "-jar", "conf-sked.jar"]
