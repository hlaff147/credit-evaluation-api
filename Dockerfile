FROM openjdk:24-slim

WORKDIR /app

COPY target/credit-evaluation-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]