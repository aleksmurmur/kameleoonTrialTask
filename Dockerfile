FROM maven:3.8.7 AS maven
LABEL MAINTAINER="aleksmurmur@yahoo.com"

WORKDIR /usr/src/app
COPY . /usr/src/app

RUN mvn package

FROM openjdk:17-alpine

ARG JAR_FILE=kameleoon-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app

COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app/

ENTRYPOINT ["java","-jar","kameleoon-0.0.1-SNAPSHOT.jar"]