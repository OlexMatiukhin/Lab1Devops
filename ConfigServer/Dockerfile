FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /workspace


COPY . .
RUN mvn -f ConfigServer/pom.xml -DskipTests clean package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/ConfigServer/target/*.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar","/app/app.jar"]