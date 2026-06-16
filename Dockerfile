# syntax=docker/dockerfile:1.7

FROM maven:3.9-eclipse-temurin-25 AS build

WORKDIR /workspace

COPY pom.xml .
COPY shared/pom.xml shared/pom.xml
COPY auth/pom.xml auth/pom.xml
COPY candidate/pom.xml candidate/pom.xml
COPY employer/pom.xml employer/pom.xml
COPY jobs/pom.xml jobs/pom.xml
COPY ai/pom.xml ai/pom.xml
COPY search/pom.xml search/pom.xml
COPY integration/pom.xml integration/pom.xml

RUN mvn -B -DskipTests dependency:go-offline

COPY . .

ARG SERVICE=auth
RUN mvn -B -pl "${SERVICE}" -am -DskipTests package

FROM eclipse-temurin:25-jre

ARG SERVICE=auth
ARG SERVICE_PORT=8081

ENV JAVA_OPTS="" \
    SERVICE="${SERVICE}" \
    SERVICE_PORT="${SERVICE_PORT}"

WORKDIR /app

COPY --from=build /workspace/${SERVICE}/target/${SERVICE}-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE ${SERVICE_PORT}

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar --server.port=${SERVICE_PORT}"]
