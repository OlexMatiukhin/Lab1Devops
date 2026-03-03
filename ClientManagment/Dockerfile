# ===== build stage =====
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /workspace

COPY . .
RUN mvn -pl ClientManagment -am -DskipTests clean package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/ClientManagment/target/*.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar","/app/app.jar"]