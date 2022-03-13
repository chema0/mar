FROM openjdk:11
LABEL maintainer="josemaria.martinezm@um.es"
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
