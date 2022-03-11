#Maven Build
FROM maven:3.8.3-openjdk-11-slim AS builder
COPY pom.xml /app/
COPY src /app/src
RUN --mount=type=cache,target=/root/.m2 mvn -f /app/pom.xml clean package -DskipTests

#Run
FROM openjdk:11-jre-slim
COPY --from=builder /app/target/pismo-1.0.jar pismo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "pismo.jar", "-web -webAllowOthers -tcp -tcpAllowOthers -browser"]