FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY build/libs/desafio-itau-backend-0.0.1-SNAPSHOT.jar /app/desafio-itau-backend.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/desafio-itau-backend.jar"]