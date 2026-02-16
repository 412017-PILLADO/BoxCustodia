# ===== BUILD =====
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

  # Cache de dependencias
COPY pom.xml ./
RUN mvn -q -DskipTests dependency:go-offline

  # Build
COPY src ./src
RUN mvn -q -DskipTests clean package

  # ===== RUN =====
FROM eclipse-temurin:21-jre
WORKDIR /app

  # copiar el jar generado
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
