FROM cgr.dev/chainguard/jre:latest

WORKDIR /app

COPY build/libs/telematics-0.0.1-SNAPSHOT.jar /app/telematics.jar

EXPOSE 7089

ENTRYPOINT ["java", "-XX:+ExitOnOutOfMemoryError", "-jar", "/app/telematics.jar"]