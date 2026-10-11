# Multi-stage build; context is the repo root so the full reactor (parent pom + modules) is available.
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /workspace
COPY pom.xml .
COPY device-status/pom.xml device-status/pom.xml
COPY data-store/pom.xml data-store/pom.xml
COPY device-control/pom.xml device-control/pom.xml
COPY device-tests/pom.xml device-tests/pom.xml
COPY device-status/src device-status/src
RUN mvn -B -pl device-status -am package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /workspace/device-status/target/device-status.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
