# Primera etapa: construir el JAR
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Establecer directorio de trabajo
WORKDIR /app

# Copiar el pom.xml y descargar dependencias (para cachear)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código fuente y construir el JAR
COPY src ./src
RUN mvn clean package -DskipTests

# Segunda etapa: imagen final
FROM openjdk:21-jdk-slim

# Copiar el JAR desde la etapa de construcción
COPY --from=build /app/target/cafeteria-0.0.1-SNAPSHOT.jar app.jar

# Exponer el puerto 8080
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]