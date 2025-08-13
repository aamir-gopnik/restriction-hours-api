# syntax=docker/dockerfile:1

FROM maven:3.9.8-eclipse-temurin-17 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn -q -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -q -DskipTests package \
	&& cp target/*.jar app.jar

FROM eclipse-temurin:17-jre
WORKDIR /app

ENV JAVA_OPTS=""
EXPOSE 8080

COPY --from=builder /app/app.jar /app/app.jar

CMD ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"] 