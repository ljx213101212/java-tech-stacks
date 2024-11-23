# Use Eclipse Temurin as the base image
FROM eclipse-temurin:latest

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file to the container (assuming the mvn package has been executed by CICD)
COPY target/secret_provider-0.0.1-SNAPSHOT.jar secret-provider.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "secret-provider.jar"]