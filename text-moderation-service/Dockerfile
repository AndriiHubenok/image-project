# syntax=docker/dockerfile:1

FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace

COPY pom.xml .
COPY .mvn .mvn
COPY mvnw mvnw
RUN chmod +x mvnw
RUN ./mvnw -q -DskipTests dependency:go-offline

COPY src src
RUN ./mvnw -q -DskipTests package

FROM eclipse-temurin:21-jre-jammy AS runtime
WORKDIR /app

RUN groupadd --system spring && useradd --system --gid spring spring

COPY --from=build /workspace/target/*.jar /app/app.jar
RUN chown -R spring:spring /app

USER spring
EXPOSE 8092

ENV JVM_OPTS=""
ENTRYPOINT ["sh", "-c", "java $JVM_OPTS -jar /app/app.jar"]

