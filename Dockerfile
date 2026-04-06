FROM maven:3.9.11-eclipse-temurin-21-alpine AS build
WORKDIR /app

COPY api/pom.xml api/pom.xml
COPY api/bootstrap/pom.xml api/bootstrap/pom.xml
COPY api/customer/pom.xml api/customer/pom.xml
COPY api/user/pom.xml api/user/pom.xml

RUN mvn -f api/pom.xml -pl bootstrap -am dependency:go-offline -B

COPY api/bootstrap/src api/bootstrap/src
COPY api/customer/src api/customer/src
COPY api/user/src api/user/src

RUN mvn -f api/pom.xml -pl bootstrap -am clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=build /app/api/bootstrap/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
