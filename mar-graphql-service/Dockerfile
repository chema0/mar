FROM maven:3.8.5-openjdk-11
LABEL maintainer="josemaria.martinezm@um.es"
WORKDIR /app
COPY . /app
RUN mvn clean package -e -DskipTests
ENTRYPOINT ["java","-jar","/app/target/mar-graphql-service.jar"]
