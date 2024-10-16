FROM openjdk:21-jdk-slim

WORKDIR /app

# Copy the Maven build output (your JAR file) to the working directory in the container
# Assuming your JAR file is in the 'target' directory after running `mvn package`
COPY ./insecureapi-0.0.1-SNAPSHOT.jar /app/insecureapi.jar

# Expose the port your application runs on (adjust the port as needed)
EXPOSE 9093

# Set the command to run your application
CMD ["java", "-jar", "/app/insecureapi.jar"]
