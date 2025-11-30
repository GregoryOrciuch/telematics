# Step 1: Use an official OpenJDK base image from Docker Hub
FROM amazoncorretto:25-alpine

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the Spring Boot JAR file into the container
COPY build/libs/telematics-0.0.1-SNAPSHOT.jar /app/telematics.jar

# Step 4: Expose the port your application runs on
EXPOSE 7089

# Step 5: Define the command to run your Spring Boot application
CMD ["java", "-jar", "/app/telematics.jar"]