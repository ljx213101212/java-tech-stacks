# Use Eclipse Temurin as the base image
FROM eclipse-temurin:latest

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file to the container
COPY target/micro-collector-0.0.1-SNAPSHOT.jar micro-collector.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "micro-collector.jar"]