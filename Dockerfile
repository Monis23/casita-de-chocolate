# ============================================================
# ETAPA 1: Construcción del JAR con Maven
# ============================================================
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar pom.xml y descargar dependencias (para usar caché)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código fuente y compilar
COPY src ./src
RUN mvn clean package -DskipTests

# ============================================================
# ETAPA 2: Imagen final para ejecutar la aplicación
# ============================================================
FROM eclipse-temurin:21-jre

WORKDIR /app
COPY --from=build /app/target/cafeteria-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]