FROM cgr.dev/chainguard/jdk:latest

WORKDIR /app

COPY build/libs/telematics-0.0.1-SNAPSHOT.jar /app/telematics.jar

EXPOSE 7089

ENTRYPOINT ["java", "-XX:+ExitOnOutOfMemoryError", "-jar", "/app/app.jar"]