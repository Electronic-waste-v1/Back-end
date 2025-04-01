FROM maven:3.9.9-eclipse-temurin-21-alpine  as build

COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-oracle

WORKDIR app

COPY --from=build /target/*.jar app.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "/app/app.jar"]