# Etapa 1: Construcción del JAR con Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final con el JAR (usando Eclipse Temurin JRE 21)
FROM eclipse-temurin:21-jre
COPY --from=build /app/target/cafeteria-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]