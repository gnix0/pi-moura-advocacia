FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

COPY api/mvnw api/pom.xml ./
COPY api/.mvn .mvn

COPY api/adapter/pom.xml adapter/
COPY api/application/pom.xml application/
COPY api/bootstrap/pom.xml bootstrap/
COPY api/model/pom.xml model/

RUN ./mvnw dependency:go-offline -B

COPY api/adapter/src adapter/src
COPY api/application/src application/src
COPY api/bootstrap/src bootstrap/src
COPY api/model/src model/src

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Run as non-root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build /app/bootstrap/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
