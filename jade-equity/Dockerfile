# Use a base image with Java 20 installed
FROM openjdk:20

# Copy your JAR files to the container
COPY ./target/jade-equity-0.0.1-SNAPSHOT.jar jade-equity-0.0.1-SNAPSHOT.jar


# Create a "public" folder
RUN mkdir public

# Expose ports

EXPOSE 8085



# Define the startup command to run all JAR files
CMD ["java", "-jar", "jade-equity-0.0.1-SNAPSHOT.jar"]
