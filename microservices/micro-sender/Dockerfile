FROM eclipse-temurin:latest

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file to the container
COPY target/micro-sender-0.0.1-SNAPSHOT.jar micro-sender.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=docker", "micro-sender.jar"]