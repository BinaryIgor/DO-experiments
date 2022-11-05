FROM maven:3.8.3-openjdk-17 AS build
COPY src/ /src/
COPY pom.xml /pom.xml
RUN mvn clean install

FROM eclipse-temurin:17-focal
COPY --from=build /target/do-java-experiments-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

