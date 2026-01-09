# Multi-stage Dockerfile
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /workspace
COPY . /workspace
RUN mvn -B -DskipTests package -pl app -am

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /workspace/app/target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

