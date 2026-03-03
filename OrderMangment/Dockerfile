# ===== build stage =====
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /workspace



COPY . .
RUN mvn -pl OrderMangment -am -DskipTests clean package
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/OrderMangment/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]