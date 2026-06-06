# syntax=docker/dockerfile:1.7 

ARG MAVEN_IMAGE=maven:3.9-eclipse-temurin-25
ARG RUNTIME_IMAGE=eclipse-temurin:25-jre

FROM ${MAVEN_IMAGE} AS build
WORKDIR /workspace

ARG SERVICE=auth

COPY . .

RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -pl "${SERVICE}" -am package -DskipTests && \
    cp "${SERVICE}"/target/"${SERVICE}"-*.jar /tmp/app.jar

FROM ${RUNTIME_IMAGE}
WORKDIR /app

ARG SERVICE=auth
ARG SERVICE_PORT=8081

ENV SERVICE="${SERVICE}" \
    JAVA_OPTS=""

COPY --from=build --chown=10001:10001 /tmp/app.jar /app/app.jar

USER 10001:10001
EXPOSE ${SERVICE_PORT}

ENTRYPOINT ["sh", "-c", "exec java ${JAVA_OPTS} -jar /app/app.jar"]
