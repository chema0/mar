FROM maven:3.8.5-openjdk-11 as build
LABEL maintainer="josemaria.martinezm@um.es"
ENV REPO_MAR=/opt/app
WORKDIR /opt/app

ADD pom.xml .
ADD mar-common/pom.xml mar-common/pom.xml
ADD mar-restservice/pom.xml mar-restservice/pom.xml
ADD mar-spark-merge/pom.xml mar-spark-merge/pom.xml
ADD mar-ml/pom.xml mar-ml/pom.xml
ADD mar-modelling/pom.xml mar-modelling/pom.xml
ADD mar-modelling-pnml/pom.xml mar-modelling-pnml/pom.xml
ADD mar-modelling-xtext/pom.xml mar-modelling-xtext/pom.xml
ADD mar-modelling-eclipse/pom.xml mar-modelling-eclipse/pom.xml
ADD mar-indexer-lucene/pom.xml mar-indexer-lucene/pom.xml
ADD mar-indexer-spark/pom.xml mar-indexer-spark/pom.xml
ADD mar-modelling-mongo/pom.xml mar-modelling-mongo/pom.xml
ADD thirdparty .

ADD mar-graphql-service mar-graphql-service
RUN mvn -pl mar-graphql-service package -DskipTests

FROM amazoncorretto:11
COPY --from=build /opt/app/mar-graphql-service/target/mar-graphql-service-1.0-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
CMD ["java","-jar","/app/app.jar"]